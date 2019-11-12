package com.compiler.ast;

import com.compiler.compiler.ByteCode;
import java.util.ArrayList;
import java.util.List;

public class ByteCodeGen implements Visitor<Void> {

    private List<ByteCode> byteCodes;

    public ByteCodeGen() {
        this.byteCodes = new ArrayList<>();
    }

    @Override
    public Void visit(Expr.Literal node) {
        byteCodes.add(new ByteCode("LOAD_CONST", node.value, node.kind));
        return null;
    }

    @Override
    public Void visit(Expr.OpBinary node) {
        String bycode = "BINARY_ADD";
        switch (node.op) {
            case "-":
                bycode = "BINARY_SUB";
                break;
            case "*":
                bycode = "BINARY_MUL";
                break;
            case "/":
                bycode = "BINARY_DIV";
                break;
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
        if (!node.op.equals("!")) {
            node.left.accept(this);
        }
        node.right.accept(this);
        if (!node.op.equals("!")) {
            byteCodes.add(new ByteCode("COMPARE_OP", node.op));
        }
        return null;
    }

    @Override
    public Void visit(Stmt.DefStmt node) {
        node.expression.accept(this);
        byteCodes.add(new ByteCode("STORE_FAST", node.identify, node.kind));
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
        if (((Expr.Logical) node.condition).op.equals("!")) {
            byteCodes.add(new ByteCode("POP_JUMP_IF_TRUE"));
        } else {
            byteCodes.add(new ByteCode("POP_JUMP_IF_FALSE"));
        }

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
        byteCodes.get(elseID - 1).getArgs().add(String.valueOf(endID));
        return null;
    }

    @Override
    public Void visit(Stmt.WhileStmt node) {
        byteCodes.add(new ByteCode("SETUP_LOOP"));

        int conditionID = byteCodes.size();

        node.condition.accept(this);
        if (((Expr.Logical) node.condition).op.equals("!")) {
            byteCodes.add(new ByteCode("POP_JUMP_IF_TRUE"));
        } else {
            byteCodes.add(new ByteCode("POP_JUMP_IF_FALSE"));
        }

        int decideID = byteCodes.size() - 1;
        for (Stmt stmt : node.body) {
            stmt.accept(this);
        }
        byteCodes.add(new ByteCode("JUMP_ABSOLUTE", String.valueOf(conditionID)));
        byteCodes.add(new ByteCode("POP_BLOCK"));

        int nextID = byteCodes.size();
        byteCodes.get(decideID).getArgs().add(String.valueOf(nextID));

        //check break and continue
        checkBreakAndContinue(decideID, nextID, conditionID);
        return null;
    }

    @Override
    public Void visit(Stmt.ForStmt node) {
        byteCodes.add(new ByteCode("SETUP_LOOP"));
        node.init.accept(this);

        int conditionID = byteCodes.size();
        node.condition.accept(this);
        byteCodes.add(new ByteCode("POP_JUMP_IF_FALSE"));
        int decideID = byteCodes.size() - 1;

        for (Stmt stmt : node.body) {
            stmt.accept(this);
        }

        node.alter.accept(this);

        byteCodes.add(new ByteCode("JUMP_ABSOLUTE", String.valueOf(conditionID)));
        byteCodes.add(new ByteCode("POP_BLOCK"));

        int nextID = byteCodes.size();
        byteCodes.get(decideID).getArgs().add(String.valueOf(nextID));

        //check break and continue
        checkBreakAndContinue(decideID, nextID, conditionID);
        return null;
    }

    @Override
    public Void visit(Stmt.BreakOrContinueStmt node) {

        if(node.name.equals("continue")){
            byteCodes.add(new ByteCode("JUMP_ABSOLUTE", ""));
        }else{
            byteCodes.add(new ByteCode("BREAK_LOOP", ""));
        }

        return null;
    }



    private void checkBreakAndContinue(int decideID, int nextID, int conditionID){
        //检查 break 和 continue
        int i;
        for(i = decideID+1; i != (nextID-1); i++){
            if(byteCodes.get(i).getBytecode().equals("SETUP_LOOP")){
                break;
            }
            if(byteCodes.get(i).getBytecode().equals("JUMP_ABSOLUTE")){
                byteCodes.get(i).getArgs().set(0, String.valueOf(conditionID));
            }else if(byteCodes.get(i).getBytecode().equals("BREAK_LOOP")){
                byteCodes.get(i).getArgs().set(0, String.valueOf(nextID));
            }
        }
        if(i != (nextID - 1)){
            for(i = (nextID-2); i > decideID; i--){
                if(byteCodes.get(i).getBytecode().equals("POP_BLOCK")){
                    break;
                }
                if(byteCodes.get(i).getBytecode().equals("JUMP_ABSOLUTE")){
                    byteCodes.get(i).getArgs().set(0, String.valueOf(conditionID));
                }else if(byteCodes.get(i).getBytecode().equals("BREAK_LOOP")){
                    byteCodes.get(i).getArgs().set(0, String.valueOf(nextID));
                }
            }
        }
    }

    public List<ByteCode> getByteCodes() {
        return byteCodes;
    }
}
