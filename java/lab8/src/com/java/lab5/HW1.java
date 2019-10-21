package com.java.lab5;


public class HW1 {

    public static void main(String[] args) {
        EvenThread et = new EvenThread("偶数线程");
        OddThread ot = new OddThread("奇数线程");

        System.out.println("当前线程:\t" + Thread.currentThread().getName());
        et.start();
        ot.start();


    }
}


class EvenThread extends Thread{
    public String name;

    public EvenThread(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.print("\n偶数序列: ");
        int num = 0;
        for (int i = 1; i <= 100; i++) {
            if(i%2 ==0){
                num++;
                System.out.print(i+" ");
            }
        }
        System.out.print("\t共有"+num+"个偶数!\n");
    }
}

class OddThread extends Thread{
    public String name;

    public OddThread(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.print("\n奇数序列: ");
        int num = 0;
        for (int i = 1; i <= 100; i++) {
            if(i%2 !=0){
                num++;
                System.out.print(i+" ");
            }
        }
        System.out.print("\t共有"+num+"个奇数!\n");
    }
}
