package com.compiler.ast;

public interface Visitor<R> {
//    public abstract void visit(Node node);
//    public abstract void visit(Expr node);
    public abstract R visit(Expr.Literal node);
    public abstract R visit(Expr.OpBinary node);
    public abstract R visit(Expr.Identify node);
    public abstract R visit(Expr.Logical node);


    public abstract R visit(Stmt.DefStmt node);
    public abstract R visit(Stmt.PrintStmt node);

    public abstract R visit(Stmt.IfStmt node);
    public abstract R visit(Stmt.WhileStmt node);
    public abstract R visit(Stmt.ForStmt node);
    public abstract R visit(Stmt.BreakOrContinueStmt node);

}
