package edu.scau.misp.todo.dao;

import edu.scau.misp.todo.entity.ToDoList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * (ToDoList)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-03 13:20:19
 */
@Mapper
@Repository
public interface ListDao {

    /**
     * 通过ID查询单条数据
     *
     * @param listId 主键
     * @return 实例对象
     */
    ToDoList queryById(Integer listId);

    /**
     * 查询指定行数据
     *
     * @param toDoList 查询条件
     * @return 对象列表
     */
    List<ToDoList> queryAll(ToDoList toDoList);

    /**
     * 统计总行数
     *
     * @param toDoList 查询条件
     * @return 总行数
     */
    long count(ToDoList toDoList);

    /**
     * 新增数据
     *
     * @param toDoList 实例对象
     * @return 影响行数
     */
    int insert(ToDoList toDoList);

//    /**
//     * 批量新增数据（MyBatis原生foreach方法）
//     *
//     * @param entities List<listt> 实例对象列表
//     * @return 影响行数
//     */
//    int insertBatch(@Param("entities") List<ToDoList> entities);
//
//    /**
//     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
//     *
//     * @param entities ToDoList<ToDoList> 实例对象列表
//     * @return 影响行数
//     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
//     */
//    int insertOrUpdateBatch(@Param("entities") List<ToDoList> entities);

    /**
     * 修改数据
     *
     * @param toDoList 实例对象
     * @return 影响行数
     */
    int update(ToDoList toDoList);

    /**
     * 通过主键删除数据
     *
     * @param listId 主键
     * @return 影响行数
     */
    int deleteById(Integer listId);

    /**
     * 未完成事项列表查询
     *
     */
    List<ToDoList> queryToDo(ToDoList toDoList);


    /**
     * 已完成事项列表查询
     *
     */
    List<ToDoList> queryFinish(ToDoList toDoList);

    /**
     * 未完成事项列表查询(通过标题）
     *
     */
    List<ToDoList> queryToDoByTitle(ToDoList toDoList);


    /**
     * 已完成事项列表查询（通过标题）
     *
     */
    List<ToDoList> queryFinishByTitle(ToDoList toDoList);

    /**
     * 未完成事项列表查询(通过时间）
     *
     */
    List<ToDoList> queryAllToDoByTime(Date date1, Date date2, String openId);


    /**
     * 已完成事项列表查询(通过时间）
     *
     */
    List<ToDoList> queryAllFinishByTime(Date date1, Date date2, String openId);

    /**
     * 未完成事项列表查询(openId），等级为1\2\3\4
     *
     */
    List<ToDoList> queryByOne(String openId);

    List<ToDoList> queryByTwo(String openId);

    List<ToDoList> queryByThree(String openId);

    List<ToDoList> queryByFour(String openId);

}

