package com.java.lab6;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HW1 {

    public static void main(String[] args) {
        try{
            File fileone = new File(".\\test\\hello.txt");
            File filetwo = new File(".\\test\\hello.secret");
            FileReader in = new FileReader(fileone, StandardCharsets.UTF_8);
            FileWriter out = new FileWriter(filetwo, StandardCharsets.UTF_8);
            char[] buff = new char[100];
            int n = -1;
            while ((n = in.read(buff)) != -1){
                for (int i = 0; i < n; i++) {
                    buff[i] = (char) (buff[i] ^ 'a');
                    //System.out.println(buff[i]);
                }
                out.write(buff, 0, n); //note there! out.write(buff) is error!
            }
            in.close();
            out.close();

            in = new FileReader(filetwo, StandardCharsets.UTF_8);
            n = -1;
            System.out.println("加密后的文件内容:");
            while((n = in.read(buff)) != -1){
                String temp = new String(buff, 0, n);
                System.out.print(temp);
            }
            in.close();

            in = new FileReader(filetwo, StandardCharsets.UTF_8);
            n = -1;
            System.out.println("\n解密后的文件内容:");
            while((n = in.read(buff)) != -1){
                for (int i = 0; i < n; i++) {
                    buff[i] = (char)(buff[i] ^ 'a');
                }
                String temp = new String(buff, 0, n);
                System.out.print(temp);
            }
            in.close();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
