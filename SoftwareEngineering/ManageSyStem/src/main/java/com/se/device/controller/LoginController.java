package com.se.device.controller;
import com.se.device.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;



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

    @RequestMapping("/login")
    public String login(HttpSession session){
        return "login";
    }



    @RequestMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }

}
