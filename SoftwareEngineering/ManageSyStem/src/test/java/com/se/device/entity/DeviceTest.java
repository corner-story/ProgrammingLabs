package com.se.device.entity;

import com.se.device.service.DeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceTest {

    @Autowired
    public DeviceService deviceService;


    @Test
    public void test1(){
        System.out.println("test device!");

        Device device = new Device();
        device.setName("大型机器");

        deviceService.save(device);
        List<Device> devices = deviceService.findAll();
        devices.forEach(device1 -> {
            System.out.println(device1);
        });
    }
}
