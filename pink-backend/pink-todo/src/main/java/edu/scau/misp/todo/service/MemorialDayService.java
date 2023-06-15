package edu.scau.misp.todo.service;

import edu.scau.misp.todo.entity.MemorialDay;
import edu.scau.misp.todo.model.MemorialDayList;
import edu.scau.misp.todo.model.MemorialList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 小程序纪念日表(MemorialDay)表服务接口
 *
 * @author makejava
 * @since 2022-11-03 10:01:41
 */
public interface MemorialDayService {

    /**
     * 通过ID查询单条数据
     *
     * @param memorialDayId 主键
     * @return 实例对象
     */
    MemorialDay queryById(Integer memorialDayId);
    MemorialList queryByIdAddType(Integer memorialDayId);
    /**
     * 查询多条数据
     *
     * @param memorialDay 筛选条件
     * @return 查询结果
     */
    List<MemorialDay> queryAll(MemorialDay memorialDay);

    /**
     * 新增数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    MemorialDay insert(MemorialDayList memorialDayList);

    /**
     * 修改数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    MemorialDay update(MemorialDayList memorialDayList);

    /**
     * 通过主键删除数据
     *
     * @param memorialDayId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer memorialDayId);

}
