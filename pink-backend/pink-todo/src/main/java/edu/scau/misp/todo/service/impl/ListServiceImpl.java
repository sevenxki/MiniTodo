package edu.scau.misp.todo.service.impl;

import edu.scau.misp.todo.dao.ListTagDao;
import edu.scau.misp.todo.dao.TagDao;
import edu.scau.misp.todo.entity.ListTag;
import edu.scau.misp.todo.entity.Tag;
import edu.scau.misp.todo.entity.ToDoList;
import edu.scau.misp.todo.dao.ListDao;
import edu.scau.misp.todo.model.*;
import edu.scau.misp.todo.service.ListService;
import edu.scau.misp.todo.util.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * (ToDoList)表服务实现类
 *
 * @author makejava
 * @since 2022-11-03 13:20:19
 */
@Service("ListService")
public class ListServiceImpl implements ListService {
    @Resource
    private ListDao listDao;
    @Resource
    private ListTagDao listTagDao;
    @Resource
    private TagDao tagDao;

    private DateUtils dateUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param listId 主键
     * @return 实例对象
     */
    @Override
    public ToDoList queryById(Integer listId) {
        return this.listDao.queryById(listId);
    }

    /**
     * 查询指定行数据
     *
     * @param  toDoList 筛选条件
     * @return 对象列表
     */
    @Override
    public List<ToDoList> queryAll(ToDoList toDoList) {
        return this.listDao.queryAll(toDoList);
    }
//    /**
//     * 分页查询
//     *
//     * @param toDoList 筛选条件
//     * @param pageRequest      分页对象
//     * @return 查询结果
//     */
//    @Override
//    public Page<ToDoList> queryByPage(ToDoList toDoList, PageRequest pageRequest) {
//        long total = this.listDao.count(toDoList);
//        return new PageImpl<>(this.listDao.queryAll(toDoList, pageRequest), pageRequest, total);
//    }

    /**
     * 新增数据
     *
     * @param toDoList 实例对象
     * @return 实例对象
     */
    @Override
    public ToDoList insert(ToDoList toDoList) {
        this.listDao.insert(toDoList);
        return toDoList;
    }

    /**
     * 修改数据
     *
     * @param toDoList 实例对象
     * @return 实例对象
     */
    @Override
    public ToDoList update(ToDoList toDoList) {
        this.listDao.update(toDoList);
        return this.queryById(toDoList.getListId());
    }

    /**
     * 通过主键删除数据
     *
     * @param listId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer listId) {
        return this.listDao.deleteById(listId) > 0;
    }

    /**
     * 获取具体某一天的所有待办事项
     *
     * @param dateList
     * @return
     */
    @Override
    public List<NowList> queryNow(DateList dateList) {
        Date date = dateList.getDate();
        String openId = dateList.getOpenId();
        ToDoList toDoList = new ToDoList();
        toDoList.setOpenId(openId);
        List<ToDoList> toDoLists = this.listDao.queryToDo(toDoList);
        List<NowList> t = new ArrayList<>();
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        for (ToDoList toDoList1 : toDoLists){
            if (toDoList1.getListTime().equals(date)){
                NowList nowList = new NowList();
//                System.out.println(toDoList1.getListId());
//                System.out.println(listTagDao.queryByListId(toDoList1.getListId()).getTagId());
                if (listTagDao.queryByListId(toDoList1.getListId())!=null){
                    Tag tag = tagDao.queryById(listTagDao.queryByListId(toDoList1.getListId()).getTagId());
                    nowList.setTag(tag);
                }
                nowList.setToDoList(toDoList1);
                t.add(nowList);
            }
        }
        return t;
    }


    @Override
    public List<ToDoList> queryByOne(String openId) {
        return null;
    }

    @Override
    public List<ToDoList> queryByTwo(String openId) {
        return null;
    }

    @Override
    public List<ToDoList> queryByThree(String openId) {
        return null;
    }

    @Override
    public List<ToDoList> queryByFour(String openId) {
        return null;
    }

