package edu.scau.misp.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scau.misp.todo.entity.WechatUser;
import edu.scau.misp.todo.service.WechatUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 小程序用户表(WechatUser)表控制层
 *
 * @author makejava
 * @since 2022-10-23 22:25:21
 */
@RestController
@RequestMapping("wechatUser")
public class WechatUserController {
    /**
     * 服务对象
     */
    @Resource
    private WechatUserService wechatUserService;

    /**
     * 查询列表
     *
     * @param wechatUser 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public List<WechatUser> list(WechatUser wechatUser) {
        return this.wechatUserService.queryAll(wechatUser);
    }

    /**
     * 分页查询
     * @param pageNum 起始页
     * @param pageSize 每页数据大小
     * @param wechatUser 筛选条件
     * @return 查询结果
     */
    @GetMapping("/page")
    public PageInfo page(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, WechatUser wechatUser) {
        PageHelper.startPage(pageNum,pageSize);
        List<WechatUser> list = wechatUserService.queryAll(wechatUser);
        PageInfo<List> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public WechatUser getById(@PathVariable("id") Integer id) {
        return this.wechatUserService.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param wechatUser 实体
     * @return 新增结果
     */
    @PostMapping
    public String add(@RequestBody WechatUser wechatUser) {
        this.wechatUserService.insert(wechatUser);
        return "ok";
    }

    /**
     * 编辑数据
     * @param wechatUser 实体
     * @return 编辑结果
     */
    @PutMapping
    public String edit(@RequestBody WechatUser wechatUser) {
        this.wechatUserService.update(wechatUser);
        return "ok";
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{id}")
    public Boolean deleteById(@PathVariable("id") Integer id) {
        return this.wechatUserService.deleteById(id);
    }

}

