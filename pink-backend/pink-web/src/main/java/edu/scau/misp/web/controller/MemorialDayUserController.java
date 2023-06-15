package edu.scau.misp.web.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scau.misp.sys.entity.ResultEntity;
import edu.scau.misp.todo.entity.MemorialDayUser;
import edu.scau.misp.todo.model.CalDay;
import edu.scau.misp.todo.model.DateList;
import edu.scau.misp.todo.model.MemorialDayList;
import edu.scau.misp.todo.model.MemorialList;
import edu.scau.misp.todo.service.MemorialDayUserService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 小程序纪念日用户关系表(MemorialDayUser)表控制层
 *
 * @author makejava
 * @since 2022-11-03 10:37:39
 */
@RestController
@RequestMapping("memorialDayUser")
public class MemorialDayUserController {
    /**
     * 服务对象
     */
    @Resource
    private MemorialDayUserService memorialDayUserService;

    /**
     * 查询列表
     *
     * @param memorialDayUser 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public List<MemorialDayUser> list(MemorialDayUser memorialDayUser) {
        return this.memorialDayUserService.queryAll(memorialDayUser);
    }

    /**
     * 查询 openId 列表(天数是跟当前日期比)——置顶的一条纪念日展示
     *
     * @param openId 筛选条件
     * @return 查询结果
     */
    @GetMapping("/top")
    public ResultEntity queryByTop(@RequestParam("openId") String openId) {
        return ResultEntity.success("成功",this.memorialDayUserService.queryByTop(openId));
    }

    /**
     * 查询 openId 列表(天数是跟当前日期比)——非置顶的列表展示
     *
     * @param openId 筛选条件
     * @return 查询结果
     */
    @GetMapping("/memorialList")
    public ResultEntity queryByOpenId(@RequestParam("openId") String openId) {
        return ResultEntity.success("成功",this.memorialDayUserService.queryByOpenId(openId));
    }

    /**
     * 查询 openId 获取日历上的纪念日日期列表 @RequestBody CalDay calDay
     *
     * @param openId
     * @param time
     * @return
     */
    @GetMapping("/dateList")
    public ResultEntity queryDate(@RequestParam("openId") String openId, @RequestParam("date")String time) {
        System.out.println(openId);
        System.out.println(time);
        CalDay calDay =new CalDay();
        calDay.setOpenId(openId);
        String format = "yyyy-MM-dd";
        Date date = null;
        try {
            date = DateUtils.parseDate(time, format, "yyyy-MM-dd");
            calDay.setYm(date);
            System.out.println(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResultEntity.success("成功",this.memorialDayUserService.queryDate(calDay));
    }

    /**
     * 获取具体某一天的所有纪念日(根据openId和所给的日期搜索/天数是跟所给的日期比) DateList dateList
     *
     */
    @GetMapping("/queryMemorialDay")
    public ResultEntity queryMemorialDay(@RequestParam("openId") String openId,@RequestParam("date")String time) {
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
        return ResultEntity.success("成功",this.memorialDayUserService.queryMemorialDay(dateList));
    }

    /**
     * 分页查询
     *
     * @param pageNum 起始页
     * @param pageSize 每页数据大小
     * @param memorialDayUser 筛选条件
     * @return 查询结果
     */
    @GetMapping
    public PageInfo page(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, MemorialDayUser memorialDayUser) {
        PageHelper.startPage(pageNum,pageSize);
        List<MemorialDayUser> list = memorialDayUserService.queryAll(memorialDayUser);
        PageInfo<List> pageInfo = new PageInfo(list);
        return  pageInfo;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param memorialDayUserId 主键
     * @return 单条数据
     */
    @GetMapping("{memorialDayUserId}")
    public MemorialDayUser queryById(@PathVariable("memorialDayUserId") Integer memorialDayUserId) {
        return this.memorialDayUserService.queryById(memorialDayUserId);
    }

    /**
     * 新增数据
     *
     * @param memorialDayList 实体
     * @return 新增结果
     */
    @PostMapping
    public String add(@RequestBody MemorialDayList memorialDayList) {
        this.memorialDayUserService.insert(memorialDayList);
        return "ok";
    }

    /**
     * 编辑数据
     *
     * @param memorialDayList 实体
     * @return 编辑结果
     */
    @PutMapping
    public String edit(@RequestBody MemorialDayList memorialDayList) {
        this.memorialDayUserService.update(memorialDayList);
        return "ok";
    }

    /**
     * 删除数据
     *
     * @param memorialDayUserId 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{memorialDayUserId}")
    public Boolean deleteById(@PathVariable("memorialDayUserId") Integer memorialDayUserId) {
        return this.memorialDayUserService.deleteById(memorialDayUserId);
    }

}

