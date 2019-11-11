package com.java.lab5;

public class HW3 {
    public static void main(String[] args) {
        String[] usernames = {"赵", "钱", "孙", "李", "周"};
        int[] moneys = {20, 20, 10, 10, 5};
        int[] tickets = {2,1,1,2,1};

        try{
            Thread[] threads = new WaitForTickets[5];
            for(int i=0; i<usernames.length; i++){
                threads[i] = new WaitForTickets(usernames[i], moneys[i], tickets[i]);
                threads[i].start();

            }

            for(int i=0; i<threads.length; i++){
                threads[i].join();
            }

        }catch (Exception e){
            System.out.println(e);
        }

        System.out.println("\n购票结束\n");

    }
}


class WaitForTickets extends Thread{
    private String username;
    private int money;
    private int tickets; //想要买的票数
    private int own;    //已经买到的票数, 初始值为0

    public WaitForTickets(String username, int money, int tickets){
        this.username = username;
        this.money = money;
        this.tickets = tickets;
        this.own = 0;
    }

    @Override
    public void run(){
        try {

            while(own != tickets){

                Seller.saleTickets(this);
                if(own != tickets){
                    Thread.sleep(Math.round(10)*10);
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }



    public int getOwn() {
        return own;
    }

    public void setOwn(int own) {
        this.own = own;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
}

class Seller{
    public static int change5 = 1;
    public static int charge10 = 0;
    public static int charge20 = 0;

    public static synchronized void saleTickets(WaitForTickets person) throws Exception{
        String msg = String.format("%s\t\t给钱%d元\t\t想买%d张票\t\t", person.getUsername(), person.getMoney(), person.getTickets());
        System.out.print(msg);

        if(person.getMoney() == 5 && person.getTickets() == 1){
            System.out.print("购票成功, 没有找零!");
            change5++;
            person.setMoney(0);
            person.setOwn(1);

        }

        if(person.getMoney() == 10 && person.getTickets() == 2){
            System.out.print("购票成功, 没有找零!");
            charge10++;
            person.setMoney(0);
            person.setOwn(2);

        }

        if(person.getMoney() == 10 && person.getTickets() == 1){
            if(change5 > 0){
                System.out.print("购票成功, 找零5元!");
                change5--;
                charge10++;
                person.setMoney(5);
                person.setOwn(1);
            }else{
                System.out.print("购票失败, 没有5元零钱啊!");
            }

        }

        if(person.getMoney() == 20 && person.getTickets() == 2){
            if(charge10 > 0){
                System.out.print("购票成功, 找零10元!");
                charge10--;
                charge20++;
                person.setMoney(10);
                person.setOwn(2);
            }else{
                System.out.print("购票失败, 没有10元零钱啊!");
            }

        }

        if(person.getMoney() == 20 && person.getTickets() == 1){
            if(charge10>0 && change5>0){
                System.out.print("购票成功, 找零一张10元和一张5元!");
                charge10--;
                change5--;
                charge20++;
                person.setMoney(15);
                person.setOwn(1);
            }else{
                System.out.print("购票失败, 没有一张10元和一张5元的零钱!");
            }
            ;
        }

        System.out.println("\t\t\tSeller的零钱\t\t5元零钱:"+Seller.change5+"\t\t10元零钱:"+Seller.charge10+"\t\t20元零钱:"+Seller.charge20);


    }
}