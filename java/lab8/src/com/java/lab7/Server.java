package com.java.lab7;
import java.io.*;
import java.net.*;

/*

       1. bw.write(str)  str必须标明以"\n"结尾
       2. <1> 之后 必须有 bw.flush()
       3. 别问我为什么知道, fuck!

*/

public class Server {
    public static int port = 8888;
    public static byte[] buff = new byte[1024];
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

            }

        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server.run();
    }

}
