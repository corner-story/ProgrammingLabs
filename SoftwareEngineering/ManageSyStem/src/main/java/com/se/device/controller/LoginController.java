package com.se.device.controller;

import com.se.device.Bean.UserBean;
import com.se.device.entity.User;
import com.se.device.service.UserService;
import com.se.device.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/*
    前后端传输数据: ajax + json

    前端: JSON.stringify(data)
    后端接收: @RequestBody
    后端发送: JsonResult
*/



@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(HttpSession session){
        String username = (String) session.getAttribute("username");
        if(username == null){
            return "redirect:/login";
        }
        return "redirect:/admin";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public JsonResult<HashMap> login(@RequestBody UserBean userBean, HttpSession session){
        HashMap<String, String> res = new HashMap<>();
        JsonResult<HashMap> jsonResult = new JsonResult<>();

        String username = userBean.getUsername();
        String password = userBean.getPassword();
        List<User> user = userService.findUserByUsernameAndPassword(username, password);
        if(user == null || user.size() == 0){
            jsonResult.setMsg("用户名或密码错误!");
            jsonResult.setCode("0");
            res.put("url", "/login");
        }else{
            //登录成功
            jsonResult.setMsg("登录成功!");
            res.put("url", "/index");

            //存储session
            session.setAttribute("username", user.get(0).getUsername());
            session.setAttribute("password", user.get(0).getPassword());
            session.setAttribute("roleid", user.get(0).getRole_id());
            session.setAttribute("dpid", user.get(0).getDp_id());
        }

        jsonResult.setData(res);
        return jsonResult;
    }


}
