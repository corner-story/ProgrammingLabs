package com.se.device.service;

import com.se.device.entity.DeviceBorrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BollowService extends JpaRepository<DeviceBorrow, Integer> {

    @Query(value = "select * from device_borrow where device_id=?1 order by start_time desc, id desc limit 1", nativeQuery = true)
    public DeviceBorrow findLastByDeviceId(int device_id);


    @Query(value = "select * from device_borrow where device_id=?1 and authorize_result='同意' order by start_time desc, id desc", nativeQuery = true)
    public List<DeviceBorrow> findAllByDeviceId(int device_id);

    @Query(value = "select * from device_borrow where user_id=?1 order by create_time desc, id desc", nativeQuery = true)
    public List<DeviceBorrow> findAllByUserId(int user_id);


    @Query(value = "select * from device_borrow where user_id=?1 order by create_time desc, id desc limit ?2, ?3", nativeQuery = true)
    public List<DeviceBorrow> findAllByUserIdToPage(int user_id, int page, int limit);



    @Query(value = "select * from device_borrow order by create_time desc, id desc limit ?1, ?2", nativeQuery = true)
    public List<DeviceBorrow> findAllByPage(int page, int limit);


    @Query(value = "select * from device_borrow where id=?1", nativeQuery = true)
    public DeviceBorrow findOneById(int id);



    //获取某个用户没有归还的所有转借记录
    @Query(value = "select * from device_borrow where user_id=?1 and authorize_result='同意' and return_time is null order by create_time desc, id desc", nativeQuery = true)
    public List<DeviceBorrow> findAllNotBackByUserId(int user_id);

    //获取某个用户没有归还的所有转借记录
    @Query(value = "select * from device_borrow where user_id=?1 and authorize_result='同意' and return_time is null order by create_time desc, id desc limit ?2, ?3", nativeQuery = true)
    public List<DeviceBorrow> findAllNotBackByUserIdToPage(int user_id, int page, int limit);
}
