package com.java.lab1;

public class HW3 {

    public static boolean isFuckNumber(int number){
        for (int i = 2; i < number; i++) {
            if(number % i == 0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("查找100-200的所有素数:");
        for(int i=100; i<=200;i++){
            if(HW3.isFuckNumber(i)){
                System.out.print(i + ", ");
            }
        }
    }
}
