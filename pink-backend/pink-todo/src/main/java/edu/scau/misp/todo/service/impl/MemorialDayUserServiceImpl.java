package edu.scau.misp.todo.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import edu.scau.misp.todo.dao.MemorialDayDao;
import edu.scau.misp.todo.dao.WechatUserDao;
import edu.scau.misp.todo.entity.MemorialDay;
import edu.scau.misp.todo.entity.MemorialDayUser;
import edu.scau.misp.todo.dao.MemorialDayUserDao;
import edu.scau.misp.todo.entity.ToDoList;
import edu.scau.misp.todo.model.*;
import edu.scau.misp.todo.service.MemorialDayUserService;
import edu.scau.misp.todo.util.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 小程序纪念日用户关系表(MemorialDayUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-03 10:39:42
 */
@Service("memorialDayUserService")
public class MemorialDayUserServiceImpl implements MemorialDayUserService {
    @Resource
    private MemorialDayUserDao memorialDayUserDao;
    @Resource
    private MemorialDayDao memorialDayDao;
    @Resource
    private WechatUserDao wechatUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param memorialDayUserId 主键
     * @return 实例对象
     */
    @Override
    public MemorialDayUser queryById(Integer memorialDayUserId) {
        return this.memorialDayUserDao.queryById(memorialDayUserId);
    }

    /**
     * 查询指定行数据
     *
     * @param memorialDayUser 筛选条件
     * @return 对象列表
     */
    @Override
    public List<MemorialDayUser> queryAll(MemorialDayUser memorialDayUser) {
        return this.memorialDayUserDao.queryAll(memorialDayUser);
    }

    /**
     * 获取纪念日的某一天
     * @param dateList
     * @return
     */
    @Override
    public List<CalList> queryMemorialDay(DateList dateList) {
//        System.out.println(dateList.getDate()+" "+dateList.getOpenId());
//        List<MemorialDayUser> memorialDayUsers = this.memorialDayUserDao.queryByOpenId(dateList.getOpenId());
        List<MemorialDayUser> memorialDayUser0 = this.memorialDayUserDao.queryByOpenId(dateList.getOpenId());
        MemorialDayUser memorialDayUser1 = this.memorialDayUserDao.queryByTop(dateList.getOpenId());
//        System.out.println(memorialDayUser1.getMemorialDayId());
        List<MemorialDayUser> memorialDayUsers = new ArrayList<>();
        if (memorialDayUser1!=null){
            memorialDayUsers.add(memorialDayUser1);
        }
        for (MemorialDayUser m : memorialDayUser0){
            memorialDayUsers.add(m);
        }

        Date date = dateList.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime (date); //放入Date类型数据
        int day = calendar.get(Calendar.DATE);//获取日
        List<CalList> calLists = new ArrayList<>();
        for (MemorialDayUser memorialDayUser : memorialDayUsers){
            MemorialDay memorialDay = this.memorialDayDao.queryById(memorialDayUser.getMemorialDayId());
            Calendar cal = Calendar.getInstance();
//            System.out.println(memorialDay.getTime());
            cal.setTime (memorialDay.getTime()); //放入Date类型数据
            int d = cal.get(Calendar.DATE);//获取日
//            System.out.println(day+" "+d);
            if (memorialDay.getTime().compareTo(date)<=0){  //筛选纪念日
                if (day==d){   //日期的同一日
                    CalList calList = new CalList();
                    calList.setMemorialDayName(memorialDay.getMemorialDayName());
                    calList.setRemark(memorialDay.getRemark());
                    calList.setTime(memorialDay.getTime());
                    calList.setConditionId(memorialDay.getConditionId());
                    Long c = DateUtil.between(date, memorialDay.getTime(), DateUnit.DAY);
                    calList.setDay(c.intValue());
//                    System.out.println(calList.getMemorialDayName()+calList.getRemark()+calList.getDay());
                    calLists.add(calList);
                }
            }
        }
        return calLists;
    }

    /**
     * 获取某年某月日历上的纪念日日期列表
     *
     * @param calDay
     * @return
     */
    @Override
    public List<String> queryDate(CalDay calDay) {
        List<MemorialDayUser> memorialDayUser0 = this.memorialDayUserDao.queryByOpenId(calDay.getOpenId());
        MemorialDayUser memorialDayUser1 = this.memorialDayUserDao.queryByTop(calDay.getOpenId());
        List<MemorialDayUser> memorialDayUsers = new ArrayList<>();
        if (memorialDayUser1!=null){
            memorialDayUsers.add(memorialDayUser1);
        }
        for (MemorialDayUser m : memorialDayUser0){
            memorialDayUsers.add(m);
        }
        List<String> dateLists = new ArrayList<>();
        System.out.println("1:"+calDay.getOpenId()+""+calDay.getYm());
        Date date = calDay.getYm();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dy = cal.get(Calendar.YEAR);
        int dm = cal.get(Calendar.MONTH)+1;
        Calendar calendar = Calendar.getInstance();
        for (MemorialDayUser memorialDayUser : memorialDayUsers){
            MemorialDay memorialDay = this.memorialDayDao.queryById(memorialDayUser.getMemorialDayId());
            calendar.setTime(memorialDay.getTime());
            System.out.println("2:"+memorialDay.getTime());
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH)+1;
            int d = calendar.get(Calendar.DATE);
            String dd = String.format("%02d", d);
            String r = dy+"-"+dm+"-"+dd;
//            System.out.println("3:"+m);
//            System.out.println("4:"+dd);
//            System.out.println("5:"+r);
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            if (y < dy){
                if (!dateLists.contains(r)){
                    dateLists.add(r);
                }
//                try {
//                    Date date1 = sdf.parse(r);
//                    System.out.println("6:"+date1);
//                    dateLists.add(date1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            }else if (y == dy && m <= dm){
                if (!dateLists.contains(r)){
                    dateLists.add(r);
                }
//                try {
//                    Date date1 = sdf.parse(r);
//                    dateLists.add(date1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            }
        }
        return dateLists;
    }

    /**
     * 获取MemorialList
     *
     * @param openId
     * @return
     */
    @Override
    public List<MemorialList> queryByOpenId(String openId) {
        List<MemorialDayUser> memorialDayUsers = this.memorialDayUserDao.queryByOpenId(openId);
        List<MemorialList> memorialLists = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date time = DateUtil.parse(dateFormat.format(date)) ;
        for (MemorialDayUser memorialDayUser : memorialDayUsers){
            MemorialDay memorialDay = this.memorialDayDao.queryById(memorialDayUser.getMemorialDayId());
            MemorialList memorialList = new MemorialList();
            memorialList.setMemorialDayId(memorialDay.getMemorialDayId());
            memorialList.setTime(memorialDay.getTime());
            memorialList.setCreatedTime(memorialDay.getCreatedTime());
            memorialList.setConditionId(memorialDay.getConditionId());
            memorialList.setRemark(memorialDay.getRemark());
            memorialList.setTitle(memorialDay.getMemorialDayName());
            Long c = DateUtil.between(time, memorialDay.getTime(), DateUnit.DAY);
            memorialList.setDay(c.intValue());
            if (memorialDay.getTime().compareTo(time)<0){
                memorialList.setTypeCode("true");
                memorialList.setType("memory");
            }else if (memorialDay.getTime().compareTo(time)>=0){
                memorialList.setTypeCode("false");
                memorialList.setType("target");
            }
            memorialLists.add(memorialList);
        }
        return memorialLists;
    }

    @Override
    public MemorialList queryByTop(String openId) {
        MemorialDayUser memorialDayUser = this.memorialDayUserDao.queryByTop(openId);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date time = DateUtil.parse(dateFormat.format(date)) ;
        MemorialList memorialList = new MemorialList();
        if (memorialDayUser!=null){
            MemorialDay memorialDay = this.memorialDayDao.queryById(memorialDayUser.getMemorialDayId());
            memorialList.setMemorialDayId(memorialDay.getMemorialDayId());
            memorialList.setTime(memorialDay.getTime());
            memorialList.setCreatedTime(memorialDay.getCreatedTime());
            memorialList.setConditionId(memorialDay.getConditionId());
            memorialList.setRemark(memorialDay.getRemark());
            memorialList.setTitle(memorialDay.getMemorialDayName());
            Long c = DateUtil.between(time, memorialDay.getTime(), DateUnit.DAY);
            memorialList.setDay(c.intValue());
            if (memorialDay.getTime().compareTo(time)<0){
                memorialList.setTypeCode("true");
                memorialList.setType("memory");
            }else if (memorialDay.getTime().compareTo(time)>=0){
                memorialList.setTypeCode("false");
                memorialList.setType("target");
            }
        }
        return memorialList;
    }

    @Override
    public MemorialDayUser queryByMemorialDayId(Integer memorialDayId) {
        return this.memorialDayUserDao.queryByMemorialDayId(memorialDayId);
    }

//    /**
//     * 分页查询
//     *
//     * @param memorialDayUser 筛选条件
//     * @param pageRequest      分页对象
//     * @return 查询结果
//     */
//    @Override
//    public Page<MemorialDayUser> queryByPage(MemorialDayUser memorialDayUser, PageRequest pageRequest) {
//        long total = this.memorialDayUserDao.count(memorialDayUser);
//        return new PageImpl<>(this.memorialDayUserDao.queryAllByLimit(memorialDayUser, pageRequest), pageRequest, total);
//    }

    /**
     * 新增数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    @Override
    public MemorialDayList insert(MemorialDayList memorialDayList) {
        MemorialDay memorialDay = new MemorialDay();
//        memorialDay.setMemorialDayId(memorialDayList.getMemorialDayId());
        memorialDay.setMemorialDayName(memorialDayList.getMemorialDayName());
        memorialDay.setTime(memorialDayList.getTime());
        memorialDay.setConditionId(memorialDayList.getConditionId());
        memorialDay.setRemark(memorialDayList.getRemark());
        memorialDay.setCreatedTime(new Date());
        memorialDay.setUpdatedTime(new Date());
        MemorialDayUser memorialDayUser = new MemorialDayUser();
        memorialDayUser.setMemorialDay(memorialDay);
        memorialDayUser.setMemorialDayId(memorialDay.getMemorialDayId());
//        memorialDayUser.setMemorialDayUserId(memorialDayList.getMemorialDayUserId());
        memorialDayUser.setOpenId(memorialDayList.getOpenId());
        memorialDayUser.setWechatUser(this.wechatUserDao.getByOpenId(memorialDayList.getOpenId()));
        this.memorialDayDao.insert(memorialDay);
        this.memorialDayUserDao.insert(memorialDayUser);
        return memorialDayList;
    }

    /**
     * 修改数据
     *
     * @param memorialDayList 实例对象
     * @return 实例对象
     */
    @Override
    public MemorialDayList update(MemorialDayList memorialDayList) {
        MemorialDay memorialDay = new MemorialDay();
        memorialDay.setMemorialDayId(memorialDayList.getMemorialDayId());
        memorialDay.setMemorialDayName(memorialDayList.getMemorialDayName());
        memorialDay.setTime(memorialDayList.getTime());
        memorialDay.setConditionId(memorialDayList.getConditionId());
        memorialDay.setRemark(memorialDayList.getRemark());
        memorialDay.setCreatedTime(new Date());
        memorialDay.setUpdatedTime(new Date());
        MemorialDayUser memorialDayUser = new MemorialDayUser();
        memorialDayUser.setMemorialDay(memorialDay);
        memorialDayUser.setMemorialDayId(memorialDayList.getMemorialDayId());
        memorialDayUser.setMemorialDayUserId(memorialDayList.getMemorialDayUserId());
        memorialDayUser.setOpenId(memorialDayList.getOpenId());
        memorialDayUser.setWechatUser(this.wechatUserDao.getByOpenId(memorialDayList.getOpenId()));
        this.memorialDayUserDao.update(memorialDayUser);
        this.memorialDayDao.update(memorialDay);
        return memorialDayList;
    }

    /**
     * 通过主键删除数据
     *
     * @param memorialDayUserId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer memorialDayUserId) {
        boolean a;
        if (memorialDayDao.deleteById(memorialDayUserDao.queryById(memorialDayUserId).getMemorialDayId())>0){
           a = memorialDayUserDao.deleteById(memorialDayUserId)>0;
        }else {
            throw new ServiceException("删除失败");
        }
        return a;
    }


}
