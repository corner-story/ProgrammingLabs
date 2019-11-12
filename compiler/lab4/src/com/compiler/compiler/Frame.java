package com.compiler.compiler;


import java.util.List;
import java.util.Stack;

public class Frame {
    public int pc;
    public List<ByteCode> bytecodes;
    public Stack<Value> stack;
    public Context context;
    public String name;

    public Frame(List<ByteCode> byteCodes){
        this.pc = 0;
        this.bytecodes = byteCodes;
        this.stack = new Stack<>();
        this.context = new Context();
        this.name = "unknown";
    }

    public Frame(String name, List<ByteCode> byteCodes){
        this.pc = 0;
        this.bytecodes = byteCodes;
        this.stack = new Stack<>();
        this.context = new Context();
        this.name = name;
    }

    public void init(){
        this.pc = 0;
        this.stack = new Stack<>();
        this.context = new Context();
    }



    public void init(int pc, Stack<Value> stack, Context context){
        this.pc = pc;
        this.stack = stack;
        this.context = context;
    }
}
