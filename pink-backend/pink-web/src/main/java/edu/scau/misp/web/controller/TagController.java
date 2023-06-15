package edu.scau.misp.web.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scau.misp.todo.entity.Tag;
import edu.scau.misp.todo.entity.WechatUser;
import edu.scau.misp.todo.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Tag)表控制层
 *
 * @author makejava
 * @since 2022-11-03 13:20:58
 */
@RestController
@RequestMapping("tag")
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;

    /**
     * 分页查询
     * @param pageNum 起始页
     * @param pageSize 每页数据大小
     * @param tag 筛选条件
     * @return 查询结果
     */
    @GetMapping("/page")
    public PageInfo page(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, Tag tag) {
        PageHelper.startPage(pageNum,pageSize);
        List<Tag> list = tagService.queryAll(tag);
        PageInfo<List> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tagId 主键
     * @return 单条数据
     */
    @GetMapping("{tagId}")
    public Tag queryById(@PathVariable("tagId") Integer tagId) {
        return this.tagService.queryById(tagId);
    }

    /**
     * 新增数据
     *
     * @param tag 实体
     * @return 新增结果
     */
    @PostMapping
    public String add(Tag tag) {
        this.tagService.insert(tag);
        return "ok";
    }

    /**
     * 编辑数据
     *
     * @param tag 实体
     * @return 编辑结果
     */
    @PutMapping
    public String edit(Tag tag) {
        this.tagService.update(tag);
        return "ok";
    }

    /**
     * 删除数据
     *
     * @param tagId 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public Boolean deleteById(@PathVariable("tagId") Integer tagId) {
        return this.tagService.deleteById(tagId);
    }

}

