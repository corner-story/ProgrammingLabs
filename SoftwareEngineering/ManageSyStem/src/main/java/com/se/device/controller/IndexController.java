package com.se.device.controller;


import com.se.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/*
    index中的大部分请求, 请求html页面

*/

@Controller
public class IndexController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping("/index")
    public String index(Model model, HttpSession session){
        Object username = session.getAttribute("username");
        if(username == null){
            return "redirect:/login";
        }
        model.addAttribute("username", username.toString());
        return "index/index";
    }


    @GetMapping("/about")
    public String about(){
        return "index/about";
    }


    //访问device.html
    @GetMapping("/device")
    public String devices(){
        return "index/device";
    }

}
