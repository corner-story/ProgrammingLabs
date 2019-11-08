package com.se.device.controller;


import com.se.device.entity.Device;
import com.se.device.service.DeviceService;
import com.se.device.utils.JsonResult;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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


    @PostMapping("/api/device")
    @ResponseBody
    public JsonResult getAllDevices(@RequestParam int page, @RequestParam int limit){
        //I should build service, but it's late
        int begin = (page-1)*limit;
        int end = limit;
        String count = String.valueOf(deviceService.count());
        List<Device> devices = deviceService.findAllByPage(begin, end);
        JsonResult<List<Device>> jsonResult = new JsonResult(devices);
        jsonResult.setCount(count);
        jsonResult.setCode("0");
        return jsonResult;
    }
}
