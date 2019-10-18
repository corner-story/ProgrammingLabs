package com.compiler.parser;

import com.compiler.lex.*;


import java.io.File;
import java.util.List;


public class Parser {
    private List<Token> tokens;
    private Token cur;     //当前token和相应的下标
    private int index = 0;

    public Parser(String filepath){
        this.tokens = new Lex(filepath).tokenize();
    }

    public void advance(){
        if(index < tokens.size()){
            index += 1;
            cur = tokens.get(index-1);
            return;
        }
        cur = null;
    }

    public Token traceback(){
        if(index > 0){
            return tokens.get(index - 1);
        }
        return null;
    }

    public void parse(){
        try{
            advance();
            if(cur == null || cur.tokenValue.equals(";")){
                System.out.println("没有输入表达式!");
                return;
            }

            parseExpr();

            if(cur == null || cur.tokenValue.equals(";")){
                System.out.println("Parse success!");
            }else{
                System.out.println("Parse failed!");
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Parse error!");
        }

    }

    public void parseExpr() throws Exception{
        parseTerm();
        //当前操作符为 + 或者 -
        while(cur!=null && (cur.tokenId == Table.PL || cur.tokenId == Table.MI)){
            advance();
            parseTerm();
        }

        return;
    }

    public void parseTerm() throws Exception{
        parseFactor();
        while(cur != null && (cur.tokenId == Table.MU || cur.tokenId == Table.DI)){
            advance();
            parseFactor();
        }
        return;
    }

    public void parseFactor() throws Exception{
        if(cur == null){
            excepted("");
        }
        if(cur.tokenId>= 7 && cur.tokenId<=11){
            advance();return;
        }else if(cur.tokenId == Table.MI){
            advance();parseFactor();return;
        }else if(cur.tokenId == Table.LB){
            advance();
            parseExpr();
            excepted(")");
            return;
        }
        excepted("");
    }


    public void excepted(String something) throws Exception{
        Token curr = cur==null? traceback(): cur;
        if(cur == null || !cur.tokenValue.equals(something)){
            String error = something.equals("")? "Syntax error":"except a \""+ something +"\"";
            String location = "";
            if(cur == null){
                if(curr != null){
                    location = " at " + curr.row + " row " + curr.endclo + " column!";
                }
            }else{
                location = " at " + cur.row + " row " + cur.beginclo + " column!";
            }

            throw new Exception(error + location);
        }
        advance();
    }

    public static void main(String[] args) {
        File f = new File(".\\test\\testcase0");
        String path = "";
        try{
            //System.out.println(f.getCanonicalPath());
            path = f.getCanonicalPath();
        }
        catch(Exception e){
            return;
        }

        Parser parser = new Parser(path);
        parser.parse();
    }
}
