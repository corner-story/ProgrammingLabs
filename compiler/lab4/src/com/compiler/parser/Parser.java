package com.compiler.parser;


import com.compiler.error.*;
import com.compiler.lex.*;
import com.compiler.ast.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private List<Token> tokens;
    private Token cur;     //当前token和相应的下标
    private int index = 0;

    List<Expr> res;
    List<Stmt> stmts;

    public Parser(String filepath){
        this.tokens = new Lex(filepath).tokenize();
        res = new ArrayList<>();
        stmts = new ArrayList<>();
    }

    public void advance(){
        if(index < tokens.size()){
            index += 1;
            cur = tokens.get(index-1);
            return;
        }
        cur = null;
    }

    public void consume(String value, String error) throws Exception{
        if(cur == null || !cur.tokenValue.equals(value)){
            String location = "";
            if(cur != null){
                location = " at " + cur.row + " row " + cur.beginclo + " column";
            }
            throw new ParserException(error + location);
        }
        advance();
    }


    public List<Stmt> parse(){
        try{

            advance();
            if(cur == null){
                return stmts;
            }

            while (cur != null){

                switch (cur.tokenValue){

                    case "int":
                    case "double":
                    case "char":
                    case "string":
                        String kind = cur.tokenValue;
                        advance();
                        String identify = cur.tokenValue; advance();
                        consume(":=", "except :=!");
                        Expr expression = parseExpr();
                        consume(";", "except ; !");
                        stmts.add(new Stmt.DefStmt(kind, identify, expression)); break;

                    case "printf":
                        advance(); consume("(", "expect ( !");
                        Expr expr = parseExpr(); consume(")", "except ) !");
                        consume(";", "except ; !");
                        stmts.add(new Stmt.PrintStmt(expr)); break;

                    default:

                        throw new ParserException();
                }



            }


        }catch (Exception e){
            System.out.println(e);
        }

        return stmts;
    }


    public Expr parseExpr() throws Exception{
        Expr left = parseTerm();

        //当前操作符为 + 或者 -
        while(cur!=null && (cur.tokenId == Table.PL || cur.tokenId == Table.MI)){
            int id = cur.tokenId;
            advance();
            Expr right = parseTerm();
            if(id == Table.PL){
                left = new Expr.OpBinary("+", left, right);
            }else{
                left = new Expr.OpBinary("-", left, right);
            }
        }
        return left;
    }

    public Expr parseTerm() throws Exception{
        Expr left = parseFactor();
        while(cur != null && (cur.tokenId == Table.MU || cur.tokenId == Table.DI)){
            int id = cur.tokenId;
            advance();
            Expr right = parseFactor();
            if(id == Table.MU){
                left = new Expr.OpBinary("*", left, right);
            }else{
                left = new Expr.OpBinary("/", left, right);
            }
        }
        return left;
    }


    public Expr parseFactor() throws Exception{
        if(cur == null){
            throw new SyntaxException();
        }
        //整数 或 小数
        if(cur.tokenId == Table.LITERAL_INT || cur.tokenId == Table.LITERAL_REAL || cur.tokenId==Table.LITERAL_CHAR || cur.tokenId==Table.LITERAL_STRING){
            String tc = cur.tokenClass;
            String tv = cur.tokenValue;
            advance();
            return new Expr.Literal(tv, tc);
        // -factor
        }else if(cur.tokenId == Table.MI) {
            advance();
            Expr res = parseFactor();
            return new Expr.OpBinary("-", new Expr.Literal("0", "LITERAL_INT"), res);

         // (expr)
        }else if(cur.tokenId == Table.LB){
            advance();
            Expr res = parseExpr();
            consume(")", "expect a \")\"");
            return res;
        // identify
        }else if(cur.tokenId == Table.ID){
            String name = cur.tokenValue;
            advance();
            return new Expr.Identify(name);
        }

        throw new SyntaxException();
    }


    public static void main(String[] args) {
        File f = new File(".\\test\\testcase0");
        String path = "";
        try{
            //System.out.println(f.getCanonicalPath());
            path = f.getCanonicalPath();
        }
        catch(Exception e){

        }

        Parser parser = new Parser(path);
        List<Stmt> stmts = parser.parse();

        for (Stmt stmt : stmts) {
            System.out.println(stmt);
        }
    }
}
