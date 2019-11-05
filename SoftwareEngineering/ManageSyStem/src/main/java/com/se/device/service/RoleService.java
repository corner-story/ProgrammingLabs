package com.se.device.service;

import com.se.device.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleService extends JpaRepository<Role, Integer> {
}
