package edu.scau.misp.todo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (ToDoList)实体类
 *
 * @author makejava
 * @since 2022-11-03 13:20:19
 */
public class ToDoList implements Serializable {
    private static final long serialVersionUID = -71895516456787983L;
    /**
     * 待办事项id
     */
    private Integer listId;
    /**
     * 待办事项标题
     */
    private String listTitle;
    /**
     * 待办事项内容详情
     */
    private String listContent;
    /**
     * 待办事项状态 0-未完成；1-已完成
     */
    private Integer listStatus;
    /**
     * 用户open_id
     */
    private String openId;
    /**
     * 待办事项创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date listTime;
    /**
     * 待办事项优先级1234
     */
    private Integer listPriority;
    /**
     * 待办事项no
     */
    private String listNo;

    private WechatUser wechatUser;

    public WechatUser getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(WechatUser wechatUser) {
        this.wechatUser = wechatUser;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getListContent() {
        return listContent;
    }

    public void setListContent(String listContent) {
        this.listContent = listContent;
    }

    public Integer getListStatus() {
        return listStatus;
    }

    public void setListStatus(Integer listStatus) {
        this.listStatus = listStatus;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getListTime() {
        return listTime;
    }

    public void setListTime(Date listTime) {
        this.listTime = listTime;
    }

    public Integer getListPriority() {
        return listPriority;
    }

    public void setListPriority(Integer listPriority) {
        this.listPriority = listPriority;
    }

    public String getListNo() {
        return listNo;
    }

    public void setListNo(String listNo) {
        this.listNo = listNo;
    }
}

