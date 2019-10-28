package com.java.lab7;

/*
    多线程server, 一个server和多个client聊天, server只能返回client发送的数据
*/


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server3 {
    private int port = 0;
    private String servermsg;

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

                String title = "Thead";
                String client = "Client[" + socket.getRemoteSocketAddress() + "]";
                new SimpleServer(title, client, socket).start();
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

    private String title;
    private String client;
    private Socket socket;

    public SimpleServer(String title, String client, Socket socket){
        this.title = title;
        this.client = client;
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            if(socket == null){
                throw new SocketException();
            }
            System.out.println(client+" connected!");

            //获取socket的input、output stream
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

            String clientmsg = "";
            while((clientmsg = br.readLine()) != null){
                System.out.print("\n"+ Thread.currentThread()+" accept msg ");
                System.out.println("from Client[" + socket.getRemoteSocketAddress()+"]:\t" + clientmsg);

                bw.write(clientmsg+"\n");
                bw.flush();
            }

        }catch (Exception e){
            System.out.println(Thread.currentThread()+" disconnect with "+client);
        }
    }
}