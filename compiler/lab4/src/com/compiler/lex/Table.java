package com.compiler.lex;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    //关键字
    public static final int INT = 1;
    public static final int DOUBLE = 2;
    public static final int STRING = 3;
    public static final int CHAR = 4;
    public static final int IF = 5;
    public static final int ELSE = 6;
    public static final int WHILE = 7;
    public static final int FOR = 8;
    public static final int PRINTF = 9;
    public static final int TRUE = 10;
    public static final int FALSE = 11;


    public static final int ID = 100;   //标识符

    public static final int LITERAL_INT = 150;
    public static final int LITERAL_OCTAL = 151;  //八进制整数 0123, 0546
    public static final int LITERAL_HEX = 152;  //十六进制整数 0x123, 0X6ff
    public static final int LITERAL_REAL = 153;   //小数

    public static final int LITERAL_CHAR = 154; //字符常量
    public static final int LITERAL_STRING = 155; //字符串常量

    public static final int LT = 200; // <
    public static final int LE = 201;
    public static final int EQ = 202;   // = 判断是否相等
    public static final int NE = 203;
    public static final int GT = 204;
    public static final int GE = 205;
    public static final int IS = 206;  // := 赋值

    public static final int PL = 300; // +
    public static final int MI = 301; // -
    public static final int MU = 302; // *
    public static final int DI = 303; // /

    public static final int SE =350; //分号
    public static final int CM = 351; //逗号
    public static final int SQM = 352; //单引号
    public static final int DQM = 353; //双引号

    public static final int LB = 400; //左括号
    public static final int RB = 401; //右括号
    public static final int LBR = 402; //左花括号
    public static final int RBR = 403; //右花括号

    public static final int OR = 500; //逻辑运算符或 ||
    public static final int AND = 501; //逻辑运算符且 &&
    public static final int NOT = 502; //逻辑运算符非 !


    public static final Map<String, Integer> table = new HashMap<>(){{
        put("INT", Table.INT);
        put("DOUBLE", Table.DOUBLE);
        put("STRING", Table.STRING);
        put("CHAR", Table.CHAR);
        put("IF", Table.IF);
        put("ELSE", Table.ELSE);
        put("WHILE", Table.WHILE);
        put("FOR", Table.FOR);
        put("PRINTF", Table.PRINTF);
        put("TRUE", Table.TRUE);
        put("FALSE", Table.FALSE);
        put("ID", Table.ID);
        put("LITERAL_INT", Table.LITERAL_INT);
        put("LITERAL_OCTAL", Table.LITERAL_OCTAL);
        put("LITERAL_HEX", Table.LITERAL_HEX);
        put("LITERAL_REAL", Table.LITERAL_REAL);
        put("LITERAL_CHAR", Table.LITERAL_CHAR);
        put("LITERAL_STRING", Table.LITERAL_STRING);
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
        put("OR", Table.OR);
        put("AND", Table.AND);
        put("NOT", Table.NOT);


    }};

    //关键字
    public static String[] KeyWords = {
            "int","double", "string", "char", "if", "else", "while", "for",
            "printf", "true", "false"
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


    }

}
