package com.se.device.service;

import com.se.device.entity.DeviceBroken;
import com.se.device.entity.DeviceFault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrokenService extends JpaRepository<DeviceBroken, Integer> {

    @Query(value = "select * from device_broken where user_id=?1 and device_id=?2 order by create_time desc, id desc limit 1", nativeQuery = true)
    public DeviceBroken findOneByUserIdAndDeviceId(int user_id, int device_id);


    @Query(value = "select * from device_broken order by create_time desc, id desc", nativeQuery = true)
    public List<DeviceBroken> findAll();


    @Query(value = "select * from device_broken order by create_time desc, id desc limit ?1, ?2", nativeQuery = true)
    public List<DeviceBroken> findAllToPage(int page, int limit);


    @Query(value = "select * from device_broken where id=?1", nativeQuery = true)
    public DeviceBroken findOneById(int id);

}
