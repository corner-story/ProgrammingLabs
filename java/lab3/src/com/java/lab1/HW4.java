package com.java.lab1;

import java.util.ArrayList;
import java.util.List;

public class HW4 {

    public static boolean isFuckNumber(int number){
        int res = number;
        for (int i = 1; i < number; i++) {
            if(number % i == 0){
                res -= i;
            }
        }
        return res == 0;
    }

    public static void main(String[] args) {
        System.out.println("输出1-1000的所有完全数:");
        for (int i = 1; i < 1000; i++) {
            if(HW4.isFuckNumber(i)){
                System.out.print(i + ", ");
            }
        }
    }
}
