package com.compiler.tree;

public class LiteralNode extends ASTNode {
    public String kind;  // INT | REAL | STRING | CHAR
    public String value;


    public LiteralNode(String kind){
        this.type = "literal";
        this.kind = kind;
        this.value = "0";
        if(kind.equals("STRING")){
            this.value = "";
        }else if(kind.equals("CHAR")){
            this.value = "\n";
        }
    }

    public LiteralNode(String kind, String value){
        this.type = "literal";
        this.kind = kind;
        this.value = value;
    }


}

