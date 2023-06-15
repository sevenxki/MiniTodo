package edu.scau.misp.todo.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CalDay {
    /**
     * 小程序openId
     */
    private String openId;
//    private int year;
//    private int month;
    @JsonFormat(pattern="yyyy-MM",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date ym;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getYm() {
        return ym;
    }

    public void setYm(Date ym) {
        this.ym = ym;
    }
}
