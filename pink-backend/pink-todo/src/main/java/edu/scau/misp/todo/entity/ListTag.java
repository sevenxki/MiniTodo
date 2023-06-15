package edu.scau.misp.todo.entity;

import java.io.Serializable;

/**
 * (ListTag)实体类
 *
 * @author makejava
 * @since 2022-11-03 13:20:42
 */
public class ListTag implements Serializable {
    private static final long serialVersionUID = -32474777918862585L;
    /**
     * 待办事项标签id
     */
    private Integer listTagId;
    /**
     * 待办事项id
     */
    private Integer listId;
    /**
     * 标签id
     */
    private Integer tagId;

    private ToDoList toDoList;

    private Tag tag;

    public ToDoList getToDoList() {
        return toDoList;
    }

    public void setToDoList(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Integer getListTagId() {
        return listTagId;
    }

    public void setListTagId(Integer listTagId) {
        this.listTagId = listTagId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

}

