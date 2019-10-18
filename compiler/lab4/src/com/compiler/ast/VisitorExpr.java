package com.compiler.ast;



public class VisitorExpr implements Visitor {

    @Override
    public void visit(Expr.Literal node) {
        System.out.print(node.value);
    }

    @Override
    public void visit(Expr.OpBinary node) {

        System.out.print("(");
        node.left.accept(this);
        System.out.print(node.op);
        node.right.accept(this);
        System.out.print(")");
    }


    @Override
    public void visit(Stmt.DefineStmt node) {

    }
}
