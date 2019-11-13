package com.compiler.parser;


import com.compiler.error.*;
import com.compiler.lex.*;
import com.compiler.ast.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private Token cur;     //当前token和相应的下标
    private int index = 0;

    List<Expr> res = new ArrayList<>();
    List<Stmt> stmts = new ArrayList<>();

    public Parser(String input) throws Exception{
        this.tokens = new Lex(input).tokenize();
    }

    public Parser(List<Token> tokens){
        this.tokens = tokens;
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


    public List<Stmt> parse() throws Exception{

        advance();
        if(cur == null){
            return stmts;
        }

        while (cur != null){
            stmts.add(parseStmt());
        }

        return stmts;
    }

    public Stmt parseStmt() throws Exception{

        switch (cur.tokenValue){

            case "int":
            case "double":
            case "char":
            case "string":

                return parseDefStmt();
            case "printf":
                return parsePrintStmt();
            case "if":
                return parseIfStmt();
            case "while":
                return parseWhileStmt();
            case "for":
                return parseForStmt();
            case "return":
                return parseReturnStmt();



            default:

                // test = 1;
                if(cur.tokenId == Table.ID){
                    return parseDefStmt();
                }
                // continue or break;
                if(cur.tokenId == 12 || cur.tokenId == 13){
                    return parseBreakOrContinueStmt();
                }


                consume("", "unknowm token '" + cur.tokenValue + "'");
                return null;
        }
    }

    //function define
    private Stmt parseFuncStmt(String kind, String identify) throws Exception{
        List<Stmt> args = new ArrayList<>();
        List<Stmt> body = new ArrayList<>();

        while(cur != null && cur.tokenId != Table.RB){
            if(cur.tokenId > 4){
                throw new ParserException("function args define error in '" + identify + "'");
            }
            String t = cur.tokenValue;
            advance();
            if(cur.tokenId != Table.ID){
                throw new ParserException("function args define error in '" + identify + "'");
            }
            args.add(new Stmt.DefStmt(t, cur.tokenValue, null));
            advance();

            if(cur != null && cur.tokenId==Table.CM){
                advance();
            }
        }
        if(cur == null){
            throw new ParserException("function args define error in '" + identify + "'");
        }
        consume(")", "expect a ')' ");
        consume("{", "expect a '{' ");

        while(cur!=null && cur.tokenId!=Table.RBR){
            body.add(parseStmt());
        }
        if(cur == null){
            throw new ParserException("error in function '" + identify + "'");
        }
        advance();

        return new Stmt.FuncStmt(kind, identify, args, body);
    }

    //function call;
    // test(1,2);
    private Stmt parseCallStmt(String funcname) throws Exception{
        List<Expr> args = new ArrayList<>();

        while (cur!=null && cur.tokenId!=Table.RB){
            args.add(parseExpr());
            if(cur != null && cur.tokenId==Table.CM){
                advance();
            }
        }
        if(cur == null){
            throw new ParserException("error in function '" + funcname + "'");
        }
        advance();
        consume(";", "expect a ';' after function call !");
        return new Stmt.CallStmt(funcname, args);
    }

    //return stmt
    private Stmt parseReturnStmt() throws Exception{
        advance();
        Expr expression = parseExpr();
        consume(";", "expected a ';' after 'return' ");
        return new Stmt.ReturnStmt(expression);
    }

    public Stmt parseDefStmt(boolean flag) throws Exception{
        String kind = "unknown";
        if(cur.tokenId != Table.ID){
            kind = cur.tokenValue;
            advance();
        }

        String identify = cur.tokenValue; advance();

        //判断是函数声明还是简单变量声明
        //cur 为 "(" 则为函数定义
        if(cur != null && cur.tokenId == Table.LB){
            advance();
            if(kind.equals("unknown")){
                return parseCallStmt(identify);
            }
            return parseFuncStmt(kind, identify);
        }

        consume(":=", "except := ");
        Expr expression = parseExpr();

        if(flag){
            consume(";", "except ; ");
        }
        return new Stmt.DefStmt(kind, identify, expression);
    }

    public Stmt parseDefStmt() throws Exception{
        return parseDefStmt(true);
    }

    public Stmt parseBreakOrContinueStmt() throws Exception{
        Stmt res = new Stmt.BreakOrContinueStmt(cur.tokenValue);
        advance();
        consume(";", "expect ';' after " + cur.tokenValue);
        return res;
    }

    public Stmt parseWhileStmt() throws Exception{
        advance();
        consume("(", "expected a '(' after 'while'");
        Expr condition = parseLogical();
        consume(")", "expected a ')'");
        consume("{", "expected a '{'");
        List<Stmt> body = new ArrayList<>();
        while(cur != null && !cur.tokenValue.equals("}")){
            body.add(parseStmt());
        }
        if(cur == null){
            throw new ParserException();
        }
        advance();

        return new Stmt.WhileStmt(condition, body);
    }

    public Stmt parseForStmt() throws Exception{
        advance();
        consume("(", "expected a '(' after 'for'");
        Stmt init = parseDefStmt();
        Expr condition = parseLogical();
        Stmt alter = parseDefStmt(false);

        consume(")", "expected a ')'");
        consume("{", "expected a '{'");
        List<Stmt> body = new ArrayList<>();
        while(cur != null && !cur.tokenValue.equals("}")){
            body.add(parseStmt());
        }
        if(cur == null){
            throw new ParserException();
        }
        advance();
        return new Stmt.ForStmt(init, condition, alter, body);
    }

    public Stmt parsePrintStmt() throws Exception{

        advance();
        consume("(", "expect ( ");
        Expr expr = parseExpr();
        consume(")", "except ) ");
        consume(";", "except ; ");
        return new Stmt.PrintStmt(expr);
    }

    public Stmt parseIfStmt() throws Exception{
        advance();
        consume("(", "expect a '(' after if");

        Expr condition = parseLogical();
        consume(")", "expect a ')' after if condition");
        consume("{", "expect a '{' ");
        List<Stmt> consequence = new ArrayList<>();
        List<Stmt> alternayive = new ArrayList<>();
        while (cur!=null && !cur.tokenValue.equals("}")){
            consequence.add(parseStmt());
        }
        consume("}", "expect a '}' ");
        if(cur==null || !cur.tokenValue.equals("else")){
            return new Stmt.IfStmt(condition, consequence, alternayive);
        }
        advance();
        consume("{", "expect a '{' after 'else' ");
        while(cur!=null && !cur.tokenValue.equals("}")){
            alternayive.add(parseStmt());
        }
        consume("}", "expect a '}' ");

        return new Stmt.IfStmt(condition, consequence, alternayive);
    }

    public Expr parseLogical() throws Exception{
        if(cur == null){
            throw new ParserException();
        }

        if(cur.tokenId == Table.NOT){
            advance();
            consume("(", "expect a '('");
            Expr res = parseLogical();
            consume(")", "expect a ')'");
            return new Expr.Logical("!", null, res);
        }
        Expr left = parseExpr();
        if(!((cur.tokenId>=200 && cur.tokenId<=205) || cur.tokenId==Table.AND || cur.tokenId==Table.OR)){
            throw new ParserException();
        }
        String op = cur.tokenValue;
        advance();
        Expr right = parseExpr();
        return new Expr.Logical(op, left, right);
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
            if(cur != null && cur.tokenId == Table.LB){
                advance();
                return parseCallExpr(name);
            }
            return new Expr.Identify(name);
        }

        throw new SyntaxException();
    }

    private Expr parseCallExpr(String funcname) throws Exception{
        List<Expr> args = new ArrayList<>();
        while (cur!=null && cur.tokenId != Table.RB){
            if(cur.tokenId == Table.CM){
                advance();
            }
            args.add(parseExpr());
        }
        if(cur == null){
            throw new ParserException("error in function call of '" + funcname + "'");
        }
        advance();

        return new Expr.CallExpr(funcname, args);
    }


}

