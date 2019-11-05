package com.se.device.service;


import com.se.device.entity.DeviceFault;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaultService extends JpaRepository<DeviceFault, Integer> {
}
