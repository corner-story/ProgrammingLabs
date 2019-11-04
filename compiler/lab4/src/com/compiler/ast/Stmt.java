package com.compiler.ast;

import java.util.List;

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


    public static class IfStmt extends Stmt{
        public Expr condition;  //条件
        public List<Stmt> consequence;
        public List<Stmt> alternative;

        public IfStmt(Expr condition, List<Stmt> consequence, List<Stmt> alternative){
            this.condition = condition;
            this.consequence = consequence;
            this.alternative = alternative;
            this.types = "IfStmt";
        }

        @Override
        public String toString() {
            return "IfStmt{" + "\n" +
                    "condition = " + condition + "\n" +
                    ",consequence = " + consequence + "\n" +
                    ",alternative = " + alternative + "\n" +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    public String types = "Stmt";
}