    /**
     * 获取日历上的待办日日期列表，按照等级
     *
     */
    @Override
    public List<CalendarToDo> queryCalendarToDo(String openId) {
        ToDoList toDoList = new ToDoList();
        toDoList.setOpenId(openId);
        List<ToDoList> toDoLists = this.listDao.queryToDo(toDoList);
        List<CalendarToDo> calendarToDos = new ArrayList<>();
        for (ToDoList toDoList1 : toDoLists){
//           System.out.println(toDoList1.getListPriority());
            CalendarToDo calendarToDo = new CalendarToDo();
            calendarToDo.setDate(toDoList1.getListTime());
            calendarToDo.setLevel(toDoList1.getListPriority());
            calendarToDos.add(calendarToDo);
        }
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < calendarToDos.size()-1 ; i++){
            for (int j = calendarToDos.size()-1 ; j > i; j--){
//                System.out.println(calendarToDos.get(j).getDate());
//                System.out.println(calendarToDos.get(i).getDate());
//                System.out.println(calendarToDos.get(j).getLevel());
//                System.out.println(calendarToDos.get(i).getLevel());
                String d1 = s.format(calendarToDos.get(j).getDate());
                String d2 = s.format(calendarToDos.get(i).getDate());
//                System.out.println(d1);
//                System.out.println(d2);
                if (d1.equals(d2)) {
                    if (calendarToDos.get(j).getLevel()>=calendarToDos.get(i).getLevel()){
                        calendarToDos.remove(j);
                    }else if (calendarToDos.get(j).getLevel()<calendarToDos.get(i).getLevel()){
                        calendarToDos.remove(i);
                    }
                }
            }
        }
        return calendarToDos;
    }

    /**
     * 未完成事项列表查询
     *
     */
