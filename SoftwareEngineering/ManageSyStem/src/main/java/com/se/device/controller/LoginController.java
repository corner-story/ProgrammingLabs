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

    前端: JSON.stringify(data), 注意 contentType: "application/json;charset=UTF-8"
    后端接收: @RequestBody
    后端发送: JsonResult

    前端: data {key: value}
    后端: @RequestParm String key
*/

/*
    用户登录登出处理

*/

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(HttpSession session){
        Object username = session.getAttribute("username");
        if(username == null){
            return "redirect:/login";
        }
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login(HttpSession session){
        Object username = session.getAttribute("username");
        if(username != null){
            return "redirect:/index";
        }
        return "login/login";
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
            session.setAttribute("id", user.get(0).getId());
            //session.setAttribute("password", user.get(0).getPassword());
            session.setAttribute("roleid", user.get(0).getRole_id());
            session.setAttribute("dpid", user.get(0).getDp_id());
            session.setAttribute("rolename", user.get(0).getRole_name());
            session.setAttribute("dpname", user.get(0).getDp_name());
        }

        jsonResult.setData(res);
        return jsonResult;
    }


    @RequestMapping("/logout")
    public Object logout(HttpSession session){
        Object username = session.getAttribute("username");

        if(username != null){
            session.invalidate();
        }

        return "redirect:/login";
    }




}
