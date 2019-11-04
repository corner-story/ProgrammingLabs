package com.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class ExecuteStmt implements Visitor<Object> {
    @Override
    public Object visit(Stmt.IfStmt node) {
        return null;
    }

    public class Value{
        public String types;
        public String value;

        public Value(String types, String value) {
            this.types = types;
            this.value = value;
        }

        public Value(String types) {
            this.types = types;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @Override
    public Object visit(Expr.Logical node) {
        return null;
    }

    public String types;

    public static Map<String, Object> context = new HashMap<>();

    @Override
    public Object visit(Expr.Literal node) {
        return new Value(node.kind, node.value);
    }

    @Override
    public Object visit(Expr.OpBinary node) {
        Value left = (Value) node.left.accept(this);
        Value right = (Value) node.right.accept(this);
        Value value = new Value(types);

        if(types.equals("string")){
            value.value = left.value + right.value;
        }else if(types.equals("int") || types.equals("double")){
            switch (node.op){
                case "+":  value.value = String.valueOf(Double.valueOf(left.value) + Double.valueOf(right.value));break;
                case "-":  value.value = String.valueOf(Double.valueOf(left.value) - Double.valueOf(right.value));break;
                case "*":  value.value = String.valueOf(Double.valueOf(left.value) * Double.valueOf(right.value));break;
                case "/":  value.value = String.valueOf(Double.valueOf(left.value) / Double.valueOf(right.value));break;
            }
        }

        return value;
    }

    @Override
    public Object visit(Expr.Identify node) {

        return context.get(node.name);
    }

    @Override
    public Object visit(Stmt.DefStmt node) {
        types = node.kind;
        Value value = (Value)node.expression.accept(this);
        value.types = node.kind;
        context.put(node.identify, value);
        return value;
    }

    @Override
    public Object visit(Stmt.PrintStmt node) {

        Value value = (Value) node.expression.accept(this);
        System.out.print(value);
        return value;
    }
}
