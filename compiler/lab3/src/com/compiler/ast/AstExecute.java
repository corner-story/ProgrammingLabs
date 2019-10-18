package com.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class AstExecute implements Visitor<Double> {
    public Map<String, String> context;

    public AstExecute(Map<String, String> context){
        this.context = context==null? new HashMap<>() : context;
    }


    @Override
    public Double visit(Expr.Literal node) {

        return Double.valueOf(node.value);
    }

    @Override
    public Double visit(Expr.OpBinary node) {
        double left = node.left.accept(this);
        double right = node.right.accept(this);

        switch (node.op){
            case "+": return left+right;
            case "-": return left-right;
            case "*": return left*right;
            case "/": return left/right;
        }
        return null;
    }

    @Override
    public Double visit(Expr.Identify node) {
        return Double.valueOf(this.context.get(node.name));
    }
}
