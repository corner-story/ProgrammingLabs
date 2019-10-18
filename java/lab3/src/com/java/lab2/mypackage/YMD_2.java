package com.java.lab2.mypackage;

public class YMD_2 {
    private String name;
    private YMD birth;

    public YMD_2(String name, int year, int month, int day){
        birth = new YMD(year, month, day);
        this.name = name;
    }

    public YMD_2(){
        this("cxk", 2000, 1, 1);
    }

    public int getAge(){
        return birth.thisyear() - birth.year();
    }

    public void output(){
        System.out.println("姓名: " + name);
        System.out.println("出生日期: " + birth);
        System.out.println("年龄: " + getAge());
    }


    public static void main(String[] args) {
        YMD_2 y = new YMD_2("cxk", 2018, 12, 5);
        y.output();
    }

}
