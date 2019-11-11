package com.java.lab7;

/*
    多线程server, 一个server和多个client聊天

*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server3 {
    private int port = 0;
    private String servermsg;
    public static List<SimpleServer> clients = new ArrayList<>();


    public void setPort(int port) {
        this.port = port;
    }

    public void setServermsg(String servermsg) {
        this.servermsg = servermsg;
    }

    public void run(){
        try{
            System.out.println(servermsg);

            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                //主进程一直等待client的连接, 连接成功后创建子进程
                Socket socket = serverSocket.accept();

                new SimpleServer(socket).start();
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }



    public static void main(String[] args) {
        int port = 8888;
        String servermsg = "#\n#\tThe little Server!\n#\n";


        Server3 server3 = new Server3();
        server3.setPort(port);
        server3.setServermsg(servermsg);
        server3.run();
    }
}



class SimpleServer extends Thread{

    private String clientName;
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;

    public boolean isConnected;

    public SimpleServer(Socket socket) throws Exception{
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        this.clientName = "Client[" + socket.getRemoteSocketAddress().toString() + "]";
        this.isConnected = true;
        Server3.clients.add(this);
        System.out.println(clientName + " connected !");
    }

    public void send(String msg) throws Exception{
        String sendMsg = this.clientName + ":\t" + msg;
        System.out.println(sendMsg);
        for (SimpleServer client : Server3.clients){

            if(client != this && client.isConnected){
                client.getBw().write(sendMsg + "\n");
                client.getBw().flush();
            }
        }
    }

    public void receive() throws Exception{
        String clientmsg;
        if((clientmsg = br.readLine()) != null){
            send(clientmsg);
        }
    }

    @Override
    public void run() {
        try {
            while(isConnected){
                receive();
            }

        }catch (Exception e){
            System.out.println(e);
        }finally {
            try{
//                System.out.println(clientName + " exit!");
                isConnected = false;
                Server3.clients.remove(this);
                this.send(clientName + " has exit!");

                if(br != null){ br.close(); }
                if(bw != null){ bw.close(); }
                if(socket != null){ socket.close();}
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public String getClientName() {
        return clientName;
    }

    public BufferedWriter getBw() {
        return bw;
    }
}