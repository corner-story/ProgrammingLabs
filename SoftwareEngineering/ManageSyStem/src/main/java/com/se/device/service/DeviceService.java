package com.se.device.service;


import com.se.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceService extends JpaRepository<Device, Integer> {

    @Query(value = "select * from device limit ?1, ?2 order by create_time desc", nativeQuery = true)
    public List<Device> findAllByPage(int begin, int end);

    @Query(value = "select * from device where id=?1", nativeQuery = true)
    public Device findOneById(int id);

}
