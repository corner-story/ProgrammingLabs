package com.java.lab7;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client3 {
    private int connectPort;
    private String connectAddr;

    public void connect(){
        try {
            Socket socket = new Socket(connectAddr, connectPort);
            System.out.println("connect Server["+connectAddr+":"+connectPort+" success!");

            //获取socket的input、output stream
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));

            String servermsg = "";
            String msg = "";
            Scanner scan = new Scanner(System.in);

            while(true){
                System.out.println("输入信息:");
                msg = scan.nextLine();
                bw.write(msg+"\n");
                bw.flush();

                servermsg = br.readLine();
                System.out.println(socket.getRemoteSocketAddress()+" :\t"+servermsg);

            }



        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void setConnectPort(int connectPort) {
        this.connectPort = connectPort;
    }

    public void setConnectAddr(String connectAddr) {
        this.connectAddr = connectAddr;
    }

    public static void main(String[] args) {
        Client3 client3 = new Client3();
        client3.setConnectPort(8888);
        client3.setConnectAddr("127.0.0.1");
        client3.connect();
    }
}
