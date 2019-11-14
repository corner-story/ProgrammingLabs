package com.example.demo.controller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class Testcase {

    @RequestMapping("/testcase/{id}")
    @ResponseBody
    public Object testcase(@PathVariable Integer id){
        HashMap<String, Object> res = new HashMap<>();
        String teststr = "";
        res.put("code", "200");
        res.put("error", "");
        StringBuilder sb = new StringBuilder();
        try{
            Resource resource = new ClassPathResource("/templates/" + "testcase" + id + ".html");
            File file = resource.getFile();
            String s = null;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((s=br.readLine()) != null){
                sb.append(s);
                sb.append("\n");
            }
            teststr = sb.toString();

        }catch (IOException e){
            System.out.println("\ntestcase error:\t" + e + "\n");
            res.put("error", e.toString());
        }catch (Exception e){
            System.out.println("\ntestcase error:\t" + e + "\n");
            res.put("error", e.toString());
        }finally {
            res.put("data", teststr);
        }

        return res;
    }

}
