package com.se.device.controller;

/*
    数据字典管理
*/

import com.se.device.entity.Department;
import com.se.device.entity.DeviceBorrow;
import com.se.device.entity.Role;
import com.se.device.entity.User;
import com.se.device.service.DepartmentService;
import com.se.device.service.RoleService;
import com.se.device.service.UserService;
import com.se.device.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/system")
public class DictController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


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



    //获取数据字典的html
    @GetMapping("/dict")
    public String getDict(){
        return "dict/data_dict";
    }


    //获取 user表
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAllUsers(@RequestParam int page, @RequestParam int limit, HttpSession session){

        String count = String.valueOf(userService.count());
        int begin = (page-1)*limit;
        int end = limit;
        List<User> data = userService.findAllToPage(begin, end);
        JsonResult<List<User>> jsonResult = new JsonResult(data);
        jsonResult.setCount(count);
        jsonResult.setCode("0");
        return jsonResult;
    }


}
