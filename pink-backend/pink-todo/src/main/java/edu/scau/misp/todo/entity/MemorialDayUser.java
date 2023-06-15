package edu.scau.misp.todo.entity;

import java.io.Serializable;

/**
 * 小程序纪念日用户关系表(MemorialDayUser)实体类
 *
 * @author makejava
 * @since 2022-11-03 10:39:36
 */
public class MemorialDayUser implements Serializable {
    private static final long serialVersionUID = 539136791113860184L;
    
    private Integer memorialDayUserId;
    /**
     * 小程序openId
     */
    private String openId;
    /**
     * 纪念日ID
     */
    private Integer memorialDayId;

    private WechatUser wechatUser;
    private MemorialDay memorialDay;


    public Integer getMemorialDayUserId() {
        return memorialDayUserId;
    }

    public void setMemorialDayUserId(Integer memorialDayUserId) {
        this.memorialDayUserId = memorialDayUserId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getMemorialDayId() {
        return memorialDayId;
    }

    public void setMemorialDayId(Integer memorialDayId) {
        this.memorialDayId = memorialDayId;
    }

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    public MemorialDay getMemorialDay() {
        return memorialDay;
    }

    public void setMemorialDay(MemorialDay memorialDay) {
        this.memorialDay = memorialDay;
    }
}

