package com.se.device.service;

import com.se.device.entity.DeviceBroken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokenService extends JpaRepository<DeviceBroken, Integer> {
}
