package com.java.lab1;

import java.sql.SQLOutput;
import java.util.Scanner;

public class HW1 {

    public static void main(String[] args) {
        String name="", birth="";
        int age = 0;
        double score = 0;

        Scanner scn = new Scanner(System.in);

        for (int i = 0; i < 10; i++) {
            System.out.print("输入姓名:\t");
            if(scn.hasNextLine()){
                name = scn.nextLine();
            }
            System.out.print("输入出生日期:\t");
            if(scn.hasNextLine()){
                birth = scn.nextLine();
            }
            System.out.print("输入年龄:\t");
            if(scn.hasNextLine()){
                age = Integer.valueOf(scn.nextLine());
            }
            System.out.print("输入分数:\t");
            if(scn.hasNextDouble()){
                score = Double.valueOf(scn.nextLine());
            }
            new Student(name, age, birth, score);
        }

        Student.printMsg();
    }

}

class Student {
    public String name;
    public int age;
    public String birth;
    public double score;

    public static int allage = 0;
    public static double allscore = 0;
    public static int number = 0;

    public Student(String n, int a, String b, double s){
        name = n;
        age = a;
        birth = b;
        score = s;

        number += 1;
        allage += a;
        allscore += s;
    }

    public static void printMsg(){
        System.out.println("所有学生年龄平均值:\t" + allage/number);
        System.out.println("所有学生分数平均值:\t" + allscore/number);
    }
}