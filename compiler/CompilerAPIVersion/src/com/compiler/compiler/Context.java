package com.compiler.compiler;

import java.util.HashMap;

public class Context {
    private HashMap<String, Value> context;
    private Context prev;

    public Context(){
        this.context = new HashMap<>();
        this.prev = null;
    }

    public void addValue(String key, Value value){
        this.context.put(key, value);
    }
    public Value getValue(String key){
        Value value = this.context.getOrDefault(key, null);
        if(value != null){
            return value;
        }
        Context p = this.prev;
        while(p != null){
            value = p.getValue(key);
            if(value != null){
                return value;
            }
            p = p.getPrev();
        }
        return null;
    }


    public Context getPrev() {
        return prev;
    }

    public void setPrev(Context prev) {
        this.prev = prev;
    }
}
