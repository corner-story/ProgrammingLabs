package com.java;




//过山洞
public class Main {
    public static void main(String[] args) throws Exception{
        Person[] people = new Person[10];
        for(int i=0; i<10; i++){
            people[i] = new Person(String.valueOf(i));
        }
        for(int i=0; i<10; i++){
            people[i].start();
        }

        for (int i = 0; i < 10; i++) {
            people[i].join();
        }

        System.out.println("\n全部通过山洞!");
    }
}


class Person extends Thread{
    public String name;
    public Person(String name){
        this.name = name;
    }

    @Override
    public void run(){
        PassHole.pass(this.name);
    }
}


class PassHole{

    public static synchronized void pass(String name){

        try{
            System.out.println("\n"+name + "\t即将穿过山洞...");
            Thread.sleep(1000);
            System.out.println(name + "\t穿过山洞\n");

        }catch (Exception e){
            System.out.println(e);
        }


    }
}

