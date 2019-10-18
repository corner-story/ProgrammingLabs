package com.compiler.tree;
import java.util.*;


public class ProgramNode extends ASTNode {
    public List<Stmt> stmt;

    public ProgramNode(){
        this.type = "program";
        stmt = new ArrayList<>();
    }
}