//    @Override
    public List<ToDoList> queryToDo(String openId) {
        ToDoList toDoList = new ToDoList();
        toDoList.setOpenId(openId);
        return this.listDao.queryToDo(toDoList);
    }

    /**
     * 未完成事项列表提供(根据openId搜索）
     *
     */
    @Override
    public List<AllListModel> queryAllToDo(String openId){
        List<AllListModel> allListModels = new ArrayList<>();
        List<ToDoList> toDoLists = new ArrayList<>();
        toDoLists = queryToDo(openId);
//        toDoLists = queryToDoBy
        for(ToDoList toDoList:toDoLists){
            AllListModel allListModel = new AllListModel();
//            allListModel.setOpenId(openId);
            allListModel.setListId(toDoList.getListId());
            allListModel.setListTitle(toDoList.getListTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            allListModel.setListTime(sdf.format(toDoList.getListTime()));
            allListModels.add(allListModel);
        }
        return allListModels;
    }

    /**
     * 未完成事项列表提供(根据openId、tagName搜索）
     *
     */
    @Override
    public List<AllListModel> queryAllToDoByTag(String openId,String tagName){
        List<AllListModel> allListModelList = new ArrayList<>();

        List<AllListModel> allListModels = new ArrayList<>();
        List<ToDoList> toDoLists = new ArrayList<>();
        toDoLists = queryToDo(openId);
        for(ToDoList toDoList:toDoLists){
            AllListModel allListModel = new AllListModel();
//            allListModel.setOpenId(openId);
            allListModel.setListId(toDoList.getListId());
            allListModel.setListTitle(toDoList.getListTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            allListModel.setListTime(sdf.format(toDoList.getListTime()));
            allListModels.add(allListModel);
        }

        if(tagName.equals("全部")){
            allListModelList = allListModels;
        }else{
//        先用openid找到相应的全部listid
//        用listid找到tagid
//        用tagid找到tagname
//        如果tagname和想要的一样
//        就把这个list信息的放进新的list里
            for(AllListModel allListModel:allListModels){
                ListTag Find = new ListTag();
                Find.setListId(allListModel.getListId());
                List<ListTag> listTags = listTagDao.queryAll(Find);
                int n = listTags.size();
                for(int i=0; i<n;i++){
                    Tag tag = tagDao.queryById(listTags.get(i).getTagId());
//                    System.out.println("tagName1:"+tag.getTagName());
//                    System.out.println("tagName:"+tagName);
                    if(tag.getTagName().equals(tagName)) {
                        allListModelList.add(allListModel);
//                        System.out.println("allListModelList:"+allListModelList);
                    }
                }
//                for(ListTag list:listTags){
//                    Tag tag = tagDao.queryById(list.getTagId());
//                    if(tag.getTagName() == tagName){
//                        allListModelList.add(allListModel);
//                    }
//                }
            }
        }
        return allListModelList;
    }

    /**
     * 某个人建立过的所有tag（未完成）
     *
     */
//    @Override
    public List<Tag> queryallTag(String openId){
        List<Tag> tags = new ArrayList<>();
        List<AllListModel> allListModels = queryAllToDo(openId);
//        List<AllListModel> allListModels1 = queryAllFinish(openId);
//        for(AllListModel allListModel:allListModels1){
//            allListModels.add(allListModel);
//        }
        for(AllListModel allListModel:allListModels) {
            ListTag Find = new ListTag();
            Find.setListId(allListModel.getListId());
            List<ListTag> listTags = listTagDao.queryAll(Find);
            for (ListTag list : listTags) {
                Tag tag = tagDao.queryById(list.getTagId());
//                System.out.println("tag"+tag.getTagName());
                tags.add(tag);
            }
        }
        return tags;
    }

    /**
     * 某个人建立过的所有tag名（未完成）
     *
     */
    @Override
    public List<String> queryTagName(String openId){
        List<Tag> tags = queryallTag(openId);
//        System.out.println("size"+tags.size());
        List<String> tagNameList = new ArrayList<>();
        for(Tag tag:tags){
            tagNameList.add(tag.getTagName());
//            System.out.println("tagNameList"+tag.getTagName());
        }
        List<String> listTemp = new ArrayList<String>();
        listTemp.add("全部");
        for(int i=0;i<tagNameList.size();i++){
            if(!listTemp.contains(tagNameList.get(i))){
                listTemp.add(tagNameList.get(i));
            }
        }

//
//        List<String> tagNames = new ArrayList<>();
//
//        for(String tagName:tagNameList){
//            if(tagNames.isEmpty()){
//                tagNames.add(tagName);
//            }else{
////                System.out.println("tagName"+tagName+"tagNames"+tagNames.get(0));
//                int n = tagNames.size();
//                for(int i=0;i<n;i++){   //(int i=0;i<n;i++)
//                    if(!tagName.equals(tagNames.get(i))){
//                        tagNames.add(tagName);
//                    }
//                }
//            }
//        }
//        tagNames.add("全部");
//        List<String> tagNamelast = new ArrayList<>();
//        int n = tagNameList.size();
//        for(int i=n-1;i>=0;i--){
//            tagNamelast.add(tagNameList.get(i));
//        }
        return listTemp;
    }


    /**
     * 已完成事项列表查询
     *
     */
//    @Override
    public List<ToDoList> queryFinish(String openId) {
        ToDoList toDoList = new ToDoList();
        toDoList.setOpenId(openId);
        return this.listDao.queryFinish(toDoList);
    }

    /**
     * 已完成事项列表提供
     *
     */
    @Override
    public List<AllListModel> queryAllFinish(String openId){
        List<AllListModel> allListModels = new ArrayList<>();
        List<ToDoList> toDoLists = new ArrayList<>();
        toDoLists = queryFinish(openId);
        for(ToDoList toDoList:toDoLists){
            AllListModel allListModel = new AllListModel();
//            allListModel.setOpenId(openId);
            allListModel.setListId(toDoList.getListId());
            allListModel.setListTitle(toDoList.getListTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            allListModel.setListTime(sdf.format(toDoList.getListTime()));
            allListModels.add(allListModel);
        }
        return allListModels;
    }


    /**
     * 已完成事项列表提供(根据openId、tagName搜索）
     *
     */
    @Override
    public List<AllListModel> queryAllFinishByTag(String openId,String tagName){
        List<AllListModel> allListModelList = new ArrayList<>();

        List<AllListModel> allListModels = new ArrayList<>();
        List<ToDoList> toDoLists = new ArrayList<>();
        toDoLists = queryFinish(openId);
        for(ToDoList toDoList:toDoLists){
            AllListModel allListModel = new AllListModel();
//            allListModel.setOpenId(openId);
            allListModel.setListId(toDoList.getListId());
            allListModel.setListTitle(toDoList.getListTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            allListModel.setListTime(sdf.format(toDoList.getListTime()));
            allListModels.add(allListModel);
        }

        if(tagName.equals("全部")){
            allListModelList = allListModels;
        }else{
//        先用openid找到相应的全部listid
//        用listid找到tagid
//        用tagid找到tagname
//        如果tagname和想要的一样
//        就把这个list信息的放进新的list里
            for(AllListModel allListModel:allListModels){
                ListTag Find = new ListTag();
                Find.setListId(allListModel.getListId());
                List<ListTag> listTags = listTagDao.queryAll(Find);
                int n = listTags.size();
                for(int i=0; i<n;i++){
                    Tag tag = tagDao.queryById(listTags.get(i).getTagId());
//                    System.out.println("tagName1:"+tag.getTagName());
//                    System.out.println("tagName:"+tagName);
                    if(tag.getTagName().equals(tagName)) {
                        allListModelList.add(allListModel);
//                        System.out.println("allListModelList:"+allListModelList);
                    }
                }
//                for(ListTag list:listTags){
//                    Tag tag = tagDao.queryById(list.getTagId());
//                    if(tag.getTagName() == tagName){
//                        allListModelList.add(allListModel);
//                    }
//                }
            }
        }
        return allListModelList;
    }

    /**
     * 某个人建立过的所有tag（已完成）
     *
     */
//    @Override
    public List<Tag> queryallTag1(String openId){
        List<Tag> tags = new ArrayList<>();
        List<AllListModel> allListModels = queryAllFinish(openId);
//        List<AllListModel> allListModels1 = queryAllFinish(openId);
//        for(AllListModel allListModel:allListModels1){
//            allListModels.add(allListModel);
//        }
        for(AllListModel allListModel:allListModels) {
            ListTag Find = new ListTag();
            Find.setListId(allListModel.getListId());
            List<ListTag> listTags = listTagDao.queryAll(Find);
            for (ListTag list : listTags) {
                Tag tag = tagDao.queryById(list.getTagId());
//                System.out.println("tag"+tag.getTagName());
                tags.add(tag);
            }
        }
        return tags;
    }

    /**
     * 某个人建立过的所有tag名（已完成）
     *
     */
    @Override
    public List<String> queryTagName1(String openId){
        List<Tag> tags = queryallTag1(openId);
//        System.out.println("size"+tags.size());
        List<String> tagNameList = new ArrayList<>();
        for(Tag tag:tags){
            tagNameList.add(tag.getTagName());
//            System.out.println("tagNameList"+tag.getTagName());
        }
        List<String> listTemp = new ArrayList<String>();
        listTemp.add("全部");
        for(int i=0;i<tagNameList.size();i++){
            if(!listTemp.contains(tagNameList.get(i))){
                listTemp.add(tagNameList.get(i));
            }
        }

//
//        List<String> tagNames = new ArrayList<>();
//
//        for(String tagName:tagNameList){
//            if(tagNames.isEmpty()){
//                tagNames.add(tagName);
//            }else{
////                System.out.println("tagName"+tagName+"tagNames"+tagNames.get(0));
//                int n = tagNames.size();
//                for(int i=0;i<n;i++){   //(int i=0;i<n;i++)
//                    if(!tagName.equals(tagNames.get(i))){
//                        tagNames.add(tagName);
//                    }
//                }
//            }
//        }
//        tagNames.add("全部");
//        List<String> tagNamelast = new ArrayList<>();
//        int n = tagNameList.size();
//        for(int i=n-1;i>=0;i--){
//            tagNamelast.add(tagNameList.get(i));
//        }
        return listTemp;
    }



    /**
     * 查找Tags
     *
     */
//    @Override
    public List<Tag> Tags(Integer listId){
        List<Tag> tags = new ArrayList<>();
        ListTag listTag = new ListTag();
        listTag.setListId(listId);
//        System.out.println("listId："+listId);
        List<ListTag> listTags = listTagDao.queryAll(listTag);
//        System.out.println("listTags:"+listTags.size());
        if(listTags.size()!=0) {
            Tag tag = tagDao.queryById(listTags.get(0).getTagId());
            tags.add(tag);
        }
//        for(ListTag t : listTags){
//            Tag tag =tagDao.queryById(t.getListTagId());
////            System.out.println("tag："+tag);
////            System.out.println("tag："+tag.getTagName());
//            tags.add(tag);
//        }
        return tags;
    }

    /**
     * 事项明细提供
     *
     */
    @Override
    public ListDetailsModel queryDetails(String openId,Integer listId){
        ListDetailsModel listDetailsModel = new ListDetailsModel();
        ToDoList toDoList = queryById(listId);
        listDetailsModel.setListId(toDoList.getListId());
        listDetailsModel.setListContent(toDoList.getListContent());
        listDetailsModel.setListTitle(toDoList.getListTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        listDetailsModel.setListTime(sdf.format(toDoList.getListTime()));
        Integer priority = toDoList.getListPriority();
        String priorityName = "";
//      1、重要且紧急。2、重要不紧急。3、不重要但紧急。4、不重要且不紧急。
        if(priority == 1){
            priorityName = "重要且紧急";
        }
        if(priority == 2){
            priorityName = "重要非紧急";
        }
        if(priority == 3){
            priorityName = "紧急非重要";
        }
        if(priority == 4){
            priorityName = "非紧急重要";
        }
        listDetailsModel.setListPriorityName(priorityName);
        listDetailsModel.setOpenId(openId);
//        System.out.println("Tags(listId).size():"+Tags(listId).size());
//        System.out.println("Tags(listId).get(0):"+Tags(listId).get(0));
        if(Tags(listId).size() != 0) {
            listDetailsModel.setTag(Tags(listId).get(0));
        }else{
            Tag tag = new Tag();
            tag.setTagName("全部");
            listDetailsModel.setTag(tag);
        }
//        listDetailsModel.setTagList(Tags(listId));
        return listDetailsModel;
    }

    /**
     * 通过标题搜索未完成的事项
     *
     */
    @Override
    public List<AllListModel> queryAllToDoByTitle(String openId,String listTitle){
        List<AllListModel> allListModels = new ArrayList<>();
//        System.out.println("openId:"+openId);
        if(!listTitle.equals("")) {
            ToDoList toDoList = new ToDoList();
            toDoList.setOpenId(openId);
            toDoList.setListTitle(listTitle);
            List<ToDoList> toDoLists = this.listDao.queryToDoByTitle(toDoList);
//            System.out.println("toDoLists:"+toDoLists);
            for (ToDoList toDoList1 : toDoLists) {
                AllListModel allListModel = new AllListModel();
                allListModel.setListId(toDoList1.getListId());
                allListModel.setListTitle(toDoList1.getListTitle());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                allListModel.setListTime(sdf.format(toDoList1.getListTime()));
                allListModels.add(allListModel);
            }
        }else{
            allListModels = queryAllToDoByTag(openId,"全部");
        }
        return allListModels;
    }

    /**
     * 通过标题搜索未完成的事项
     *
     */
    @Override
    public List<AllListModel> queryAllToDoByTitleAndTag(String openId,String listTitle,String tagName){
        List<AllListModel> allListModels = new ArrayList<>();
//        System.out.println("openId:"+openId);
        if(!tagName.equals("全部")) {
            if (!listTitle.equals("")) {
                ToDoList toDoList = new ToDoList();
                toDoList.setOpenId(openId);
                toDoList.setListTitle(listTitle);
                List<ToDoList> toDoLists = this.listDao.queryToDoByTitle(toDoList);
//            System.out.println("toDoLists:"+toDoLists);
                List<ToDoList> toDoLists1 = new ArrayList<>();
                for (ToDoList list : toDoLists) {
                    ListTag listTag = new ListTag();
                    listTag.setListId(list.getListId());
                    String Name = listTagDao.queryAll(listTag).get(0).getTag().getTagName();
                    if (Name == tagName) {
                        toDoLists1.add(list);
                    }
                }
                for (ToDoList toDoList1 : toDoLists1) {
                    AllListModel allListModel = new AllListModel();
                    allListModel.setListId(toDoList1.getListId());
                    allListModel.setListTitle(toDoList1.getListTitle());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    allListModel.setListTime(sdf.format(toDoList1.getListTime()));
                    allListModels.add(allListModel);
                }
            } else {
                allListModels = queryAllToDoByTag(openId, "全部");
            }
        }else{
            allListModels = queryAllToDoByTag(openId, "全部");
        }
        return allListModels;
    }

    /**
     * 通过标题搜索已完成的事项
     *
     */
    @Override
    public List<AllListModel> queryAllFinishByTitle(String openId,String listTitle){
        List<AllListModel> allListModels = new ArrayList<>();
        if(!listTitle.equals("")) {
            ToDoList toDoList = new ToDoList();
            toDoList.setOpenId(openId);
            toDoList.setListTitle(listTitle);
            List<ToDoList> toDoLists = this.listDao.queryFinishByTitle(toDoList);
            for (ToDoList toDoList1 : toDoLists) {
                AllListModel allListModel = new AllListModel();
                allListModel.setListId(toDoList1.getListId());
                allListModel.setListTitle(toDoList1.getListTitle());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                allListModel.setListTime(sdf.format(toDoList1.getListTime()));
                allListModels.add(allListModel);
            }
        }else{
            allListModels = queryAllFinishByTag(openId,"全部");
        }
        return allListModels;
    }


    /**
     * 通过标题搜索未完成的事项
     *
     */
    @Override
    public List<AllListModel> queryAllFinishByTitleAndTag(String openId,String listTitle,String tagName){
        List<AllListModel> allListModels = new ArrayList<>();
//        System.out.println("openId:"+openId);
        if(!tagName.equals("全部")) {
            if (!listTitle.equals("")) {
                ToDoList toDoList = new ToDoList();
                toDoList.setOpenId(openId);
                toDoList.setListTitle(listTitle);
                List<ToDoList> toDoLists = this.listDao.queryFinishByTitle(toDoList);
//            System.out.println("toDoLists:"+toDoLists);
                List<ToDoList> toDoLists1 = new ArrayList<>();
                for (ToDoList list : toDoLists) {
                    ListTag listTag = new ListTag();
                    listTag.setListId(list.getListId());
                    String Name = listTagDao.queryAll(listTag).get(0).getTag().getTagName();
                    if (Name == tagName) {
                        toDoLists1.add(list);
                    }
                }
                for (ToDoList toDoList1 : toDoLists1) {
                    AllListModel allListModel = new AllListModel();
                    allListModel.setListId(toDoList1.getListId());
                    allListModel.setListTitle(toDoList1.getListTitle());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    allListModel.setListTime(sdf.format(toDoList1.getListTime()));
                    allListModels.add(allListModel);
                }
            } else {
                allListModels = queryAllFinishByTag(openId, "全部");
            }
        }else{
            allListModels = queryAllFinishByTag(openId, "全部");
        }
        return allListModels;
    }


    /**
     * 通过时间搜索未完成的事项
     *
     */
    @Override
    public List<AllListModel> queryAllToDoByTime(String openId,String listTime1,String listTime2) throws ParseException {

//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = sd.parse(listTime);
//        long times = date.getTime();
//        long times1 = times + + 1L*24 * 60 * 60 * 1000;
//        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times)));
////        System.out.println(date1);
//        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times1)));
////        System.out.println(date2);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date dateA = sd.parse(listTime1);
        Date dateB = sd.parse(listTime2);
        long times1 = dateA.getTime();
        long times2 = dateB.getTime() + + 1L*24 * 60 * 60 * 1000;
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times1)));
//        System.out.println(date1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times2)));
//        System.out.println(date2);


        List<ToDoList> toDoLists = this.listDao.queryAllToDoByTime(date1,date2,openId);
        List<AllListModel> allListModels = new ArrayList<>();
        for(ToDoList toDoList1:toDoLists){
            AllListModel allListModel = new AllListModel();
            allListModel.setListId(toDoList1.getListId());
            allListModel.setListTitle(toDoList1.getListTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            allListModel.setListTime(sdf.format(toDoList1.getListTime()));
            allListModels.add(allListModel);
        }
        return allListModels;
    }

    /**
     * 通过时间搜索已完成的事项
     *
     */
    @Override
    public List<AllListModel> queryAllFinishByTime(String openId,String listTime1,String listTime2) throws ParseException {

//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = sd.parse(listTime);
//        long times = date.getTime();
//        long times1 = times + + 1L*24 * 60 * 60 * 1000;
//        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times)));
////        System.out.println(date1);
//        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times1)));
////        System.out.println(date2);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date dateA = sd.parse(listTime1);
        Date dateB = sd.parse(listTime2);
        long times1 = dateA.getTime();
        long times2 = dateB.getTime() + + 1L*24 * 60 * 60 * 1000;
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times1)));
//        System.out.println(date1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sd.format(new Date(times2)));
//        System.out.println(date2);


        List<ToDoList> toDoLists = this.listDao.queryAllFinishByTime(date1,date2,openId);
        List<AllListModel> allListModels = new ArrayList<>();
        for(ToDoList toDoList1:toDoLists){
            AllListModel allListModel = new AllListModel();
            allListModel.setListId(toDoList1.getListId());
            allListModel.setListTitle(toDoList1.getListTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            allListModel.setListTime(sdf.format(toDoList1.getListTime()));
            allListModels.add(allListModel);
        }
        return allListModels;
    }

    /**
     * 添加待办事项
     *
     */
    @Override
    public void insertNewList(ListDetailsModel listDetailsModel) throws ParseException {
        String listNo = addList(listDetailsModel);
        ToDoList toDoList = new ToDoList();
        toDoList.setListNo(listNo);
        Integer listId = listDao.queryAll(toDoList).get(0).getListId();
        String tagName = listDetailsModel.getTag().getTagName();
//        System.out.println("tagName:"+tagName);
        if (!tagName.equals("全部")) {
            Integer tagId = tagDao.queryByTagName(tagName).getTagId();
//            System.out.println("tagId:"+tagId);
            addListTag(tagId, listId);
        }
    }

    /**
     * 添加List
     *
     */
//    @Override
    public String addList(ListDetailsModel listDetailsModel) throws ParseException {
        ToDoList toDoList = new ToDoList();
        String listPriorityName = listDetailsModel.getListPriorityName();
        Integer priority = 0;
        if(listPriorityName .equals("重要且紧急")){
            priority = 1;
        }
        if(listPriorityName .equals("重要非紧急")){
            priority = 2;
        }
        if(listPriorityName .equals("紧急非重要")){
            priority = 3;
        }
        if(listPriorityName .equals("非紧急重要")){
            priority = 4;
        }
        String No = RandomStringUtils.random(5, false, true);
//        while(whetherNo(No))
//            No = RandomStringUtils.random(5, false, true);
        toDoList.setListNo(No);
        toDoList.setListTitle(listDetailsModel.getListTitle());
        toDoList.setListContent(listDetailsModel.getListContent());
        toDoList.setListStatus(0);
        toDoList.setListPriority(priority);
//        toDoList.setListTime(dateUtils.getNowDate());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sd.parse(listDetailsModel.getListTime());
        toDoList.setListTime(date);
        toDoList.setOpenId(listDetailsModel.getOpenId());
        listDao.insert(toDoList);
        return No;
    }

    /**
     * 排除出现一样的id
     */
    public boolean whetherNo(String No){
        ToDoList toDoList = new ToDoList();
        if(listDao.queryAll(toDoList).size()!=0){
            List<ToDoList> toDoLists= listDao.queryAll(toDoList);
            for (int i = 0; i < toDoLists.size(); i++) {
                String s = toDoLists.get(i).getListNo();
                if(!No.equals(s))
                    return true;
            }
        }else{
            return true;
        }
        return false;
    }

//    /**
//     * 添加Tag
//     *
//     */
////    @Override
//    public Integer addTag(ListDetailsModel listDetailsModel){
//        Tag tag = new Tag();
//
//    }

    /**
     * 添加ListTag
     *
     */
//    @Override
    public void addListTag(Integer tagId,Integer listId){
        ListTag listTag = new ListTag();
        listTag.setTagId(tagId);
        listTag.setListId(listId);
        listTagDao.insert(listTag);
    }

    /**
     * 未完成更改成已完成
     *
     */
    @Override
    public void toDoToFinish(String openId, Integer listId){
        ToDoList toDoList = new ToDoList();
        toDoList.setListId(listId);
        toDoList.setOpenId(openId);
        List<ToDoList> toDoLists = listDao.queryAll(toDoList);
        ToDoList toDoList1 = toDoLists.get(0);
        toDoList1.setListStatus(1);
        listDao.update(toDoList1);
    }

    /**
     * 已完成更改成未完成
     *
     */
    @Override
    public void FinishTotoDo(String openId, Integer listId){
        ToDoList toDoList = new ToDoList();
        toDoList.setListId(listId);
        toDoList.setOpenId(openId);
        List<ToDoList> toDoLists = listDao.queryAll(toDoList);
        ToDoList toDoList1 = toDoLists.get(0);
        toDoList1.setListStatus(0);
        listDao.update(toDoList1);
    }

    /**
     * 修改待办事项
     *
     */
    @Override
    public void updateNewList(ListDetailsModel listDetailsModel) throws ParseException {
        updateList(listDetailsModel);

        Integer listId = listDetailsModel.getListId();

        String tagName = listDetailsModel.getTag().getTagName();
        if(!tagName.equals("全部")) {
            //通过tagName找到tagId
            System.out.println("tagName:"+tagName);
            Tag tag = new Tag();
            tag.setTagName(tagName);
            Integer tagId = tagDao.queryByTagName(tagName).getTagId();
            //通过listId找到对应listTag
            ListTag listTag = new ListTag();
            listTag.setListId(listId);
            if(listTagDao.queryAll(listTag).size() != 0) {
                ListTag listTag1 = listTagDao.queryAll(listTag).get(0);
                listTag1.setTagId(tagId);
                updateListTag(listTag1);
            }else{
                ListTag listTag1 = new ListTag();
                listTag1.setListId(listId);
                listTag1.setTagId(tagId);
                listTagDao.insert(listTag1);
            }
        }else{
            //通过listId找到对应listTag
            ListTag listTag = new ListTag();
            listTag.setListId(listId);
            if(listTagDao.queryAll(listTag).size() != 0) {
                listTagDao.deleteById(listTagDao.queryAll(listTag).get(0).getListTagId());
            }
        }
    }

    /**
     * 修改List
     *
     */
//    @Override
    public void updateList(ListDetailsModel listDetailsModel) throws ParseException {
        ToDoList toDoList = new ToDoList();
        String listPriorityName = listDetailsModel.getListPriorityName();
        Integer priority = 0;
        if(listPriorityName .equals("重要且紧急")){
            priority = 1;
        }
        if(listPriorityName .equals("重要非紧急")){
            priority = 2;
        }
        if(listPriorityName .equals("紧急非重要")){
            priority = 3;
        }
        if(listPriorityName .equals("非紧急重要")){
            priority = 4;
        }
        toDoList.setListId(listDetailsModel.getListId());
        toDoList.setListTitle(listDetailsModel.getListTitle());
        toDoList.setListContent(listDetailsModel.getListContent());
        ToDoList a = listDao.queryById(listDetailsModel.getListId());
        toDoList.setListStatus(a.getListStatus());
        toDoList.setListPriority(priority);
//        toDoList.setListTime(dateUtils.getNowDate());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sd.parse(listDetailsModel.getListTime());
        toDoList.setListTime(date);
        toDoList.setOpenId(listDetailsModel.getOpenId());
        listDao.update(toDoList);
    }


    /**
     * 修改ListTag
     *
     */
//    @Override
    public void updateListTag(ListTag listTag){
        listTagDao.update(listTag);
    }

    /**
     * 修改ListTag
     *
     */
    @Override
    public void deleteList(Integer listId){
        listDao.deleteById(listId);
        Integer id = listTagDao.queryByListId(listId).getListId();
        listTagDao.deleteById(id);
    }

}
