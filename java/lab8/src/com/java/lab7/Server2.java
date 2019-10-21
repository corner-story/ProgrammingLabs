package com.java.lab7;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server2 {
    public static int port = 8888;
    //public static byte[] buff = new byte[1024];
    private static String secret = "lambdafate";

    public static void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(serverSocket.getLocalSocketAddress());
            System.out.println(serverSocket.getLocalPort());
            System.out.println("Windows10 等待连接....");
            while(true){

                Socket socket = serverSocket.accept(); //等待连接
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                int errortimes = 3;

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
                bw.write("Verifying Server!\n");
                bw.flush();

                String verify = "";
                while(errortimes > 0){

                    verify = br.readLine();
                    errortimes--;

                    System.out.println(errortimes + "\t" + verify);
                    if(!secret.equals(verify) && errortimes!=0){
                        bw.write("PassWord Wrong!\n");
                        bw.flush();
                    }else{
                        break;
                    }
                }
                String res = "Registration Successful!\n";
                if(!secret.equals(verify)){
                    res = "Illegal User!\n";
                }

                bw.write(res);
                bw.flush();

                if(!secret.equals(verify)){
                    continue;
                }

                //登录成功, 可以撩骚
                System.out.println("********************聊天室********************");
                Scanner scan = new Scanner(System.in);
                String clientmsg = "";
                String servermsg = "";
                while(!(clientmsg = br.readLine()).equals("bye")){
                    System.out.print("Client["+socket.getRemoteSocketAddress()+"]"+": ");
                    System.out.println(clientmsg);
                    System.out.print("Server["+socket.getLocalSocketAddress()+"]"+":");
                    servermsg = scan.nextLine();
                    bw.write(servermsg+"\n");
                    bw.flush();
                }
                System.out.print("Client["+socket.getRemoteSocketAddress()+"]"+": bye");
                bw.write("bye\n");
                bw.flush();
            }

        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server2.run();
    }
}
