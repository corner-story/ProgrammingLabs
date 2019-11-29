package com.se.device.controller;


import com.se.device.entity.DeviceBorrow;
import com.se.device.entity.User;
import com.se.device.service.BollowService;
import com.se.device.service.DeviceService;
import com.se.device.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/*
    index中的大部分请求, 请求html页面
    左侧导航栏请求对应的html

*/

@Controller
public class IndexController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private BollowService bollowService;

    @RequestMapping("/index")
    public String index(Model model, HttpSession session){
        Object username = session.getAttribute("username");
        if(username == null){
            return "redirect:/login";
        }
        model.addAttribute("username", username.toString());
        model.addAttribute("rolename", session.getAttribute("rolename").toString());
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



    //添加设备
    @GetMapping("/adddevice")
    public String addDeviec(){
        return "index/add_device";
    }


    //设备申请
    @GetMapping("/bollowdevice")
    public String bollowDevice(HttpSession session, Model model){

        Object id = session.getAttribute("id");
        if(id == null){
            return "redirect:/login";
        }
        User user = userService.findOneById(String.valueOf(id));
        model.addAttribute("user", user);
        return "index/device_bollow";
    }


    //设备申请的结果
    @GetMapping("/bollowresult")
    public String bollowResult(HttpSession session, Model model){
        Object id = session.getAttribute("id");
        if(id == null){
            return "redirect:/login";
        }
        return "index/bollow_result";
    }

    //设备申请审查html
    @GetMapping("/bollowcheck")
    public String bollowCheck(HttpSession session, Model model){
        Object id = session.getAttribute("id");
        if(id == null){
            return "redirect:/login";
        }
        return "index/bollow_check";
    }



    //设备故障的html页面
    @GetMapping("/devicefault")
    public String deviceFault(HttpSession session, Model model){
        Object id = session.getAttribute("id");
        if(id == null){
            return "redirect:/login";
        }

        User user = userService.findOneById(String.valueOf(id));
        model.addAttribute("user", user);
        return "index/device_fault";
    }


    //设备故障审批界面html
    @GetMapping("/faultcheck")
    public String fauluCheck(HttpSession session, Model model){
        Object id = session.getAttribute("id");
        if(id == null){
            return "redirect:/login";
        }

        User user = userService.findOneById(String.valueOf(id));
        model.addAttribute("user", user);
        return "index/fault_check";
    }

}
