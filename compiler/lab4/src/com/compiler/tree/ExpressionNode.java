package com.compiler.tree;

public class ExpressionNode extends ASTNode {
    public String type = "BinaryExpression";
    public String op;

    public ExpressionNode(String op, ASTNode left, ASTNode right){
        this.type = "BinaryOpExpr";
        this.op = op;
        this.left = left;
        this.right = right;
    }
}
