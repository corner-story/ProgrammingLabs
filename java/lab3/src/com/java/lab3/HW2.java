package com.java.lab3;


public class HW2 {

    public static void main(String[] args) {
        try{

            String input = args.length==0 ? "" : args[0];

            if(input.equals("XYZ")){
                throw new XYZException("输入XYZ！");
            }else if(input.equals("ABC")){
                System.out.println("输入ABC");
            }


        }catch (XYZException e){
            System.out.println(e);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}


class XYZException extends RuntimeException{
    public XYZException(String msg){
        super(msg);
    }
}
