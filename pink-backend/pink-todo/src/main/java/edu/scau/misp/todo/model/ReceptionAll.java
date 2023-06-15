package edu.scau.misp.todo.model;

import edu.scau.misp.todo.entity.Tag;

public class ReceptionAll {
    /**
     * 用户open_id
     */
    private String openId;
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
     * 标签名
     */
    private String tagName;
    /**
     * 标签id
     */
    private String tagId;

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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
