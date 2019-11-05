package com.example.demo.controller;


import com.compiler.interpreter.Interpreter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.compiler.interpreter.ByteCode;

import java.util.List;

@Controller
public class Index {

    @RequestMapping("/")
    @ResponseBody
    public String index(){
        String source = "int test := 23E+2; printf(test);";

        List<ByteCode> byteCodes = Interpreter.ByteCodes(source);
        String res = "";
        for (ByteCode code : byteCodes) {
            res = res + code.toString() + "\n";
        }
        return res;
    }
}
