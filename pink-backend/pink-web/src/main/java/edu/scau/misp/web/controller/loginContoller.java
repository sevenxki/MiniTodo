package edu.scau.misp.web.controller;

import edu.scau.misp.sys.entity.ResultEntity;
import edu.scau.misp.sys.entity.WechatLoginRequest;

import edu.scau.misp.sys.service.WechatService;
import edu.scau.misp.todo.entity.WechatUser;
import edu.scau.misp.todo.model.IdModel;
import edu.scau.misp.todo.model.Reception;
import edu.scau.misp.todo.service.WechatUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/login")
public class loginContoller {
    @Resource
    WechatService wechatService;
    @Resource
    WechatUserService wechatUserService;

    @PostMapping("/save")
    public ResultEntity login(@Validated @RequestBody WechatLoginRequest loginRequest) throws Exception {
       IdModel user = wechatService.getUserInfoMap(loginRequest);
        return ResultEntity.success("获取成功", user);
    }

    /**
     * 通过openid查询单条数据
     *
     * @param openId 主键
     * @return 单条数据
     */
    @GetMapping("")
    public ResultEntity getById(@RequestParam("openId") String openId) {
        return ResultEntity.success("获取成功",this.wechatUserService.getByOpenId(openId));
    }

    /**
     * 通过openid查询单条数据
     *
     * @return 单条数据
     */
    @PutMapping("/updateNickname")
    public ResultEntity updateNickname(@RequestBody Reception reception) {
        String openId=reception.getOpenId();
        String nickname = reception.getNickname();
        WechatUser wechatUser = new WechatUser();
        wechatUser.setNickname(nickname);
        wechatUser.setOpenId(openId);
        this.wechatUserService.updateByOpenId(wechatUser);
        return ResultEntity.success("更改成功");
    }




}
