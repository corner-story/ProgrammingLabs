package com.java.lab4;

public class Student {
    public static int number = 0;
    public int id;

    public String name;
    public int age;
    public String birth;
    public double score;

    public Student(String name, int age, String birth, double score){
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.score = score;
        this.id = ++number;
    }

    public String toString(){
        return id + "\t\t" + name + "\t\t" + age + "\t\t" + birth + "\t\t" + score;
    }

}
