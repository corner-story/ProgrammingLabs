package com.compiler.compiler;

import java.util.ArrayList;
import java.util.List;

public class ByteCode {
    private int id = 0;
    private String bytecode;
    private List<String> args = new ArrayList<>();

    public ByteCode(String bytecode) {
        this.bytecode = bytecode;
    }

    public ByteCode(String bytecode, String args) {
        this.bytecode = bytecode;
        this.args.add(args);
    }

    public ByteCode(String bytecode, String arg1, String arg2) {
        this.bytecode = bytecode;
        this.args.add(arg1);
        this.args.add(arg2);
    }

    public ByteCode(String bytecode, List<String> args) {
        this.bytecode = bytecode;
        this.args = args;
    }

    public int getId() {
        return id;
    }

    public String getBytecode() {
        return bytecode;
    }

    public List<String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "ByteCode{" +
                "id=" + id +
                ", bytecode='" + bytecode + '\'' +
                ", args='" + args + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }
}
