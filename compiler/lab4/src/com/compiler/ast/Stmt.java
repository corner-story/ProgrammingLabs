package com.compiler.ast;

public abstract class Stmt implements Node{

    public static class DefStmt extends Stmt{
        public String kind;   // int double char string
        public String identify;
        public Expr expression;

        public DefStmt(String kind, String identify , Expr expression){
            this.kind = kind;
            this.identify = identify;
            this.expression = expression;
            this.types = "DefStmt";
        }

        @Override
        public String toString() {
            return "DefStmt{" +
                    "kind='" + kind + '\'' +
                    ", identify='" + identify + '\'' +
                    ", expression=" + expression +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }



    public static class PrintStmt extends Stmt{
        public Expr expression;

        public PrintStmt(Expr expression){
            this.expression = expression;
            this.types = "PrintStmt";
        }

        @Override
        public String toString() {
            return "PrintStmt{" +
                    "expression=" + expression +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }


    public String types = "Stmt";
}
