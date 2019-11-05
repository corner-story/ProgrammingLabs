package com.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class AstTransform implements Visitor<String> {
    @Override
    public String visit(Stmt.IfStmt node) {
        return null;
    }

    @Override
    public String visit(Stmt.WhileStmt node) {
        return null;
    }

    @Override
    public String visit(Stmt.ForStmt node) {
        return null;
    }

    @Override
    public String visit(Expr.Logical node) {
        return null;
    }

    public final static String tag = "result-";
    public final static int size = 0;
    public List<IR> irs = new ArrayList<>();
    public class IR{
        public String op;
        public String arg1;
        public String arg2;
        public String res;

        public IR(String op, String arg1, String arg2, String res){
            this.op = op;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.res = res;
        }

        @Override
        public String toString() {
            return "(" + op + ", " + arg1 + ", " + arg2 + ", " + res + ")";
        }
    }

    @Override
    public String visit(Expr.Literal node) {

        return node.value;
    }

    @Override
    public String visit(Stmt.DefStmt node) {
        return null;
    }

    @Override
    public String visit(Stmt.PrintStmt node) {
        return null;
    }

    @Override
    public String visit(Expr.OpBinary node) {
        String left = node.left.accept(this);
        String right = node.right.accept(this);

        String result = tag + (irs.size()+1);
        int num = irs.size();
        String op = node.op;
        String arg1 = left;
        String arg2 = right;
        if(left == null){
            arg1 = tag + num--;
        }
        if(right == null){
            arg2 = tag + num--;
        }

        IR ir = new IR(op, arg1, arg2, result);
        irs.add(ir);
        return null;
    }

    @Override
    public String visit(Expr.Identify node) {
        return node.name;
    }

    public void print(){
        for (int i = 0; i < irs.size(); i++) {
            System.out.println((i+1) +"\t" + irs.get(i));
        }
    }
}
