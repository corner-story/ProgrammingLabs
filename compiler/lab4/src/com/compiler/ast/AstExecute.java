package com.compiler.ast;

import java.util.HashMap;
import java.util.Map;

public class AstExecute implements Visitor<Double> {
    public Map<String, String> context;

    @Override
    public Double visit(Stmt.ReturnStmt node) {
        return null;
    }

    @Override
    public Double visit(Expr.DoNothing node) {
        return null;
    }

    @Override
    public Double visit(Expr.CallExpr node) {
        return null;
    }

    @Override
    public Double visit(Stmt.FuncStmt node) {
        return null;
    }

    @Override
    public Double visit(Stmt.CallStmt node) {
        return null;
    }

    @Override
    public Double visit(Stmt.ForStmt node) {
        return null;
    }

    @Override
    public Double visit(Stmt.BreakOrContinueStmt node) {
        return null;
    }

    @Override
    public Double visit(Stmt.WhileStmt node) {
        return null;
    }

    public AstExecute(Map<String, String> context){
        this.context = context==null? new HashMap<>() : context;
    }

    @Override
    public Double visit(Stmt.DefStmt node) {
        return null;
    }

    @Override
    public Double visit(Stmt.PrintStmt node) {
        return null;
    }

    @Override
    public Double visit(Expr.Literal node) {

        return Double.valueOf(node.value);
    }

    @Override
    public Double visit(Expr.Logical node) {
        return null;
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
    public Double visit(Stmt.IfStmt node) {
        return null;
    }

    @Override
    public Double visit(Expr.Identify node) {
        return Double.valueOf(this.context.get(node.name));
    }
}
