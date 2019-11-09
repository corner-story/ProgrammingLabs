package com.example.demo.controller;


import com.compiler.interpreter.Interpreter;
import com.compiler.lex.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;


@Controller
public class Index {


    @RequestMapping("/")
    public String index(){
        return "redirect:/compiler";
    }

    @RequestMapping("/compiler")
    public String compiler(){
        return "index";
    }

    @PostMapping("/api/tokens")
    @ResponseBody
    public Object tokens(@RequestParam String source){
        HashMap<String, Object> res = new HashMap<>();
        try{
            List<Token> tokens = Interpreter.Tokenize(source);

            res.put("code", "200");
            res.put("data", tokens);
        }catch (Exception e){
            System.out.println(e);
        }

        return res;
    }
}
