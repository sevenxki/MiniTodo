package edu.scau.misp.todo.dao;

import edu.scau.misp.todo.entity.MemorialDayUser;
import edu.scau.misp.todo.model.MemorialDayList;
import edu.scau.misp.todo.model.MemorialList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 小程序纪念日用户关系表(MemorialDayUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-03 10:37:40
 */
@Mapper
@Repository
public interface MemorialDayUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param memorialDayUserId 主键
     * @return 实例对象
     */
    MemorialDayUser queryById(Integer memorialDayUserId);

    /**
     * 查询指定行数据
     *
     * @param memorialDayUser 查询条件
     * @return 对象列表
     */
    List<MemorialDayUser> queryAll(MemorialDayUser memorialDayUser);

    List<MemorialDayUser> queryByOpenId(String openId);

    MemorialDayUser queryByTop(String openId);

    MemorialDayUser queryByMemorialDayId(Integer memorialDayId);

    /**
     * 统计总行数
     *
     * @param memorialDayUser 查询条件
     * @return 总行数
     */
    long count(MemorialDayUser memorialDayUser);

    /**
     * 新增数据
     *
     * @param memorialDayUser 实例对象
     * @return 影响行数
     */
    int insert(MemorialDayUser memorialDayUser);

//    /**
//     * 批量新增数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<MemorialDayUser> 实例对象列表
//     * @return 影响行数
//     */
//    int insertBatch(@Param("entities") ToDoList<MemorialDayUser> entities);
//
//    /**
//     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<MemorialDayUser> 实例对象列表
//     * @return 影响行数
//     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
//     */
//    int insertOrUpdateBatch(@Param("entities") ToDoList<MemorialDayUser> entities);

    /**
     * 修改数据
     *
     * @param memorialDayUser 实例对象
     * @return 影响行数
     */
    int update(MemorialDayUser memorialDayUser);

    /**
     * 通过主键删除数据
     *
     * @param memorialDayUserId 主键
     * @return 影响行数
     */
    int deleteById(Integer memorialDayUserId);

}

