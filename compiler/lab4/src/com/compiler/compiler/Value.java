package com.compiler.compiler;

public class Value<T>{
    public int type;
    public T value;

    public Value(int type, T value){
        this.type = type;
        this.value = value;
    }

    public T getValue() {
        return value;
    }



    public static int INT = 0;
    public static int DOUBLE = 1;
    public static int STRING = 2;
    public static int CHAR = 3;

    public static int BOOL = 4;

}






//public abstract class Value {
//    public static class IntObject extends Value{
//        public int value;
//        public IntObject(int value){
//            this.value = value;
//            this.type = "int";
//        }
//
//        @Override
//        public String toString() {
//            return String.valueOf(value);
//        }
//    }
//
//    public static class DoubleObject extends Value{
//        public double value;
//        public DoubleObject(double value){
//            this.value = value;
//            this.type = "double";
//        }
//
//        @Override
//        public String toString() {
//            return String.valueOf(value);
//        }
//    }
//
//    public static class StringObject extends Value{
//        public String value;
//        public StringObject(String value){
//            this.value = value;
//            this.type = "string";
//        }
//
//        @Override
//        public String toString() {
//            return value;
//        }
//    }
//
//    public String type = "Value";
//
//}
