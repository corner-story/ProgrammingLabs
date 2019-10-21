package com.java.lab5;

public class HW2 {

    public static void main(String[] args) {

        Rabit rabit;
        rabit = new Rabit("Rabit", 1000, 5);  // 新建线程rabit
        Tortoise tortoise = new Tortoise("Tortoise", 3000, 5);  // 新建线程tortoise
        System.out.println("当前线程:\t" + Thread.currentThread().getName());
        //rabit.setPriority(10);
        //tortoise.setPriority(10);
        tortoise.start();  // 启动线程tortoise
        rabit.start();  // 启动线程rabit
    }
}

class Tortoise extends Thread {
    int sleepTime = 0, liveLength = 0;

    public Tortoise(String name, int sleepTime, int liveLength) {
        super(name);
        this.sleepTime = sleepTime;
        this.liveLength = liveLength;

    }

    public void run() {
        while (true) {
            liveLength--;
            System.out.println(getName() + "\t@_@\t"+this.getState());
            try {

                sleep(sleepTime);// 让线程调用sleep()方法进入中断状态
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            if (liveLength <= 0) {

                System.out.println(getName() + "进入死亡状态\n");
                return;// 结束run()方法的语句
            }
        }
    }
}



class Rabit extends Thread {
    int sleepTime=0, liveLength=0;

    public Rabit(String name,int sleepTime, int liveLength) {
        super(name);// 调用父类构造函数，设置线程的名字为name
        this.sleepTime=sleepTime;
        this.liveLength=liveLength;
    }
    public void run() {
        while (true)
        {
            liveLength--;
            System.out.println(getName() + "\t*_*\t"+getState());
            try{
                sleep(sleepTime);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            if (liveLength<=0 )
            {
                System.out.println(getName()+"进入死亡状态\n");
                break;
            }

        }
    }
}
