package com.se.device.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {



    @RequestMapping("/search/device")
    @ResponseBody
    public Object searchDeviceById(){

        return null;
    }

}
