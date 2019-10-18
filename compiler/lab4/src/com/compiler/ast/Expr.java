package com.compiler.ast;



public abstract class Expr implements Node {
    public static class Literal extends Expr{
        public String kind;
        public String value;
        public Literal(String value, String kind){
            this.value = value;
            this.kind = kind;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }


    public static class OpBinary extends Expr{
//        public final static int ADD = 0;
//        public final static int SUB = 1;
//        public final static int MUL = 2;
//        public final static int DIV = 3;

        public String op;
        public Expr left;
        public Expr right;

        public OpBinary(String op, Expr left, Expr right){
            this.op = op;
            this.left = left;
            this.right = right;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }


//    @Override
//    public void accept(Visitor visitor) {
//        visitor.visit(this);
//    }
}

//class Literal extends Expr {
//    @Override
//    public void accept(Visitor visitor){
//        visitor.visit(this);
//    }
//}
//
//class VisitExpr implements Visitor{
//
//    @Override
//    public void visit(Node node) {
//        System.out.println("visit Node!");
//    }
//
//    public void visit(Expr expr) {
//        System.out.println("visit Expr!");
//    }
//
//    public void visit(Literal literal) {
//        System.out.println("visit literal!");
//    }
//}
//
//class Test{
//    public static void main(String[] args) {
//        VisitExpr vs = new VisitExpr();
//        Node[] nodes = {new Expr(), new Literal(), new Expr()};
//        for (int i = 0; i < nodes.length; i++) {
//            nodes[i].accept(vs);
//        }
//
//        System.out.println("****");
//        Node t = new Literal();
//        t.accept(vs);
//    }
//}
