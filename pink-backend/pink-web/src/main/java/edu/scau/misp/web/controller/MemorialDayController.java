package edu.scau.misp.web.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scau.misp.sys.entity.ResultEntity;
import edu.scau.misp.todo.entity.MemorialDay;
import edu.scau.misp.todo.model.MemorialDayList;
import edu.scau.misp.todo.service.MemorialDayService;
import edu.scau.misp.todo.service.MemorialDayService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 小程序纪念日表(MemorialDay)表控制层
 *
 * @author makejava
 * @since 2022-11-03 10:01:29
 */
@RestController
@RequestMapping("memorialDay")
public class MemorialDayController {
    /**
     * 服务对象
     */
    @Resource
    private MemorialDayService memorialDayService;

    /**
     * 查询列表
     *
     * @param memorialDay 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public List<MemorialDay> list(MemorialDay memorialDay) {
        return this.memorialDayService.queryAll(memorialDay);
    }

    /**
     * 分页查询
     *
     * @param pageNum 起始页
     * @param pageSize 每页数据大小
     * @param memorialDay 筛选条件
     * @return 查询结果
     */
    @GetMapping("/page")
    public PageInfo page(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, MemorialDay memorialDay) {
        PageHelper.startPage(pageNum,pageSize);
        List<MemorialDay> list = memorialDayService.queryAll(memorialDay);
        PageInfo<List> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param memorialDayId 主键
     * @return 单条数据
     */
    @GetMapping("{memorialDayId}")
    public ResultEntity queryById(@PathVariable("memorialDayId") Integer memorialDayId) {
        return ResultEntity.success("成功",this.memorialDayService.queryById(memorialDayId));
    }

    @GetMapping("type/{memorialDayId}")
    public ResultEntity queryByIdAddType(@PathVariable("memorialDayId") Integer memorialDayId) {
        return ResultEntity.success("成功",this.memorialDayService.queryByIdAddType(memorialDayId));
    }

    /**
     * 新增数据
     *
     * @param memorialDayList 实体
     * @return 新增结果
     */
    @PostMapping
    public ResultEntity add(@RequestBody MemorialDayList memorialDayList) {
        this.memorialDayService.insert(memorialDayList);
        return ResultEntity.success("成功","ok");
    }

    /**
     * 编辑数据
     *
     * @param memorialDayList 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResultEntity edit(@RequestBody MemorialDayList memorialDayList) {
        this.memorialDayService.update(memorialDayList);
        return ResultEntity.success("成功","ok");
    }

    /**
     * 删除数据
     *
     * @param memorialDayId 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{memorialDayId}")
    public ResultEntity deleteById(@PathVariable("memorialDayId") Integer memorialDayId) {
        return ResultEntity.success("成功",this.memorialDayService.deleteById(memorialDayId));
    }

}

