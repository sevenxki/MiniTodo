package edu.scau.misp.todo.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import edu.scau.misp.todo.dao.MemorialDayUserDao;
import edu.scau.misp.todo.dao.WechatUserDao;
import edu.scau.misp.todo.entity.MemorialDay;
import edu.scau.misp.todo.dao.MemorialDayDao;
import edu.scau.misp.todo.entity.MemorialDayUser;
import edu.scau.misp.todo.model.MemorialDayList;
import edu.scau.misp.todo.model.MemorialList;
import edu.scau.misp.todo.service.MemorialDayService;
import edu.scau.misp.todo.service.MemorialDayUserService;
import edu.scau.misp.todo.util.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 小程序纪念日表(MemorialDay)表服务实现类
 *
 * @author makejava
 * @since 2022-11-03 10:01:44
 */
@Service("memorialDayService")
public class MemorialDayServiceImpl implements MemorialDayService {
    @Resource
    private MemorialDayDao memorialDayDao;
    @Resource
    private MemorialDayUserDao memorialDayUserDao;
    @Resource
    private WechatUserDao wechatUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param memorialDayId 主键
     * @return 实例对象
     */
    @Override
    public MemorialDay queryById(Integer memorialDayId) {
        return this.memorialDayDao.queryById(memorialDayId);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param memorialDayId 主键
     * @return 实例对象
     */
    @Override
    public MemorialList queryByIdAddType(Integer memorialDayId) {
        MemorialDay memorialDay =  this.memorialDayDao.queryById(memorialDayId);
        MemorialList memorialList = new MemorialList();
        memorialList.setMemorialDayId(memorialDay.getMemorialDayId());
        memorialList.setRemark(memorialDay.getRemark());
        memorialList.setTime(memorialDay.getTime());
        memorialList.setConditionId(memorialDay.getConditionId());
        memorialList.setCreatedTime(memorialDay.getCreatedTime());
        memorialList.setTitle(memorialDay.getMemorialDayName());
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date time = DateUtil.parse(dateFormat.format(date)) ;
        Long c = DateUtil.between(time, memorialDay.getTime(), DateUnit.DAY);
        memorialList.setDay(c.intValue());
        if (memorialDay.getTime().compareTo(time)<0){
            memorialList.setTypeCode("true");
            memorialList.setType("memory");
        }else if (memorialDay.getTime().compareTo(time)>=0){
            memorialList.setTypeCode("false");
            memorialList.setType("target");
        }
        return memorialList;
    }


    /**
     * 查询指定行数据
     *
     * @param memorialDay 筛选条件
     * @return 对象列表
     */
    @Override
    public List<MemorialDay> queryAll(MemorialDay memorialDay) {
        return this.memorialDayDao.queryAll(memorialDay);
    }

    /**
     * 新增数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    @Override
    public MemorialDay insert(MemorialDayList memorialDayList) {
        String openId = memorialDayList.getOpenId();
        MemorialDay memorialDay = new MemorialDay();
        memorialDay.setMemorialDayName(memorialDayList.getMemorialDayName());
        memorialDay.setCreatedTime(new Date());
        memorialDay.setUpdatedTime(new Date());
        memorialDay.setTime(memorialDayList.getTime());
        memorialDay.setRemark(memorialDayList.getRemark());
        MemorialDayUser memorialDayUser = this.memorialDayUserDao.queryByTop(openId);
        if (memorialDayList.getConditionId()!=null && memorialDayList.getConditionId()==1){  //如果设为置顶
            if (memorialDayUser==null){   //没有置顶
                memorialDay.setConditionId(memorialDayList.getConditionId());
                System.out.println("没有置顶");
            }else if (memorialDayUser!=null){  //有置顶
                System.out.println("有置顶");
                System.out.println(memorialDayUser.getMemorialDayId());
                if (memorialDayUser.getMemorialDayId()!=null && memorialDayUser.getMemorialDayId()==memorialDayList.getMemorialDayId()){
                    memorialDay.setConditionId(memorialDayList.getConditionId());
                }else if (memorialDayUser.getMemorialDayId()!=null && memorialDayUser.getMemorialDayId()!=memorialDayList.getMemorialDayId()){
                    MemorialDay memorialDay1 = this.memorialDayDao.queryById(memorialDayUser.getMemorialDayId());
                    memorialDay1.setConditionId(0);
                    this.memorialDayDao.update(memorialDay1);
                    System.out.println(memorialDayUser.getMemorialDayId());
                    System.out.println("更新好了");
                    memorialDay.setConditionId(memorialDayList.getConditionId());
                }
            }
        }else {
            memorialDay.setConditionId(memorialDayList.getConditionId());
        }
        this.memorialDayDao.insert(memorialDay);
        //插入关系表
        MemorialDayUser memorialDayUser1 = new MemorialDayUser();
        memorialDayUser1.setMemorialDay(memorialDay);
        memorialDayUser1.setMemorialDayId(memorialDay.getMemorialDayId());
        memorialDayUser1.setOpenId(memorialDayList.getOpenId());
        memorialDayUser1.setWechatUser(this.wechatUserDao.getByOpenId(memorialDayList.getOpenId()));
        this.memorialDayUserDao.insert(memorialDayUser1);
        return memorialDay;
    }

//    public static Date strToDateFormat(String str) {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        Date date=null;
//        try {
//            date=sdf.parse(str);
//        }catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
    /**
     * 修改数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    @Override
    public MemorialDay update(MemorialDayList memorialDayList) {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date nd = DateUtil.parse(sdf.format(date));
//        System.out.println(date);
//        System.out.println(sdf.format(date));
//        System.out.println(nd);
        String openId = memorialDayList.getOpenId();
        MemorialDay memorialDay = new MemorialDay();
        memorialDay.setMemorialDayName(memorialDayList.getMemorialDayName());
        memorialDay.setUpdatedTime(new Date());
        memorialDay.setMemorialDayId(memorialDayList.getMemorialDayId());
        memorialDay.setTime(memorialDayList.getTime());
        memorialDay.setCreatedTime(memorialDayList.getCreatedTime());
        memorialDay.setRemark(memorialDayList.getRemark());
        MemorialDayUser memorialDayUser = this.memorialDayUserDao.queryByTop(openId);
        if (memorialDayList.getConditionId()!=null && memorialDayList.getConditionId()==1){  //如果设为置顶
            if (memorialDayUser==null){   //没有置顶
                memorialDay.setConditionId(memorialDayList.getConditionId());
//                System.out.println("没有置顶");
            }else if (memorialDayUser!=null){  //有置顶
//                System.out.println("有置顶");
//                System.out.println(memorialDayUser.getMemorialDayId());
                if (memorialDayUser.getMemorialDayId()!=null && memorialDayUser.getMemorialDayId()==memorialDayList.getMemorialDayId()){
                    memorialDay.setConditionId(memorialDayList.getConditionId());
                }else if (memorialDayUser.getMemorialDayId()!=null && memorialDayUser.getMemorialDayId()!=memorialDayList.getMemorialDayId()){
                    MemorialDay memorialDay1 = this.memorialDayDao.queryById(memorialDayUser.getMemorialDayId());
                    memorialDay1.setConditionId(0);
                    this.memorialDayDao.update(memorialDay1);
//                    System.out.println(memorialDayUser.getMemorialDayId());
//                    System.out.println("更新好了");
                    memorialDay.setConditionId(memorialDayList.getConditionId());
                }
            }
        }else {
            memorialDay.setConditionId(memorialDayList.getConditionId());
        }
//            throw new ServiceException("删除失败");
        this.memorialDayDao.update(memorialDay);
        return this.queryById(memorialDay.getMemorialDayId());
    }

    /**
     * 通过主键删除数据
     *
     * @param memorialDayId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer memorialDayId) {
        MemorialDayUser memorialDayUser = this.memorialDayUserDao.queryByMemorialDayId(memorialDayId);
        int memorialDayUserId = memorialDayUser.getMemorialDayUserId();
        boolean a;
        if (memorialDayDao.deleteById(memorialDayUserDao.queryById(memorialDayUserId).getMemorialDayId())>0){
            a = memorialDayUserDao.deleteById(memorialDayUserId)>0;
        }else {
            throw new ServiceException("删除失败");
        }
        return a;
    }
}
