package com.java.lab2;


public class HW2 {


    public static void main(String[] args) {
        Student s = new Student("1234456", "cxk", "184421", "18-08-23", 59.9);
        s.openCount();
        s.storeMoney(10);
        s.storeMoney(30);
        s.getMoney(20);

        System.out.println("学生信息: " + s);
    }
}



class Persion{
    public String idcard;
    public String name;

    protected boolean isOpen = false;
    protected double money = 0;

    public Persion(String idcard, String name){
        this.idcard = idcard;
        this.name = name;
    }

    //开户
    public void openCount(){
        setOpen(true);
    }

    //销户
    public void closeCount(){
        setOpen(false);
    }

    //存款
    public void storeMoney(double money){
        if(isOpen){
            this.money += money;
            System.out.println("存款: " + money + " 余额: " + this.money);
        }
    }

    //取款
    public double getMoney(double money){
        if(isOpen && this.money >= money){
            this.money -= money;
            System.out.println("取款: " + money + " 余额: " + this.money);
            return money;
        }
        return 0;
    }

    //查询
    public double search(){
        if(isOpen){
            return money;
        }
        return 0;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}


class Student extends Persion {
    public String number;
    public String time;
    public double score;

    public Student(String idcard, String name, String number, String time, double score){
        super(idcard, name);
        this.number = number;
        this.time = time;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idcard='" + idcard + '\'' +
                ", name='" + name + '\'' +
                ", isOpen=" + isOpen +
                ", money=" + money +
                ", number='" + number + '\'' +
                ", time='" + time + '\'' +
                ", score=" + score +
                '}';
    }
}