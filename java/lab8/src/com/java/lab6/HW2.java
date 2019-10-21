package com.java.lab6;

import com.java.lab4.Student;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HW2 {

    public static void main(String[] args) {
        try{
            File file1 = new File(".\\test\\myfile1.txt");
            File file2 = new File(".\\test\\myfile2.txt");

            FileWriter out = new FileWriter(file1, StandardCharsets.UTF_8);
            Scanner scanner = new Scanner(System.in);
            int num = 0;
            System.out.print("请输入学生个数: ");
            num = Integer.valueOf(scanner.nextLine());
            for (int i = 0; i < num; i++) {
                System.out.println("\n第" + (i+1) + "名学生信息:");

                System.out.print("姓名: ");
                String name = scanner.nextLine();
                System.out.print("年龄: ");
                int age = Integer.valueOf(scanner.nextLine());
                System.out.print("出生日期: ");
                String birth = scanner.nextLine();
                System.out.print("成绩: ");
                double score = Double.valueOf(scanner.nextLine());
                var student = new Student(name, age, birth, score);

                out.write(student.toString()+"\n");
            }
            System.out.println("信息写入myfile1.txt!");
            out.close();

            System.out.println("myfile1.txt --> myfile2.txt\n文件内容:");
            FileReader in = new FileReader(file1, StandardCharsets.UTF_8);
            out = new FileWriter(file2, StandardCharsets.UTF_8);
            int n = -1;
            char[] buff = new char[64];
            while ((n = in.read(buff)) != -1){
                String temp = new String(buff, 0, n);
                System.out.print(temp);
                out.write(buff, 0, n);
            }

            in.close();
            out.close();
            scanner.close();
        }catch (Exception e){
            System.out.println(e);
        }



    }
}



