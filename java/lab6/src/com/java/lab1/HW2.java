package com.java.lab1;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class HW2 {

    public static void main(String[] args) {
        int[] a= {12,34,9,-23,45,6,90,123,19,45,34};

        //升序排序
        Arrays.sort(a);
        int left = 0;
        int right = a.length -1;
        int mid = (left + right) / 2;

        int tofind = 0;
        Scanner scn = new Scanner(System.in);
        if(scn.hasNextLine()){
            tofind = Integer.valueOf(scn.nextLine());
        }

        System.out.println("升序数组:");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ", ");
        }
        System.out.println("");

        while(left <= right){
            if(a[mid] == tofind){
                break;
            }
            if(a[mid] < tofind){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
            mid = (left + right) / 2;
        }
        if(left > right){
            System.out.println("can't find the number in array!");return;
        }
        System.out.println("find the number in array at index = " + mid);
    }
}
