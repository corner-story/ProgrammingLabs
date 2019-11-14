package com.example.demo.controller;


import com.compiler.ast.Stmt;
import com.compiler.compiler.Compiler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.compiler.compiler.*;
import com.compiler.lex.*;
import com.compiler.error.*;


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


    //tokens
    @PostMapping("/api/tokens")
    @ResponseBody
    public Object tokens(@RequestParam String source){
        HashMap<String, Object> res = new HashMap<>();
        List<Token> tokens = new ArrayList<>();
        res.put("code", "200");
        res.put("error", "");
        try{

            tokens = Compiler.Tokenize(source);

        }catch (SyntaxException e){
            System.out.println("\nTokenize error:\t" + e + "\n");
            res.put("error", e.toString());
        } catch(Exception e){
            System.out.println("\nTokenize error:\t" + e + "\n");
            res.put("error", e.toString());

        }finally {
            res.put("data", tokens);
        }

        return res;
    }


    //ast
    @RequestMapping("/api/ast")
    @ResponseBody
    public Object parse(@RequestParam String source){
        HashMap<String, Object> res = new HashMap<>();
        List<Stmt> stmts = new ArrayList<>();
        res.put("code", "200");
        res.put("error", "");
        try {
            stmts = Compiler.Parse(source);

        }catch (ParserException e){
            System.out.println("\nParse error:\t" + e + "\n");
            res.put("error", e.toString());
        }catch (Exception e){
            System.out.println("\nparse error:\t" + e + "\n");
            res.put("error", e.toString());
        }finally {
            res.put("data", stmts);
        }

        return res;
    }



    //bytecode
    @RequestMapping("/api/bytecode")
    @ResponseBody
    public Object bytecode(@RequestParam String source){
        HashMap<String, Object> res = new HashMap<>();
        List<ByteCode> byteCodes = new ArrayList<>();
        res.put("code", "200");
        res.put("error", "");
        try {
             byteCodes = Compiler.ByteCodes(source);

        }catch (SyntaxException e) {
            System.out.println("\nbytecode error:\t" + e + "\n");
            res.put("error", e.toString());
        } catch (ParserException e){
            System.out.println("\nbytecode error:\t" + e + "\n");
            res.put("error", e.toString());
        }catch (Exception e){
            System.out.println("\nbytecode error:\t" + e + "\n");
            res.put("error", e.toString());
        }finally {
            res.put("data", byteCodes);
        }

        return res;

    }


    @RequestMapping("/api/run")
    @ResponseBody
    public Object rumVM(@RequestParam String source){
        HashMap<String, Object> res = new HashMap<>();
        String runvm = "";
        res.put("code", "200");
        res.put("error", "");
        try {
            //一次性创建PrintStream输出流
            PrintStream ps=new PrintStream(new FileOutputStream("runvm.txt"));
            //将标准输出重定向到PS输出流
            System.setOut(ps);

            Compiler.RunVM(source);

            StringBuilder result = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader("runvm.txt"));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(s);
                result.append("\n");
            }
            runvm = result.toString();


            File file=new File("runvm.txt");
            if(file.exists()&&file.isFile())
                file.delete();

        }catch (SyntaxException e) {
            System.out.println("\nrun error:\t" + e + "\n");
            res.put("error", e.toString());
        } catch (ParserException e){
            System.out.println("\nrun error:\t" + e + "\n");
            res.put("error", e.toString());
        }catch (Exception e){
            System.out.println("\nrun error:\t" + e + "\n");
            res.put("error", e.toString());
        }finally {
            res.put("data", runvm);
        }

        return res;
    }





}
