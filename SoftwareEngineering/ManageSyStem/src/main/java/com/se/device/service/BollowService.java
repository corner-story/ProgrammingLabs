package com.se.device.service;

import com.se.device.entity.DeviceBorrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BollowService extends JpaRepository<DeviceBorrow, Integer> {

    @Query(value = "select * from device_borrow where device_id=?1 order by start_time desc, id limit 1", nativeQuery = true)
    public DeviceBorrow findLastByDeviceId(int device_id);


    @Query(value = "select * from device_borrow where device_id=?1 order by start_time desc, id", nativeQuery = true)
    public List<DeviceBorrow> findAllByDeviceId(int device_id);

}
