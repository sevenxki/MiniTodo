package edu.scau.misp.todo.service;

import edu.scau.misp.todo.entity.WechatUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 小程序用户表(WechatUser)表服务接口
 *
 * @author makejava
 * @since 2022-10-23 22:26:13
 */
public interface WechatUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WechatUser queryById(Integer id);

//    /**
//     * 分页查询
//     *
//     * @param wechatUser 筛选条件
//     * @param pageRequest      分页对象
//     * @return 查询结果
//     */
//    Page<WechatUser> queryAll(WechatUser wechatUser, PageRequest pageRequest);

    List<WechatUser> queryAll(WechatUser wechatUser);

    /**
     * 新增数据
     *
     * @param wechatUser 实例对象
     * @return 实例对象
     */
    WechatUser insert(WechatUser wechatUser);

    /**
     * 修改数据
     *
     * @param wechatUser 实例对象
     * @return 实例对象
     */
    void update(WechatUser wechatUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     *通过openId查询单条数据
     *
     */
    WechatUser getByOpenId(String openId);

    /**
     *修改数据byopenid
     *
     */
    void updateByOpenId(WechatUser wechatUser);

}
