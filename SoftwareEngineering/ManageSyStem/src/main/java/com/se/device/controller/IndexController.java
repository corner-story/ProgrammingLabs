package com.se.device.controller;


import com.se.device.entity.Device;
import com.se.device.service.DeviceService;
import com.se.device.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
    index中的大部分请求, 请求html页面

*/

@Controller
public class IndexController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }


    @GetMapping("/about")
    public String about(){
        return "about";
    }


    @GetMapping("/devices")
    public String devices(){
        return "devices";
    }


}
