package com.compiler.compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Frame {
    public int pc;
    public List<ByteCode> bytecodes;
    public Stack<Value> stack;
    public Map<String, Value> context;

    public Frame(List<ByteCode> byteCodes){
        this.pc = 0;
        this.bytecodes = byteCodes;
        this.stack = new Stack<>();
        this.context = new HashMap<>();
    }

    public void init(){
        this.pc = 0;
        this.stack = new Stack<>();
        this.context = new HashMap<>();
    }
}
