package com.se.device.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

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
