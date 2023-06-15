package edu.scau.misp.todo.service;

import edu.scau.misp.todo.entity.Tag;
import edu.scau.misp.todo.entity.ToDoList;
import edu.scau.misp.todo.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * (ToDoList)表服务接口
 *
 * @author makejava
 * @since 2022-11-03 13:20:19
 */
public interface ListService {

    /**
     * 通过ID查询单条数据
     *
     * @param listId 主键
     * @return 实例对象
     */
    ToDoList queryById(Integer listId);

    /**
     * 分页查询
     *
     * @param toDoList 筛选条件
     * @return 查询结果
     */
    List<ToDoList> queryAll(ToDoList toDoList);

    /**
     * 新增数据
     *
     * @param toDoList 实例对象
     * @return 实例对象
     */
    ToDoList insert(ToDoList toDoList);

    /**
     * 修改数据
     *
     * @param toDoList 实例对象
     * @return 实例对象
     */
    ToDoList update(ToDoList toDoList);

    /**
     * 通过主键删除数据
     *
     * @param listId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer listId);

    List<NowList> queryNow(DateList dateList);

    List<CalendarToDo> queryCalendarToDo(String openId);

    List<ToDoList> queryByOne(String openId);

    List<ToDoList> queryByTwo(String openId);

    List<ToDoList> queryByThree(String openId);

    List<ToDoList> queryByFour(String openId);

    /**
     * 未完成事项列表提供(根据openId搜索）
     *
     */
    List<AllListModel> queryAllToDo(String openId);

    /**
     * 未完成事项列表提供(根据openId、tagName搜索）
     *
     */
    List<AllListModel> queryAllToDoByTag(String openId,String tagName);

    /**
     * 某个人建立过的所有tag名（未完成）
     *
     */
    List<String> queryTagName(String openId);

    /**
     * 已完成事项列表提供
     *
     */
    List<AllListModel> queryAllFinish(String openId);

    /**
     * 事项明细提供
     *
     */
    ListDetailsModel queryDetails(String openId, Integer listId);

    /**
     * 已完成事项列表提供(根据openId、tagName搜索）
     *
     */
    List<AllListModel> queryAllFinishByTag(String openId,String tagName);

    /**
     * 某个人建立过的所有tag名（已完成）
     *
     */
    List<String> queryTagName1(String openId);

    /**
     * 通过标题搜索未完成的事项
     *
     */
    List<AllListModel> queryAllToDoByTitle(String openId,String listTitle);

    /**
     * 通过标题和标签搜索未完成的事项
     *
     */
    List<AllListModel> queryAllToDoByTitleAndTag(String openId,String listTitle,String tagName);

    /**
     * 通过标题和标签搜索未完成的事项
     *
     */
    List<AllListModel> queryAllFinishByTitleAndTag(String openId,String listTitle,String tagName);

    /**
     * 通过标题搜索已完成的事项
     *
     */
    List<AllListModel> queryAllFinishByTitle(String openId,String listTitle);

    /**
     * 未完成事项列表查询(通过时间）
     *
     */
    List<AllListModel> queryAllToDoByTime(String openId,String listTime1,String listTime2) throws ParseException;

    /**
     * 已完成事项列表查询(通过时间）
     *
     */
    List<AllListModel> queryAllFinishByTime(String openId,String listTime1,String listTime2) throws ParseException;

    /**
     * 添加待办事项
     *
     */
    void insertNewList(ListDetailsModel listDetailsModel) throws ParseException;

    /**
     * 未完成更改成已完成
     *
     */
    void toDoToFinish(String openId, Integer listId);

    /**
     * 已完成更改成未完成
     *
     */
    void FinishTotoDo(String openId, Integer listId);

    /**
     * 修改待办事项
     *
     */
    void updateNewList(ListDetailsModel listDetailsModel) throws ParseException;

    /**
     * 修改ListTag
     *
     */
    void deleteList(Integer listId);

}
