package edu.scau.misp.todo.model;

import edu.scau.misp.todo.entity.Tag;
import edu.scau.misp.todo.entity.ToDoList;

public class NowList {
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
}
