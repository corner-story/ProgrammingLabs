package com.java.lab1;

public class HW5 {


    public static void main(String[] args) {
        System.out.println("计算 XYZ + YZZ = 532 ???");
        int x = 0; //0-5
        int y = 0; //0-5
        int z = 1; //1 or 6

        int left=0, right=0, sum=0;
        for (x=0; x < 6; x++) {
            for (y = 0; y < 6; y++) {
                left = x*100 + y*10 + z;
                right = y*100 + z*10 + z;
                sum = left + right;
                if(sum == 532){
                    break;
                }
            }
            if(sum == 532){
                break;
            }
        }
        System.out.println(left+ " + " + right + " = " + (left+right));
        System.out.println("x="+x+" y="+y+" z="+z);
    }
}
