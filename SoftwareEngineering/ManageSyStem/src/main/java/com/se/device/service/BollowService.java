package com.se.device.service;

import com.se.device.entity.DeviceBorrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BollowService extends JpaRepository<DeviceBorrow, Integer> {
}
