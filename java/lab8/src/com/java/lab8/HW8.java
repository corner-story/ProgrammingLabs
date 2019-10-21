package com.java.lab8;

import java.sql.*;

public class HW8 {

    public static void insert(Statement stm) throws Exception{
        String[] names = {"cxk", "jojo", "jbl", "mas"};
        String[] sexs = {"man", "woman", "midsex"};
        int[] ages = {10, 12, 13, 15, 20, 25, 28};
        for (int i = 0; i < 20; i++) {
            stm.executeUpdate("insert into student(name, sex, age) values('" + (names[i%4]+i)+ "', '"+ sexs[i%3] +"', '"+ ages[i%7]+"');");
        }
        stm.executeUpdate("insert into student(name, sex, age) values('cxk', 'women', '12');");
    }

    public static void main(String[] args) {
        SqliteDB.Drivde = "org.sqlite.JDBC";
        SqliteDB.URL = "jdbc:sqlite:.\\database\\Studentinfo.db";
        try{

            Statement stm = SqliteDB.connect();
            if(stm == null){
                return;
            }
            stm.executeUpdate("delete from student");
            HW8.insert(stm);



            System.out.println("查询所有记录:");
            ResultSet res = stm.executeQuery("select * from student;");
            while (res.next()){
                System.out.println(res.getString(1)+"\t"+res.getString(2)+"\t"+res.getString(3)+"\t"+ res.getString(4));
            }

            System.out.println("\n查询age>18的所有记录:");
            res = stm.executeQuery("select * from student where age>18;");
            while (res.next()){
                System.out.println(res.getString(1)+"\t"+res.getString(2)+"\t"+res.getString(3)+"\t"+ res.getString(4));
            }


            System.out.println("删除所有sex=midsex的记录");
            stm.executeUpdate("delete from student where sex='midsex';");

            System.out.println("\n把所有的name='cxk'的记录 age改为1000");
            stm.executeUpdate("update student set age=1000 where name='cxk'");

            System.out.println("查询所有记录:");
            res = stm.executeQuery("select * from student");
            while (res.next()){
                System.out.println(res.getString(1)+"\t"+res.getString(2)+"\t"+res.getString(3)+"\t"+ res.getString(4));
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
}

class SqliteDB{
    public static String Drivde=null;
    public static String URL = null;
    private static Statement sql = null;

    public static Statement connect(){
        try{
            Class.forName(Drivde);
            Connection connection= DriverManager.getConnection(URL);
            sql = connection.createStatement();

        }catch (SQLException e){
            System.out.println(e);
        }catch (Exception e){
            System.out.println(e);
        }
        return sql;
    }

    public static void close(){
        try{
            if(sql != null){
                sql.close();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
