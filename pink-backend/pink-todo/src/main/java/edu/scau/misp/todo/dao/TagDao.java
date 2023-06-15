package edu.scau.misp.todo.dao;

import edu.scau.misp.todo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-03 13:20:58
 */
@Mapper
@Repository
public interface TagDao {

    /**
     * 通过ID查询单条数据
     *
     * @param tagId 主键
     * @return 实例对象
     */
    Tag queryById(Integer tagId);

    /**
     * 查询指定行数据
     *
     * @param tag 查询条件
     * @return 对象列表
     */
    List<Tag> queryAll(Tag tag);

    /**
     * 统计总行数
     *
     * @param tag 查询条件
     * @return 总行数
     */
    long count(Tag tag);

    /**
     * 新增数据
     *
     * @param tag 实例对象
     * @return 影响行数
     */
    int insert(Tag tag);

//    /**
//     * 批量新增数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<Tag> 实例对象列表
//     * @return 影响行数
//     */
//    int insertBatch(@Param("entities") List<Tag> entities);
//
//    /**
//     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<Tag> 实例对象列表
//     * @return 影响行数
//     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
//     */
//    int insertOrUpdateBatch(@Param("entities") List<Tag> entities);

    /**
     * 修改数据
     *
     * @param tag 实例对象
     * @return 影响行数
     */
    int update(Tag tag);

    /**
     * 通过主键删除数据
     *
     * @param tagId 主键
     * @return 影响行数
     */
    int deleteById(Integer tagId);

    /**
     * 通过tagName查询单条数据
     *
     * @param tagName
     * @return 实例对象
     */
    Tag queryByTagName(String tagName);

}

