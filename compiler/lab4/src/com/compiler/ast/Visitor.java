package com.compiler.ast;

public interface Visitor {
//    public abstract void visit(Node node);
//    public abstract void visit(Expr node);
    public abstract void visit(Expr.Literal node);
    public abstract void visit(Expr.OpBinary node);
    public abstract void visit(Stmt.DefineStmt node);
}
