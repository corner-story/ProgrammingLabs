package com.se.device.service;


import com.se.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceService extends JpaRepository<Device, Integer> {

}
