package com.se.device.controller;


import com.se.device.entity.Device;
import com.se.device.entity.DeviceBorrow;
import com.se.device.service.BollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class BollowController {

    @Autowired
    private BollowService bollowService;

    //新增一个设备转借记录
    @RequestMapping(value = "/bollow", method = RequestMethod.POST)
    @ResponseBody
    public Object insertOneDevice(@RequestBody DeviceBorrow deviceBorrow){
        HashMap<String, Object> res = new HashMap<>();
        try {

            bollowService.save(deviceBorrow);
            res.put("code", "200");
            res.put("msg", "申请成功!");

        }catch (Exception e){
            res.put("code", "500");
            res.put("msg", "申请失败, 请联系管理员!");
        }
        return res;
    }


}
