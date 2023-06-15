package edu.scau.misp.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (Tag)实体类
 *
 * @author makejava
 * @since 2022-11-13 22:05:44
 */
public class Tag implements Serializable {
    private static final long serialVersionUID = -85925051092673844L;
    /**
     * 标签id
     */
    private Integer tagId;
    /**
     * 标签name
     */
    private String tagName;
    /**
     * 标签时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tagTime;
    /**
     * 标签no
     */
    private String tagNo;
    
    private String colorNo;


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getTagTime() {
        return tagTime;
    }

    public void setTagTime(Date tagTime) {
        this.tagTime = tagTime;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getColorNo() {
        return colorNo;
    }

    public void setColorNo(String colorNo) {
        this.colorNo = colorNo;
    }

}

