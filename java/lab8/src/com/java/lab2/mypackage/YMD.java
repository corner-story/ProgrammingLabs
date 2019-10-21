package com.java.lab2.mypackage;

import java.util.Calendar;

public class YMD {
    private int year;
    private int month;
    private int day;

    public YMD(int year, int month, int day){
        this.year = year;
        this.month = (month<1||month>12)? 1: month;
        this.day = (day<1||day>31)? 1: day;
    }

    public YMD(){
        this(0,0,0);
    }

    public int year(){
        return this.year;
    }


    public static int thisyear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public String toString() {
        return year +"-" + month+"-"+day;
    }


    public static void main(String[] args) {
        System.out.println(YMD.thisyear());
    }
}
