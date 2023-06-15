package edu.scau.misp.todo.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CalList {

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
     * 纪念日情况，0为没到即目标日，1为到了即纪念日
     */
    private Integer conditionId;
    /**
     * 备注
     */
    private String remark;
    private Integer day;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
