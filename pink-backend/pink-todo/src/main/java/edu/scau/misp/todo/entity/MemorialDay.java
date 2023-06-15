package edu.scau.misp.todo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * 小程序纪念日表(MemorialDay)实体类
 *
 * @author makejava
 * @since 2022-11-03 10:01:38
 */
public class MemorialDay implements Serializable {
    private static final long serialVersionUID = -20785054280344955L;
    
    private Integer memorialDayId;
    /**
     * 纪念日日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
//    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;
    /**
     * 纪念日名称
     */
    private String memorialDayName;
    /**
     * 纪念日情况，0为没置顶，1为置顶了
     */
    private Integer conditionId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
////    @JSONField(format="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
////    @JSONField(format="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    /**
     * 备注
     */
    private String remark;


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

