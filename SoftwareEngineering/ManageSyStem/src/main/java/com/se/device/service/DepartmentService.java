package com.se.device.service;


import com.se.device.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentService extends JpaRepository<Department, Integer> {
}
