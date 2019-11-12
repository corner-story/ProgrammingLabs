package com.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class Expr implements Node {


    public static class Literal extends Expr{
        public String kind;
        public String value;

        public String types = "Literal";

        public Literal(String value, String kind){
            this.value = value;
            this.kind = kind;
        }

        @Override
        public String toString() {
            return "Literal{" +
                    "kind='" + kind + '\'' +
                    ", value='" + value + '\'' +
                    ", types='" + types + '\'' +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }


    public static class OpBinary extends Expr{

        public String op;
        public Expr left;
        public Expr right;

        public String types = "OpBinary";
        public OpBinary(String op, Expr left, Expr right){
            this.op = op;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "OpBinary{" +
                    "op='" + op + '\'' +
                    ", left=" + left +
                    ", right=" + right +
                    ", types='" + types + '\'' +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

    }


    public static class Identify extends Expr{
        public String name;
        public String kind;
        public String types = "Identify";
        public Identify(String name){
            this.name = name;
        }

        public Identify(String kind, String name) {
            this.name = name;
            this.kind = kind;
        }

        @Override
        public String toString() {
            return "Identify{" +
                    "name='" + name + '\'' +
                    ", kind='" + kind + '\'' +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

    }


    public static class Logical extends Expr{
        public Expr left;
        public Expr right;
        public String op;

        public Logical(String op, Expr left, Expr right){
            this.op = op;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Logical{" +
                    "left=" + left +
                    ", right=" + right +
                    ", op='" + op + '\'' +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }


    public static class DoNothing extends Expr{
        public DoNothing(){
            this.types = "DoNothing";
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "DoNothing{}";
        }
    }

    public static class CallExpr extends Expr{
        public String funcname;
        public List<Expr> args;

        public CallExpr(String funcname, List<Expr> args) {
            this.funcname = funcname;
            this.args = args;
            this.types = "CallExpr";
        }

        public CallExpr(String funcname) {
            this.funcname = funcname;
            this.args = new ArrayList<>();
            this.types = "CallExpr";
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

        @Override
        public String toString() {
            return "CallExpr{" +
                    "funcname='" + funcname + '\'' +
                    ", args=" + args +
                    '}';
        }
    }

    public String types = "Expr";
}


