package com.se.device.controller;



import com.se.device.entity.User;
import com.se.device.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
@RequestMapping("/self")
public class UserController {


    @Autowired
    private UserService userService;



    //获取个人信息的html界面
    @GetMapping("/selfinfo")
    public String selfInfo(HttpSession session, Model model){
        Object id = session.getAttribute("id");
        if(id == null){
            return "redirect:/login";
        }
        User user = userService.findOneById(String.valueOf(id));
        model.addAttribute("user", user);
        return "index/self_info";
    }



    @PostMapping("/update")
    @ResponseBody
    public Object update(@RequestBody User user){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        try{
            userService.save(user);
            res.put("msg", "修改成功");
        }catch (Exception e){
            res.put("msg", "修改失败, 请联系管理员!");
        }
        return res;
    }



    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(@RequestBody User user){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        try{
            userService.save(user);
            res.put("msg", "添加成功!");
        }catch (Exception e){
            System.out.println(e);
            res.put("msg", "添加失败, error: " + e.toString());
        }
        return res;
    }

    @GetMapping("/user")
    public String addUserHtml(){
        return "index/add_user";
    }

}
