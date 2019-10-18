package com.java.lab2;

import java.util.Scanner;

//MyDate
public class HW1 {

    //判断是否为闰年
    public boolean isTheFuckYear(int year) throws Exception{
        int y = year;
        //判断世纪年
        if(y%100 == 0 && y%400 == 0){
            return true;
        }
        //判断普通年
        if(y%4 == 0 && y%100 != 0){
            return true;
        }
        return false;
    }

    public boolean checkInputDate(String date){
        try{
            String[] res = date.split("-");
            int year = Integer.valueOf(res[0]);
            int month = Integer.valueOf(res[1]);
            int day = Integer.valueOf(res[2]);

            if(month<=0 || month>=13){
                throw new Exception("month must be 1-12!");
            }

            int shortday = 30;
            int twomonth = 30;  //默认二月有30天
            int bigday = 31;

            //如果是闰年, 二月有29天
            if(isTheFuckYear(year)){
                twomonth = 29;
            }

            //判断day是否合法
            if(month == 2){
                if(day<=0 || day>twomonth){
                    throw new Exception("闰年的二月只有29天!");
                }
                return true;
            }
            //该月有31天
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                if(day<=0 || day>bigday){
                    throw new Exception("该月有31天!");
                }
                return true;
            }
            //该月有30天
            if(day<=0 || day>shortday){
                throw new Exception("该月有30天!");
            }
            return true;

        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String date = "";
        System.out.println("请输入日期, 格式: yyyy-mm-dd\n");
        if(scn.hasNextLine()){
            date = scn.nextLine();
        }

        HW1 test = new HW1();
        test.checkInputDate(date);
    }
}
