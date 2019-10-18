package com.compiler.ast;

public abstract class Stmt implements Node{


    public static class DefineStmt extends Stmt{
        public String declation;
        public String identify;
        public Expr value;

        public DefineStmt(String declation, String identify, Expr value){
            this.declation = declation;
            this.identify = identify;
            this.value = value;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }




}
