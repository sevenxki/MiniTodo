package edu.scau.misp.todo.dao;

import edu.scau.misp.todo.entity.ListTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (ListTag)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-03 13:20:42
 */
@Mapper
@Repository
public interface ListTagDao {

    /**
     * 通过ID查询单条数据
     *
     * @param listTagId 主键
     * @return 实例对象
     */
    ListTag queryById(Integer listTagId);

    /**
     * 查询指定行数据
     *
     * @param listTag 查询条件
     * @return 对象列表
     */
    List<ListTag> queryAll(ListTag listTag);

    /**
     * 统计总行数
     *
     * @param listTag 查询条件
     * @return 总行数
     */
    long count(ListTag listTag);

    /**
     * 新增数据
     *
     * @param listTag 实例对象
     * @return 影响行数
     */
    int insert(ListTag listTag);

//    /**
//     * 批量新增数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<ListTag> 实例对象列表
//     * @return 影响行数
//     */
//    int insertBatch(@Param("entities") List<ListTag> entities);
//
//    /**
//     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<ListTag> 实例对象列表
//     * @return 影响行数
//     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
//     */
//    int insertOrUpdateBatch(@Param("entities") List<ListTag> entities);

    /**
     * 修改数据
     *
     * @param listTag 实例对象
     * @return 影响行数
     */
    int update(ListTag listTag);

    /**
     * 通过主键删除数据
     *
     * @param listTagId 主键
     * @return 影响行数
     */
    int deleteById(Integer listTagId);

    /**
     * 查询指定行数据
     *
     * @param listTag 查询条件
     * @return 对象列表
     */
    List<ListTag> queryTags(ListTag listTag);

    /**
     * 通过ListID查询单条数据
     *
     * @param listId 主键
     * @return 实例对象
     */
    ListTag queryByListId(Integer listId);

}

