package com.compiler.ast;

import java.util.ArrayList;
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

    public static class BreakOrContinueStmt extends Stmt{
        public String name;
        public BreakOrContinueStmt(String name){
            this.name = name;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "BreakOrContinueStmt{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }


    //函数定义和调用
    public static class FuncStmt extends Stmt{
        public String returntype;
        public String funcname;
        public List<Stmt> args;
        public List<Stmt> body;



        public FuncStmt(String returntype, String funcname, List<Stmt> body) {
            this.returntype = returntype;
            this.funcname = funcname;
            this.body = body;
            this.args = new ArrayList<>();
            this.types = "FuncStmt";
        }

        public FuncStmt(String returntype, String funcname, List<Stmt> args, List<Stmt> body) {
            this.returntype = returntype;
            this.funcname = funcname;
            this.args = args;
            this.body = body;
            this.types = "FuncStmt";
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "FuncStmt{" +
                    "returntype='" + returntype + '\'' +
                    ", funcname='" + funcname + '\'' +
                    ", args=" + args +
                    ", body=" + body +
                    '}';
        }
    }

    public static class CallStmt extends Stmt{
        public String funcname;
        public List<Expr> args;

        public CallStmt(String funcname, List<Expr> args) {
            this.funcname = funcname;
            this.args = args;
            this.types = "CallStmt";
        }

        public CallStmt(String funcname) {
            this.funcname = funcname;
            this.args = new ArrayList<>();
            this.types = "CallStmt";
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "CallStmt{" +
                    "funcname='" + funcname + '\'' +
                    ", args=" + args +
                    '}';
        }
    }


    public static class ReturnStmt extends Stmt{
        public Expr expression;

        public ReturnStmt(Expr expression) {
            this.expression = expression;
            this.types = "ReturnStmt";
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "ReturnStmt{" +
                    "expression=" + expression +
                    '}';
        }
    }

    public String types = "Stmt";
}
