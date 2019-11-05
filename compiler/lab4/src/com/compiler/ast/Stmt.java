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


    public static class WhileStmt extends Stmt{
        public Expr condition;
        public List<Stmt> body;

        public WhileStmt(Expr condition, List<Stmt> body){
            this.condition = condition;
            this.body = body;
            this.types = "WhileStmt";
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "WhileStmt{" +
                    "condition=" + condition +
                    ", body=" + body +
                    '}';
        }
    }


    public static class ForStmt extends Stmt{
        public Stmt init;
        public Expr condition;
        public Stmt alter;
        public List<Stmt> body;

        public ForStmt(Stmt init, Expr condition, Stmt alter, List<Stmt> body) {
            this.init = init;
            this.condition = condition;
            this.alter = alter;
            this.body = body;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "ForStmt{" +
                    "init=" + init +
                    ", condition=" + condition +
                    ", alter=" + alter +
                    ", body=" + body +
                    '}';
        }
    }

    public String types = "Stmt";
}
