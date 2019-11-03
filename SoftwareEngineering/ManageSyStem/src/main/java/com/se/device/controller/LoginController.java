package com.se.device.controller;

import com.se.device.entity.User;
import com.se.device.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String login(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "index";
    }


}
