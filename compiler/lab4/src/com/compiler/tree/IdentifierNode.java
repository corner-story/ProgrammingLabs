package com.compiler.tree;


//标识符node
public class IdentifierNode extends ASTNode {
    public String name;

    public IdentifierNode(String name){
        this.type = "identifier";
        this.name = name;
    }
}
