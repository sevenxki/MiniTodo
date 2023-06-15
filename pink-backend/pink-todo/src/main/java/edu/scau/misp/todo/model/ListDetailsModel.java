package edu.scau.misp.todo.model;

import com.alibaba.fastjson.annotation.JSONField;
import edu.scau.misp.todo.entity.Tag;
import edu.scau.misp.todo.entity.WechatUser;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class ListDetailsModel {
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
     * 待办事项创建时间
     */
    private String listTime;
    /**
     * 待办事项优先级1234
     */
    private String listPriorityName;
    /**
     * 标签
     */
    private Tag tag;
//    private List<Tag> tagList;
    /**
     * 用户open_id
     */
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
    }

    public String getListPriorityName() {
        return listPriorityName;
    }

    public void setListPriorityName(String listPriorityName) {
        this.listPriorityName = listPriorityName;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
