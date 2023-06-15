package edu.scau.misp.todo.service;

import edu.scau.misp.todo.entity.MemorialDayUser;
import edu.scau.misp.todo.entity.ToDoList;
import edu.scau.misp.todo.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

/**
 * 小程序纪念日用户关系表(MemorialDayUser)表服务接口
 *
 * @author makejava
 * @since 2022-11-03 10:39:42
 */
public interface MemorialDayUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param memorialDayUserId 主键
     * @return 实例对象
     */
    MemorialDayUser queryById(Integer memorialDayUserId);

    /**
     * 查询多条数据
     *
     * @param memorialDayUser 筛选条件
     * @return 查询结果
     */
    List<MemorialDayUser> queryAll(MemorialDayUser memorialDayUser);

    List<CalList> queryMemorialDay(DateList dateList);

    List<MemorialList> queryByOpenId(String openId);

    MemorialList queryByTop(String openId);

    MemorialDayUser queryByMemorialDayId(Integer memorialDayId);
    /**
     * 新增数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    MemorialDayList insert(MemorialDayList memorialDayList);

    /**
     * 修改数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    MemorialDayList update(MemorialDayList memorialDayList);

    /**
     * 通过主键删除数据
     *
     * @param memorialDayUserId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer memorialDayUserId);

    List<String> queryDate(CalDay calDay);
}
