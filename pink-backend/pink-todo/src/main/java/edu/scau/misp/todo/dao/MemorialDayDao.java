package edu.scau.misp.todo.dao;

import edu.scau.misp.todo.entity.MemorialDay;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 小程序纪念日表(MemorialDay)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-03 10:01:35
 */
@Mapper
@Repository
public interface MemorialDayDao {

    /**
     * 通过ID查询单条数据
     *
     * @param memorialDayId 主键
     * @return 实例对象
     */
    MemorialDay queryById(Integer memorialDayId);

    /**
     * 查询指定行数据
     *
     * @param memorialDay 查询条件
     * @return 对象列表
     */
    List<MemorialDay> queryAll(MemorialDay memorialDay);
    /**
     * 统计总行数
     *
     * @param memorialDay 查询条件
     * @return 总行数
     */
    long count(MemorialDay memorialDay);

    /**
     * 新增数据
     *
     * @param memorialDay 实例对象
     * @return 影响行数
     */
    int insert(MemorialDay memorialDay);

//    /**
//     * 批量新增数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<MemorialDay> 实例对象列表
//     * @return 影响行数
//     */
//    int insertBatch(@Param("entities") ToDoList<MemorialDay> entities);
//
//    /**
//     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<MemorialDay> 实例对象列表
//     * @return 影响行数
//     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
//     */
//    int insertOrUpdateBatch(@Param("entities") ToDoList<MemorialDay> entities);

    /**
     * 修改数据
     *
     * @param memorialDay 实例对象
     * @return 影响行数
     */
    int update(MemorialDay memorialDay);

    /**
     * 通过主键删除数据
     *
     * @param memorialDayId 主键
     * @return 影响行数
     */
    int deleteById(Integer memorialDayId);

}

