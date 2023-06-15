package edu.scau.misp.todo.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MemorialDayList {
    private Integer memorialDayUserId;
    /**
     * 小程序openId
     */
    private String openId;
    /**
     * 纪念日ID
     */
    private Integer memorialDayId;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /**
     * 纪念日日期
     */
    private Date time;
    /**
     * 纪念日名称
     */
    private String memorialDayName;
    /**
     * 纪念日情况，0为没到即目标日，1为到了即纪念日
     */
    private Integer conditionId;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    /**
     * 备注
     */
    private String remark;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMemorialDayName() {
        return memorialDayName;
    }

    public void setMemorialDayName(String memorialDayName) {
        this.memorialDayName = memorialDayName;
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
