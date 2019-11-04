package com.compiler.ast;

import com.compiler.interpreter.ByteCode;

import java.util.ArrayList;
import java.util.List;

public class ByteCodeGen implements Visitor<Void> {

    private List<ByteCode> byteCodes;

    public ByteCodeGen(){
        this.byteCodes = new ArrayList<>();
    }

    @Override
    public Void visit(Expr.Literal node) {
        byteCodes.add(new ByteCode("LOAD_CONST", node.value));
        return null;
    }

    @Override
    public Void visit(Expr.OpBinary node) {
        String bycode = "BINARY_ADD";
        switch (node.op){
            case "-": bycode = "BINARY_SUB"; break;
            case "*": bycode = "BINARY_MUL"; break;
            case "/": bycode = "BINARY_DIV"; break;
        }
        node.left.accept(this);
        node.right.accept(this);

        byteCodes.add(new ByteCode(bycode));
        return null;
    }

    @Override
    public Void visit(Expr.Identify node) {
        byteCodes.add(new ByteCode("LOAD_FAST", node.name));
        return null;
    }

    @Override
    public Void visit(Expr.Logical node) {
        node.left.accept(this);
        node.right.accept(this);

        byteCodes.add(new ByteCode("COMPARE_OP", node.op));
        return null;
    }

    @Override
    public Void visit(Stmt.DefStmt node) {
        node.expression.accept(this);
        byteCodes.add(new ByteCode("STORE_FAST", node.identify));
        return null;
    }

    @Override
    public Void visit(Stmt.PrintStmt node) {
        node.expression.accept(this);
        byteCodes.add(new ByteCode("CALL_FUNCTION", "printf"));
        byteCodes.add(new ByteCode("POP_TOP"));
        return null;
    }

    @Override
    public Void visit(Stmt.IfStmt node) {
        node.condition.accept(this);
        byteCodes.add(new ByteCode("POP_JUMP_IF_FALSE"));
        int conditionID = byteCodes.size() - 1;

        for (Stmt stmt : node.consequence) {
            stmt.accept(this);
        }
        byteCodes.add(new ByteCode("JUMP_FORWARD"));
        int elseID = byteCodes.size();
        for (Stmt stmt : node.alternative) {
            stmt.accept(this);
        }
        int endID = byteCodes.size();
        byteCodes.get(conditionID).getArgs().add(String.valueOf(elseID));
        byteCodes.get(elseID-1).getArgs().add(String.valueOf(endID));
        return null;
    }

    public List<ByteCode> getByteCodes() {
        return byteCodes;
    }
}
