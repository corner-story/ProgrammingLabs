package com.se.device.service;


import com.se.device.entity.DeviceBorrow;
import com.se.device.entity.DeviceFault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FaultService extends JpaRepository<DeviceFault, Integer> {

    @Query(value = "select * from device_fault where user_id=?1 and device_id=?2 order by create_time desc, id desc limit 1", nativeQuery = true)
    public DeviceFault findOneByUserIdAndDeviceId(int user_id,int device_id);



    @Query(value = "select * from device_fault order by create_time desc, id desc", nativeQuery = true)
    public List<DeviceFault> findAll();


    @Query(value = "select * from device_fault order by create_time desc, id desc limit ?1, ?2", nativeQuery = true)
    public List<DeviceFault> findAllToPage(int page, int limit);



    @Query(value = "select * from device_fault where id=?1", nativeQuery = true)
    public DeviceFault findOneById(int id);
}
