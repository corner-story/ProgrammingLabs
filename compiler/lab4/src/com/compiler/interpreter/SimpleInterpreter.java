package com.compiler.interpreter;

import com.compiler.lex.*;
import com.compiler.ast.*;
import com.compiler.parser.*;

import java.io.File;
import java.util.List;

public class SimpleInterpreter {
    private List<Stmt> stmts;

    public SimpleInterpreter(List<Stmt> stmts){
        this.stmts = stmts;
    }

    public void execute(){
        try{
            for (Stmt stmt : stmts) {
                ExecuteStmt execute = new ExecuteStmt();
                stmt.accept(execute);
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }


    public static void main(String[] args) {
        File f = new File(".\\test\\testcase0");
        String path = "";
        try{
            path = f.getCanonicalPath();
            Parser parser = new Parser(path);
            List<Stmt> stmts = parser.parse();
            SimpleInterpreter simpleInterpreter = new SimpleInterpreter(stmts);
            simpleInterpreter.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }


    }


}
