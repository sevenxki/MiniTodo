package edu.scau.misp.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scau.misp.sys.entity.ResultEntity;
import edu.scau.misp.todo.entity.Tag;
import edu.scau.misp.todo.entity.ToDoList;
import edu.scau.misp.todo.model.*;
import edu.scau.misp.todo.service.ListService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (ToDoList)表控制层
 *
 * @author makejava
 * @since 2022-11-03 13:20:18
 */
@RestController
@RequestMapping("list")
public class ListController {
    /**
     * 服务对象
     */
    @Resource
    private ListService listService;

    /**
     * 查询列表
     *
     * @param toDoList 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public List<ToDoList> list(ToDoList toDoList) {
        return this.listService.queryAll(toDoList);
    }

    /**
     * 分页查询
     *
     * @param pageNum 起始页
     * @param pageSize 每页数据大小
     * @param toDoList 筛选条件
     * @return 查询结果
     */
    @GetMapping("/page")
    public PageInfo page(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, ToDoList toDoList) {
        PageHelper.startPage(pageNum,pageSize);
        List<ToDoList> list = listService.queryAll(toDoList);
        PageInfo<List> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param listId 主键
     * @return 单条数据
     */
    @GetMapping("{listId}")
    public ToDoList queryById(@PathVariable("listId") Integer listId) {
        return this.listService.queryById(listId);
    }

    /**
     * 新增数据
     *
     * @param toDoList 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public String add(@RequestBody ToDoList toDoList) {
        this.listService.insert(toDoList);
        return "ok";
    }

    /**
     * 编辑数据
     *
     * @param toDoList 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public String edit(ToDoList toDoList) {
        this.listService.update(toDoList);
        return "ok";
    }

    /**
     * 删除数据
     *
     * @param listId 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{listId}")
    public Boolean deleteById(@PathVariable("listId") Integer listId) {
        return this.listService.deleteById(listId);
    }

    /**
     * 获取具体某一天的所有待办事项(根据openId 和当前日期 搜索）DateList dateList
     *
     */
    @GetMapping("/queryNow")
    public ResultEntity queryNow(@RequestParam("openId") String openId,@RequestParam("date")String time) {
//        System.out.println(openId);
//        System.out.println(time);
        DateList dateList = new DateList();
        dateList.setOpenId(openId);
        String format = "yyyy-MM-dd";
        Date date = null;
        try {
            date = DateUtils.parseDate(time, format, "yyyy-MM-dd");
            dateList.setDate(date);
//            System.out.println(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResultEntity.success("成功",this.listService.queryNow(dateList));
    }

    /**
     * 获取日历上的待办日日期列表(根据openId搜索），按照等级
     *
     */
    @GetMapping("/queryCalendarToDo/{openId}")
    public ResultEntity queryCalendarToDo(@PathVariable("openId") String openId) {
        return ResultEntity.success("成功",this.listService.queryCalendarToDo(openId));
    }

    /**
     * 未完成事项列表提供(根据openId搜索）
     *
     */
    @GetMapping("/queryAllToDo/{openId}")
    public ResultEntity queryAllToDo(@PathVariable("openId") String openId) {
        return ResultEntity.success("成功",this.listService.queryAllToDo(openId));
    }

    /**
     * 未完成事项列表提供(根据openId、tagName搜索）
     *
     */
    @GetMapping("/queryAllToDoByTag")
    public ResultEntity queryAllToDoByTag(@RequestParam("openId") String openId,@RequestParam("tagName")String tagName) {
        return ResultEntity.success("成功",this.listService.queryAllToDoByTag(openId,tagName));
    }

    /**
     * 某个人建立过的所有tag名（未完成）
     *
     */
    @GetMapping("/queryTagName/{openId}")
    public ResultEntity queryTagName(@PathVariable("openId") String openId) {
        List<ListTab> listTab = new ArrayList<>();
        List<String> name = this.listService.queryTagName(openId);

        for(String n:name){
            ListTab name1 = new ListTab();
            name1.setName(n);
            listTab.add(name1);
//            System.out.println(n);
        }
        return ResultEntity.success("成功",listTab);
    }

    /**
     * 已完成事项列表提供
     *
     */
    @GetMapping("/queryAllFinish/{openId}")
    public ResultEntity queryAllFinish(@PathVariable("openId") String openId) {
        return ResultEntity.success("成功",this.listService.queryAllFinish(openId));
    }

    /**
     * 已完成事项列表提供(根据openId、tagName搜索）
     *
     */
    @GetMapping("/queryAllFinishByTag")
    public ResultEntity queryAllFinishByTag(@RequestParam("openId") String openId,@RequestParam("tagName")String tagName) {
        return ResultEntity.success("成功",this.listService.queryAllFinishByTag(openId,tagName));
    }

    /**
     * 某个人建立过的所有tag名（已完成）
     *
     */
    @GetMapping("/queryTagName1/{openId}")
    public ResultEntity queryTagName1(@PathVariable("openId") String openId) {
        List<ListTab> listTab = new ArrayList<>();
        List<String> name = this.listService.queryTagName1(openId);
        for(String n:name){
            ListTab name1 = new ListTab();
            name1.setName(n);
            listTab.add(name1);
        }
        return ResultEntity.success("成功",listTab);
    }

    /**
     * 事项明细提供
     *
     */
    @GetMapping("/queryDetails")
    public ResultEntity queryDetails(@RequestParam("openId") String openId,@RequestParam("listId")Integer listId) {
        return ResultEntity.success("成功",this.listService.queryDetails(openId,listId));
    }

    /**
     * 通过标题搜索未完成的事项
     *
     */
    @GetMapping("/queryAllToDoByTitle")
    public ResultEntity queryAllToDoByTitle(@RequestParam("openId")String openId,@RequestParam("listTitle")String listTitle ) {
//        System.out.println("openId:"+openId+"listTitle"+listTitle);
        return ResultEntity.success("成功",this.listService.queryAllToDoByTitle(openId,listTitle));
    }

    /**
     * 通过标题和标签搜索未完成的事项
     *
     */
    @GetMapping("/queryAllToDoByTitleAndTag")
    public ResultEntity queryAllToDoByTitleAndTag(@RequestParam("openId")String openId,@RequestParam("listTitle")String listTitle,@RequestParam("tagName")String tagName) {
//        System.out.println("openId:"+openId+"listTitle"+listTitle);
        return ResultEntity.success("成功",this.listService.queryAllToDoByTitleAndTag(openId,listTitle,tagName));
    }

    /**
     * 通过标题和标签搜索未完成的事项
     *
     */
    @GetMapping("/queryAllFinishByTitleAndTag")
    public ResultEntity queryAllFinishByTitleAndTag(@RequestParam("openId")String openId,@RequestParam("listTitle")String listTitle,@RequestParam("tagName")String tagName) {
//        System.out.println("openId:"+openId+"listTitle"+listTitle);
        return ResultEntity.success("成功",this.listService.queryAllFinishByTitleAndTag(openId,listTitle,tagName));
    }

    /**
     * 通过标题搜索已完成的事项
     *
     */
    @GetMapping("/queryAllFinishByTitle")
    public ResultEntity queryAllFinishByTitle(@RequestParam("openId")String openId,@RequestParam("listTitle")String listTitle ) {
        return ResultEntity.success("成功",this.listService.queryAllFinishByTitle(openId,listTitle));
    }

    /**
     * 通过时间搜索未完成的事项
     *
     */
    @GetMapping("/queryAllToDoByTime")
    public ResultEntity queryAllToDoByTime(@RequestParam("openId") String openId,
                                           @RequestParam("before")String before,
                                           @RequestParam("after")String after)throws ParseException {
        return ResultEntity.success("成功",this.listService.queryAllToDoByTime(openId,before,after));
    }

    /**
     * 通过时间搜索已完成的事项
     *
     */
    @GetMapping("/queryAllFinishByTime")
    public ResultEntity queryAllFinishByTime(@RequestParam("openId") String openId,
                                             @RequestParam("before")String before,
                                             @RequestParam("after")String after)throws ParseException {
        return ResultEntity.success("成功",this.listService.queryAllFinishByTime(openId,before,after));
    }

//    /**
//     * 添加待办事项
//     *
//     */
//    @PostMapping("/insertNewList")
//    public ResultEntity insertNewList(@RequestParam("openId") String openId,
//                                      @RequestParam("listPriorityName") String listPriorityName,
//                                      @RequestParam("listTitle") String listTitle,
//                                      @RequestParam("listContent") String listContent,
//                                      @RequestParam("tagName") String tagName) throws ParseException {
//        ListDetailsModel listDetailsModel = new ListDetailsModel();
//        listDetailsModel.setOpenId(openId);
//        listDetailsModel.setListContent(listContent);
//        listDetailsModel.setListTitle(listTitle);
//        listDetailsModel.setListPriorityName(listPriorityName);
//        Tag tag = new Tag();
//        tag.setTagName(tagName);
//        listDetailsModel.setTag(tag);
//        this.listService.insertNewList(listDetailsModel);
//        return ResultEntity.success("成功");
//    }

    /**
     * 未完成更改成已完成
     *
     */
    @PutMapping("/toDoToFinish")
    public ResultEntity toDoToFinish(@RequestBody ToDoOrFinish toDoOrFinish){
        this.listService.toDoToFinish(toDoOrFinish.getOpenId(),toDoOrFinish.getListId());
        return ResultEntity.success("成功");
    }
//    @RequestParam("openId") String openId,@RequestParam("listId") Integer listId

    /**
     * 已完成更改成未完成
     *
     */
    @PutMapping("/FinishTotoDo")
    public ResultEntity FinishTotoDo(@RequestBody ToDoOrFinish toDoOrFinish){
        this.listService.FinishTotoDo(toDoOrFinish.getOpenId(),toDoOrFinish.getListId());
        return ResultEntity.success("成功");
    }

//    /**
//     * 修改待办事项
//     *
//     */
//    @PutMapping("/updateNewList")
//    public ResultEntity updateNewList(@RequestParam("openId") String openId,
//                                      @RequestParam("listPriorityName") String listPriorityName,
//                                      @RequestParam("listTitle") String listTitle,
//                                      @RequestParam("listContent") String listContent,
//                                      @RequestParam("tagName") String tagName,
//                                      @RequestParam("listId") Integer listId) throws ParseException {
//        ListDetailsModel listDetailsModel = new ListDetailsModel();
//        listDetailsModel.setListId(listId);
//        listDetailsModel.setOpenId(openId);
//        listDetailsModel.setListContent(listContent);
//        listDetailsModel.setListTitle(listTitle);
//        listDetailsModel.setListPriorityName(listPriorityName);
//        Tag tag = new Tag();
//        tag.setTagName(tagName);
//        listDetailsModel.setTag(tag);
//        this.listService.updateNewList(listDetailsModel);
//        return ResultEntity.success("成功");
//    }

    /**
     * 添加待办事项
     *
     */
    @PutMapping("/insertNewList")
    public ResultEntity insertNewList(@RequestBody ListDetailsModel all) throws ParseException {
        ListDetailsModel listDetailsModel = new ListDetailsModel();
        listDetailsModel.setListId(all.getListId());
        listDetailsModel.setOpenId(all.getOpenId());
        listDetailsModel.setListContent(all.getListContent());
        listDetailsModel.setListTitle(all.getListTitle());
        listDetailsModel.setListTime(all.getListTime());
        listDetailsModel.setListPriorityName(all.getListPriorityName());
        Tag tag = new Tag();
        tag.setTagName(all.getTag().getTagName());
        listDetailsModel.setTag(tag);
        this.listService.insertNewList(listDetailsModel);
        return ResultEntity.success("成功");
    }

    /**
     * 修改待办事项
     *
     */
    @PutMapping("/updateNewList")
    public ResultEntity updateNewList(@RequestBody ListDetailsModel all) throws ParseException {
        ListDetailsModel listDetailsModel = new ListDetailsModel();
        listDetailsModel.setListId(all.getListId());
        listDetailsModel.setOpenId(all.getOpenId());
        listDetailsModel.setListContent(all.getListContent());
        listDetailsModel.setListTitle(all.getListTitle());
        listDetailsModel.setListTime(all.getListTime());
        listDetailsModel.setListPriorityName(all.getListPriorityName());
        Tag tag = new Tag();
        tag.setTagName(all.getTag().getTagName());
        listDetailsModel.setTag(tag);
        this.listService.updateNewList(listDetailsModel);
        return ResultEntity.success("成功");
    }

    /**
     * 删除list数据
     *
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteList")
    public ResultEntity deleteList(@RequestBody Delete delete) {
        this.listService.deleteById(delete.getListId());
        return ResultEntity.success("成功");
    }


}

