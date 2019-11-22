package com.se.device.controller;

/*
    数据字典管理
*/

import com.se.device.entity.Department;
import com.se.device.entity.Role;
import com.se.device.service.DepartmentService;
import com.se.device.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/system")
public class DictController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

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



    @PostMapping("/dps")
    @ResponseBody
    public Object getAlldps(){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        List<Department> dps = departmentService.findAll();
        res.put("data", dps);
        res.put("msg", "操作成功");
        return res;
    }


    @PostMapping("/roles")
    @ResponseBody
    public Object getAllRoles(){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        List<Role> roles = roleService.findAll();
        res.put("data", roles);
        res.put("msg", "操作成功");
        return res;
    }


}
