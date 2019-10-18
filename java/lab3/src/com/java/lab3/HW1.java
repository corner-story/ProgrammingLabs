package com.java.lab3;

public class HW1 {

    public static void main(String[] args) {
        try{
            String[] tests = {"hello", "cxk", "hhhh"};
            int i = 0;
            while(i< (tests.length+1)){
                System.out.println(tests[i]);
                i++;
            }

        }catch (Exception e){
            System.out.println(e);
        }finally {
            System.out.println("总会执行！");
        }
    }


}
