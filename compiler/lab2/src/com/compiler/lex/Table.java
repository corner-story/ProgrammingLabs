package com.compiler.lex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    public static final int IF = 1;
    public static final int THEN = 2;
    public static final int ELSE = 3;
    public static final int WHILE = 4;
    public static final int BEGIN = 5;
    public static final int END = 6;
    public static final int ID = 7;   //标识符

    public static final int INT = 8;
    public static final int OCTAL = 9;  //八进制整数 0123, 0546
    public static final int HEX = 10;  //十六进制整数 0x123, 0X6ff
    public static final int REAL = 11;   //小数

    public static final int LT = 12; // <
    public static final int LE = 13;
    public static final int EQ = 14;
    public static final int NE = 15;
    public static final int GT = 16;
    public static final int GE = 17;
    public static final int IS = 18;

    public static final int PL = 19; // +
    public static final int MI = 20; // -
    public static final int MU = 21; // *
    public static final int DI = 22; // /

    public static final int SE = 23; //分号
    public static final int CM = 24; //逗号
    public static final int SQM = 25; //单引号
    public static final int DQM = 26; //双引号

    public static final int LB = 27; //左括号
    public static final int RB = 28; //右括号
    public static final int LBR = 29; //左花括号
    public static final int RBR = 30; //右花括号

    public static final int CHAR = 31; //字符常量
    public static final int STRING = 32; //字符串常量


    public static final int OR = 33; //逻辑运算符或 ||
    public static final int AND = 34; //逻辑运算符且 &&
    public static final int NOT = 35; //逻辑运算符非 !


    public static final Map<String, Integer> table = new HashMap<>(){{
        put("IF", Table.IF);
        put("THEN", Table.THEN);
        put("ELSE", Table.ELSE);
        put("WHILE", Table.WHILE);
        put("BEGIN", Table.BEGIN);
        put("END", Table.END);
        put("ID", Table.ID);
        put("INT", Table.INT);
        put("OCTAL", Table.OCTAL);
        put("HEX", Table.HEX);
        put("REAL", Table.REAL);
        put("LT", Table.LT);
        put("LE", Table.LE);
        put("EQ", Table.EQ);
        put("NE", Table.NE);
        put("GT", Table.GT);
        put("GE", Table.GE);
        put("IS", Table.IS);
        put("PL", Table.PL);
        put("MI", Table.MI);
        put("MU", Table.MU);
        put("DI", Table.DI);
        put("SE", Table.SE);
        put("CM", Table.CM);
        put("SQM", Table.SQM);
        put("DQM", Table.DQM);
        put("LB", Table.LB);
        put("RB", Table.RB);
        put("LBR", Table.LBR);
        put("RBR", Table.RBR);
        put("CHAR", Table.CHAR);
        put("STRING", Table.STRING);
        put("OR", Table.OR);
        put("AND", Table.AND);
        put("NOT", Table.NOT);

    }};

    //关键字
    public static String[] KeyWords = {
            "int","double", "if", "else", "while", "for", "then",
            "begin", "end", "printf", "string"
    };


    //查看一个string是不是关键字
    public static boolean lookUp(String string){
        boolean res = false;
        for (int i = 0; i < KeyWords.length; i++) {
            if(string.equals(KeyWords[i])){
                res = true;
                break;
            }
        }
        return res;
    }


    public static void main(String[] args) {
        var s = new ArrayList<String>();
        s.add("a");
        s.add("b");
        s.add("c");
        s.forEach(item -> {
            System.out.println(item);
        });

        System.out.println(lookUp("begin"));
        System.out.println(lookUp("var"));

    }




}
