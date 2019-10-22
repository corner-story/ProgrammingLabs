package com.compiler.ast;

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
        public String types = "Identify";
        public Identify(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return "Identify{" +
                    "name='" + name + '\'' +
                    ", types='" + types + '\'' +
                    '}';
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }

    }


    public String types = "Expr";
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
