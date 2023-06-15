package edu.scau.misp.todo.service.impl;

import edu.scau.misp.todo.entity.ListTag;
import edu.scau.misp.todo.dao.ListTagDao;
import edu.scau.misp.todo.entity.MemorialDay;
import edu.scau.misp.todo.service.ListTagService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ListTag)表服务实现类
 *
 * @author makejava
 * @since 2022-11-03 13:20:42
 */
@Service("listTagService")
public class ListTagServiceImpl implements ListTagService {
    @Resource
    private ListTagDao listTagDao;

    /**
     * 通过ID查询单条数据
     *
     * @param listTagId 主键
     * @return 实例对象
     */
    @Override
    public ListTag queryById(Integer listTagId) {
        return this.listTagDao.queryById(listTagId);
    }

    /**
     * 查询指定行数据
     *
     * @param  listTag 筛选条件
     * @return 对象列表
     */
    @Override
    public List<ListTag> queryAll(ListTag listTag) {
        return this.listTagDao.queryAll(listTag);
    }
//    /**
//     * 分页查询
//     *
//     * @param listTag 筛选条件
//     * @param pageRequest      分页对象
//     * @return 查询结果
//     */
//    @Override
//    public Page<ListTag> queryByPage(ListTag listTag, PageRequest pageRequest) {
//        long total = this.listTagDao.count(listTag);
//        return new PageImpl<>(this.listTagDao.queryAllByLimit(listTag, pageRequest), pageRequest, total);
//    }

    /**
     * 新增数据
     *
     * @param listTag 实例对象
     * @return 实例对象
     */
    @Override
    public ListTag insert(ListTag listTag) {
        this.listTagDao.insert(listTag);
        return listTag;
    }

    /**
     * 修改数据
     *
     * @param listTag 实例对象
     * @return 实例对象
     */
    @Override
    public ListTag update(ListTag listTag) {
        this.listTagDao.update(listTag);
        return this.queryById(listTag.getListTagId());
    }

    /**
     * 通过主键删除数据
     *
     * @param listTagId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer listTagId) {
        return this.listTagDao.deleteById(listTagId) > 0;
    }
}
