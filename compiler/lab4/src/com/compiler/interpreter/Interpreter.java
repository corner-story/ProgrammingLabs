package com.compiler.interpreter;

import com.compiler.ast.*;
import com.compiler.lex.Lex;
import com.compiler.parser.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {
    private List<Stmt> stmts;
    private Lex lexer;
    private Parser parser;
    private List<ByteCode> bytecodes = new ArrayList<>();

    public Interpreter(List<Stmt> stmts){
        this.stmts = stmts;
    }

    public void execute(){
        try{
            for (Stmt stmt : stmts) {
                ByteCodeGen byteCodeGen = new ByteCodeGen();
                stmt.accept(byteCodeGen);
                for (int i = 0; i < byteCodeGen.getByteCodes().size(); i++) {
                    System.out.println(i + "\t" + byteCodeGen.getByteCodes().get(i).getBytecode() + "\t\t\t" + String.join(", ", byteCodeGen.getByteCodes().get(i).getArgs()));
                }
                System.out.println("\n");
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }


    public static void main(String[] args) {
        File f = new File(".\\test\\testcase1");
        String path = "";
        try{
            path = f.getCanonicalPath();
            Parser parser = new Parser(path);
            List<Stmt> stmts = parser.parse();
            Interpreter simpleInterpreter = new Interpreter(stmts);
            simpleInterpreter.execute();
        }
        catch(Exception e){
            System.out.println(e);
        }


    }


}
