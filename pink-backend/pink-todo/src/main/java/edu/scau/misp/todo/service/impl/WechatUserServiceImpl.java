package edu.scau.misp.todo.service.impl;

import edu.scau.misp.todo.entity.WechatUser;
import edu.scau.misp.todo.dao.WechatUserDao;
import edu.scau.misp.todo.service.WechatUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 小程序用户表(WechatUser)表服务实现类
 *
 * @author makejava
 * @since 2022-10-23 22:26:14
 */
@Service
public class WechatUserServiceImpl implements WechatUserService {
    @Resource
    private WechatUserDao wechatUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WechatUser queryById(Integer id) {
        return this.wechatUserDao.queryById(id);
    }

//    /**
//     * 分页查询
//     *
//     * @param wechatUser 筛选条件
//     * @param pageRequest      分页对象
//     * @return 查询结果
//     */
//    @Override
//    public Page<WechatUser> queryByPage(WechatUser wechatUser, PageRequest pageRequest) {
//        long total = this.wechatUserDao.count(wechatUser);
//        return new PageImpl<>(this.wechatUserDao.queryAllByLimit(wechatUser, pageRequest), pageRequest, total);
//    }

    /**
     * 查询指定行数据
     *
     * @param wechatUser 筛选条件
     * @return 对象列表
     */
    @Override
    public List<WechatUser> queryAll(WechatUser wechatUser) {
        return this.wechatUserDao.queryAll(wechatUser);
    }

    /**
     * 新增数据
     *
     * @param wechatUser 实例对象
     * @return 实例对象
     */
    @Override
    public WechatUser insert(WechatUser wechatUser) {
        this.wechatUserDao.insert(wechatUser);
        return wechatUser;
    }

    /**
     * 修改数据
     *
     * @param wechatUser 实例对象
     * @return 实例对象
     */
    @Override
    public void update(WechatUser wechatUser) {
        this.wechatUserDao.update(wechatUser);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.wechatUserDao.deleteById(id) > 0;
    }

    /**
     * 通过openId查询单条数据
     *
     * @param openId 主键
     * @return 实例对象
     */
    @Override
    public WechatUser getByOpenId(String openId) {
        return this.wechatUserDao.getByOpenId(openId);
    }

    /**
     * 修改数据byopenid
     *
     * @param wechatUser 实例对象
     * @return 实例对象
     */
    @Override
    public void updateByOpenId(WechatUser wechatUser) {
        this.wechatUserDao.updateByOpenId(wechatUser);
    }
}
