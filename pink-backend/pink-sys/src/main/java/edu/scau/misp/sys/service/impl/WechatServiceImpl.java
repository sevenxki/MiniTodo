package edu.scau.misp.sys.service.impl;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import edu.scau.misp.sys.entity.WechatLoginRequest;

import edu.scau.misp.sys.service.WechatService;
import edu.scau.misp.sys.util.JwtUtil;
import edu.scau.misp.todo.dao.WechatUserDao;
import edu.scau.misp.todo.entity.RawData;
import edu.scau.misp.todo.model.IdModel;
import edu.scau.misp.todo.entity.WechatUser;
import edu.scau.misp.todo.util.CacheManager;
import edu.scau.misp.todo.util.DateUtils;
import edu.scau.misp.todo.util.HttpClientUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;



import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;

import java.security.Security;
import java.util.*;

@Service("wechatService")
public class WechatServiceImpl implements WechatService {
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String  GRANT_TYPE = "authorization_code";
    @Resource
    private WechatUserDao wechatUserService;
    @Resource
    private JwtUtil jwtUtil;
    CacheManager map = CacheManager.getCacheManagerInstance();
    CacheManager TMap = CacheManager.getCacheManagerInstance();

    @Override
    public IdModel getUserInfoMap(WechatLoginRequest loginRequest) throws Exception {
        Map<String, Object> userInfoMap = new HashMap<>();
        // logger报错的话，删掉就好，或者替换为自己的日志对象
//        logger.info("Start get SessionKey，loginRequest的数据为：" + JSONObject.toJSONString(loginRequest));
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(loginRequest.getCode());
        // 这里的ErrorCodeEnum是自定义错误字段，可以删除，用自己的方式处理
//        Assert.isTrue(sessionKeyOpenId != null, ErrorCodeEnum.P01.getCode());

        // 获取openId && sessionKey
        String openId = sessionKeyOpenId.getString("openid");
        // 这里的ErrorCodeEnum是自定义错误字段，可以删除，用自己的方式处理
//        Assert.isTrue(openId != null, ErrorCodeEnum.P01.getCode());
        String sessionKey = sessionKeyOpenId.getString("session_key");
        WechatUser insertOrUpdateDO = buildWechatUserAuthInfoDO(loginRequest, sessionKey, openId);

        // 根据code保存openId和sessionKey
        JSONObject sessionObj = new JSONObject();
        sessionObj.put("openId", openId);
        sessionObj.put("sessionKey", sessionKey);

        String skey=DateUtils.dateTimeNow();
        insertOrUpdateDO.setSkey(skey);

        map.put(loginRequest.getCode(),sessionObj.toJSONString());
        userInfoMap.put("WechatUser",insertOrUpdateDO);
        // 根据openid查询用户，这里的查询service自己写，就不贴出来了
        WechatUser user = wechatUserService.getByOpenId(openId);
        String t = getToken(skey,openId);
        if (user == null) {
            // 用户不存在，insert用户，这里加了个分布式锁，防止insert重复用户，看自己的业务，决定要不要这段代码
//            if (setLock(WechatRedisPrefixConstant.INSERT_USER_DISTRIBUTED_LOCK_PREFIX + openId, "1", 10)) {
                // 用户入库，service自己写
//                insertOrUpdateDO.setToken(t);
            TMap.put(openId,t);
            wechatUserService.insert(insertOrUpdateDO);
            userInfoMap.put("token", t);
//          }
        } else {
            userInfoMap.put("token", t);
            TMap.put(openId,t);
//            wechatUserService.updateByOpenId(insertOrUpdateDO);
        }
//        UserInforModel userInforModel = new UserInforModel();
//        userInforModel.setId(insertOrUpdateDO.getId());
//        userInforModel.setAvatarUrl(insertOrUpdateDO.getAvatarUrl());
//        userInforModel.setNickname(insertOrUpdateDO.getNickname());
//        userInforModel.setMobile(insertOrUpdateDO.getMobile());
//        userInforModel.setSkey(insertOrUpdateDO.getSkey());
//        userInforModel.setToken(t);
        IdModel idModel = new IdModel();
        idModel.setOpenId(openId);
        idModel.setToken(t);
        return idModel;
    }

    // 这里的JSONObject是阿里的fastjson，自行maven导入
    private JSONObject getSessionKeyOrOpenId(String code) throws Exception {
        Map<String, String> requestUrlParam = new HashMap<>();
        // 小程序appId，自己补充
        requestUrlParam.put("appid", "wx1f688c9b774908aa");
        // 小程序secret，自己补充
        requestUrlParam.put("secret", "02c32318497aedc100c542e0ff04fdd6");
        // 小程序端返回的code
        requestUrlParam.put("js_code", code);
        // 默认参数
        requestUrlParam.put("grant_type", GRANT_TYPE);
        // 发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpClientUtils.doPost(REQUEST_URL, requestUrlParam );
        return JSON.parseObject(result);
    }

    private WechatUser buildWechatUserAuthInfoDO(WechatLoginRequest loginRequest, String sessionKey, String openId){
        WechatUser wechatUserDO = new WechatUser();
        wechatUserDO.setOpenId(openId);

        if (loginRequest.getRawData() != null) {
            RawData rawDataDO = JSON.parseObject(loginRequest.getRawData(), RawData.class);
            wechatUserDO.setNickname(rawDataDO.getNickName());
            wechatUserDO.setAvatarUrl(rawDataDO.getAvatarUrl());
            wechatUserDO.setGender(rawDataDO.getGender());
            wechatUserDO.setCity(rawDataDO.getCity());
            wechatUserDO.setCountry(rawDataDO.getCountry());
            wechatUserDO.setProvince(rawDataDO.getProvince());
        }

        // 解密加密信息，获取unionID
        if (loginRequest.getEncryptedData() != null){
            JSONObject encryptedData = getEncryptedData(loginRequest.getEncryptedData(), sessionKey, loginRequest.getIv());
            if (encryptedData != null){
                String unionId = encryptedData.getString("unionId");
                String mobile = encryptedData.getString("phoneNumber");
                wechatUserDO.setUnionId(unionId);
                wechatUserDO.setMobile(mobile);
            }
        }

        return wechatUserDO;
    }

    private JSONObject getEncryptedData(String encryptedData, String sessionkey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.这个if中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
//            logger.error("解密加密信息报错", e.getMessage());
        }
        return null;
    }

    //补全数组位数
    public static byte[] completToBase(byte[] bytes, int base) {
        byte[] temp = new byte[]{};
        if (bytes.length % base != 0) {
            int groups = bytes.length / base + (bytes.length % base != 0 ? 1 : 0);
            temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(bytes, 0, temp, 0, bytes.length);
            return temp;
        } else {
            return bytes;
        }
    }


//    private boolean setLock(String key, String value, long expire) throws Exception {
//        boolean result = stringJedisClientTem.setNx(key, value, expire, TimeUnit.SECONDS);
//        return result;
//    }

    private String getToken(String skey,String openId) throws Exception {
        // 这里自定义token生成策略，可以用UUID+sale进行MD5
        String t = jwtUtil.createJWT(skey, openId);
//        System.out.println("原openId="+openId);
//        System.out.println("原skey="+skey);
//        System.out.println("加密后token="+t);
        return t;
    }
}
