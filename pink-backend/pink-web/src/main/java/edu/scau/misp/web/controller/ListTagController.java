package edu.scau.misp.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scau.misp.todo.entity.ListTag;
import edu.scau.misp.todo.entity.ToDoList;
import edu.scau.misp.todo.service.ListTagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ListTag)表控制层
 *
 * @author makejava
 * @since 2022-11-03 13:20:41
 */
@RestController
@RequestMapping("listTag")
public class ListTagController {
    /**
     * 服务对象
     */
    @Resource
    private ListTagService listTagService;

    /**
     * 分页查询
     *
     * @param pageNum 起始页
     * @param pageSize 每页数据大小
     * @param listTag 筛选条件
     * @return 查询结果
     */
    @GetMapping("/page")
    public PageInfo page(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, ListTag listTag) {
        PageHelper.startPage(pageNum,pageSize);
        List<ListTag> list = listTagService.queryAll(listTag);
        PageInfo<List> pageInfo = new PageInfo(list);
        return pageInfo;
    }


    /**
     * 通过主键查询单条数据
     *
     * @param listTagId 主键
     * @return 单条数据
     */
    @GetMapping("{listTagId}")
    public ListTag queryById(@PathVariable("listTagId") Integer listTagId) {
        return this.listTagService.queryById(listTagId);
    }

    /**
     * 新增数据
     *
     * @param listTag 实体
     * @return 新增结果
     */
    @PostMapping
    public String add(@RequestBody ListTag listTag) {
        this.listTagService.insert(listTag);
        return "ok";
    }

    /**
     * 编辑数据
     *
     * @param listTag 实体
     * @return 编辑结果
     */
    @PutMapping
    public String edit(@RequestBody ListTag listTag) {
        this.listTagService.update(listTag);
        return "ok";
    }

    /**
     * 删除数据
     *
     * @param listTagId 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{listTagId}")
    public Boolean deleteById(@PathVariable("listTagId") Integer listTagId) {
        return this.listTagService.deleteById(listTagId);
    }

}

