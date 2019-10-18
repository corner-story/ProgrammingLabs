package com.compiler.lex;

import java.util.ArrayList;
import java.util.List;

public class Table {

    public static final int IF = 1;
    public static final int THEN = 2;
    public static final int ELSE = 3;
    public static final int WHILE = 4;
    public static final int BEGIN = 5;
    public static final int END = 6;
    public static final int ID = 7;

    public static final int INT = 8;
    public static final int OCTAL = 9;  //八进制整数 0123, 0546
    public static final int HEX = 10;  //十六进制整数 0x123, 0X6ff
    public static final int REAL = 11;   //小数

    public static final int LT = 30; // <
    public static final int LE = 31;
    public static final int EQ = 12;
    public static final int NE = 13;
    public static final int GT = 14;
    public static final int GE = 15;
    public static final int IS = 16;

    public static final int PL = 17; // +
    public static final int MI = 18; // -
    public static final int MU = 19;
    public static final int DI = 20;

    public static final int SE = 21; //分号
    public static final int CM = 22; //逗号
    public static final int SQM = 23; //单引号
    public static final int DQM = 24; //双引号

    public static final int LB = 25; //左括号
    public static final int RB = 26; //右括号
    public static final int LBR = 42; //左花括号
    public static final int RBR = 43; //右花括号

    public static final int CHAR = 40; //字符常量
    public static final int STRING = 41; //字符串常量


    public static final int OR = 27; //逻辑运算符或 ||
    public static final int AND = 28; //逻辑运算符且 &&
    public static final int NOT = 29; //逻辑运算符非 !


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
