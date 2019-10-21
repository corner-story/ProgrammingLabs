package com.compiler.parser;

import com.compiler.error.SyntaxException;
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

    List<Expr> res = new ArrayList<>();

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

    public void consume(String value, String error) throws Exception{
        if(cur == null || !cur.tokenValue.equals(value)){
            String location = "";
            if(cur != null){
                location = " at " + cur.row + " row " + cur.beginclo + " column";
            }
            throw new SyntaxException(error + location);
        }
        advance();
    }

    public void parse(){
        try{

            //AstPrint ve = new AstPrint();
            Map<String, String> context = new HashMap<>();
            context.put("name", "6");
            AstGenerator bytecode = new AstGenerator();
            AstTransform astTransform = new AstTransform();
            AstExecute execute = new AstExecute(context);

            advance();
            if(cur == null){
                return;
            }
            while (cur != null){
                Expr expr = parseExpr();
                System.out.println("字节码:");
                expr.accept(bytecode);
                System.out.println("\n四元式:");
                expr.accept(astTransform);
                astTransform.print();
                System.out.println("计算结果:");
                System.out.println(expr.accept(execute));
                //System.out.println(expr);
            }

        }catch (Exception e){
            System.out.println(e);
        }

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
        if(cur.tokenId == Table.LITERAL_INT || cur.tokenId == Table.LITERAL_REAL){
            String tc = cur.tokenClass;
            String tv = cur.tokenValue;
            advance();
            return new Expr.Literal(tv, tc);
        // -factor
        }else if(cur.tokenId == Table.MI){
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
        parser.parse();
    }
}
