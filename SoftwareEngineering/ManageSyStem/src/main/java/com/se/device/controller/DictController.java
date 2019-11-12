package com.se.device.controller;

/*
    数据字典管理
*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/system")
public class DictController {



    @GetMapping("/users")
    public String users(){
        return "dict/users";
    }

    @GetMapping("/roles")
    public String roles(){
        return "dict/roles";
    }

    @GetMapping("/dps")
    public String dps(){
        return "dict/dps";
    }

    @GetMapping("/authority")
    public String authority(){
        return "dict/authority";
    }


}
