package com.se.device.entity;


import com.se.device.service.BollowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityTest {

    @Autowired
    private BollowService bollowService;


    @Test
    public void testBollow(){
        DeviceBorrow deviceBorrow = new DeviceBorrow();
        deviceBorrow.setDo_what("用来干神魔!");
        String back_time = "2013-1-1";
        Date date = null;
        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(back_time);
        }catch (Exception e){
            System.out.println(e);
        }

        deviceBorrow.setBack_time(date);
        bollowService.save(deviceBorrow);
        for (DeviceBorrow borrow : bollowService.findAll()) {
            System.out.println(borrow.getBack_time());
        }
    }



}
