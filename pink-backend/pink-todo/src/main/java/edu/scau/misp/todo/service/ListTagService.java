package edu.scau.misp.todo.service;

import edu.scau.misp.todo.entity.ListTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (ListTag)表服务接口
 *
 * @author makejava
 * @since 2022-11-03 13:20:42
 */
public interface ListTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param listTagId 主键
     * @return 实例对象
     */
    ListTag queryById(Integer listTagId);

    /**
     * 分页查询
     *
     * @param listTag 筛选条件
     * @return 查询结果
     */
    List<ListTag> queryAll(ListTag listTag);

    /**
     * 新增数据
     *
     * @param listTag 实例对象
     * @return 实例对象
     */
    ListTag insert(ListTag listTag);

    /**
     * 修改数据
     *
     * @param listTag 实例对象
     * @return 实例对象
     */
    ListTag update(ListTag listTag);

    /**
     * 通过主键删除数据
     *
     * @param listTagId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer listTagId);

}
