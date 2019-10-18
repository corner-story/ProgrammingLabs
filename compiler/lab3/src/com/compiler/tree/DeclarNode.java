package com.compiler.tree;

public class DeclarNode extends ASTNode {
    public ASTNode identifier;
    public ASTNode literal;

    public DeclarNode(ASTNode identifier, ASTNode literal){
        this.type = "declaraton";
        this.identifier = identifier;
        this.literal = literal;
    }

}
