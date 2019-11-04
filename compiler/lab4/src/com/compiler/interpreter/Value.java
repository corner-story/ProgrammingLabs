package com.compiler.interpreter;

public abstract class Value {
    public static class IntObject extends Value{
        public int value;
        public IntObject(int value){
            this.value = value;
            this.type = "int";
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static class DoubleObject extends Value{
        public double value;
        public DoubleObject(double value){
            this.value = value;
            this.type = "double";
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static class StringObject extends Value{
        public String value;
        public StringObject(String value){
            this.value = value;
            this.type = "string";
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public String type = "Value";
}
