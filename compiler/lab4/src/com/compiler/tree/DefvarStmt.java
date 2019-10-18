package com.compiler.tree;
import java.util.*;

public class DefvarStmt extends Stmt {

    public String kind;    // int | double | string | char
    public int kindid;
    public List<ASTNode> declarations;

    public DefvarStmt(String kind){
        this.kind = kind;
        this.type = "defvarstmt";
        declarations = new ArrayList<>();
    }

    public static void main(String[] args) {

    }
}
