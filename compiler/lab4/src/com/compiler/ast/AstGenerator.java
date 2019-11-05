package com.compiler.ast;


import java.util.*;

public class AstGenerator implements Visitor<Void>{
    public List<String> res = new ArrayList<>();

    @Override
    public Void visit(Stmt.DefStmt node) {
        return null;
    }

    @Override
    public Void visit(Stmt.ForStmt node) {
        return null;
    }

    @Override
    public Void visit(Stmt.WhileStmt node) {
        return null;
    }

    @Override
    public Void visit(Expr.Logical node) {
        return null;
    }

    @Override
    public Void visit(Stmt.IfStmt node) {
        return null;
    }

    @Override
    public Void visit(Stmt.PrintStmt node) {
        return null;
    }

    @Override
    public Void visit(Expr.Literal node) {
        System.out.println("load_const\t" + node.value);
        return null;
    }

    @Override
    public Void visit(Expr.OpBinary node) {
        node.left.accept(this);
        node.right.accept(this);

        System.out.println("binary_"+node.op.toLowerCase()+"\t");
        return null;
    }

    @Override
    public Void visit(Expr.Identify node) {
        System.out.println("load_fast\t" + node.name);
        return null;
    }
}
