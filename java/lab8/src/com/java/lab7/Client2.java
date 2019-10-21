package com.java.lab7;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static int port = 8888;


    public void run(){
        try{
            Socket client = new Socket("127.0.0.1", port);
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String msgfromserver = br.readLine();
            //System.out.println(msgfromserver);

            if(!msgfromserver.equals("Verifying Server!")){
                System.out.println("Server Wrong!");
                client.close();
                return;
            }
            System.out.println(msgfromserver);
            Scanner scan = new Scanner(System.in);

            while (!msgfromserver.equals("Illegal User!")){
                System.out.println("PassWord: ");
                String pass = scan.nextLine();

                bw.write(pass+"\n");
                bw.flush();
                //System.out.println("客户端发送password: " + pass);
                msgfromserver = br.readLine();

                //System.out.println(msgfromserver);
                if(msgfromserver.equals("Registration Successful!")){
                    break;
                }
            }

            System.out.println(msgfromserver);
            if(msgfromserver.equals("Illegal User!")){
                return;
            }

            //登录成功, 可以聊天
            System.out.println("********************聊天室********************");
            String servermsg = "";
            String clientmsg = "";
            while (true){
                System.out.print("Client["+client.getLocalSocketAddress()+"]"+": ");
                clientmsg = scan.nextLine();
                bw.write(clientmsg+"\n");
                bw.flush();

                servermsg = br.readLine();
                System.out.print("Server["+client.getRemoteSocketAddress()+"]"+": ");
                System.out.println(servermsg);

                //结束聊天判断
                if(servermsg.equals("bye")){
                    break;
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        Client2 client = new Client2();
        client.run();
    }
}
