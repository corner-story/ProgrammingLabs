package com.compiler.interpreter;

import com.compiler.ast.*;
import com.compiler.lex.Lex;
import com.compiler.lex.Token;
import com.compiler.parser.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Interpreter {

    private String source;
    private List<ByteCode> bytecodes = new ArrayList<>();
    private Stack<Frame> frames = new Stack<>();


    public Interpreter(String source){
        this.source = source;
    }

    public List<Token> lex(String source){
        return new Lex(source).tokenize();
    }

    public List<Stmt> parse(String source){
        return new Parser(source).parse();
    }


    public void run(){
        try{
            init();
            /*
                设置相应的上下文 context
            */
            while(!frames.empty()){

                Frame frame = frames.pop();
                frame.init();

                Stack<Value> stack = frame.stack;
                List<ByteCode> bytecodes = frame.bytecodes;
                Map<String, Value> context = frame.context;

                for(int pc = frame.pc; pc < bytecodes.size();){
                    ByteCode bytecode = bytecodes.get(pc);
                    List<String> args = bytecode.getArgs();

                    switch (bytecode.getBytecode()){

                        case "LOAD_CONST":
                            Value value = null;
                            if(args.get(1).equals("LITERAL_INT")){
                                value = new Value<Integer>(Value.INT, Integer.valueOf(args.get(0)));
                            }else if (args.get(1).equals("LITERAL_DOUBLE")){
                                value = new Value<Double>(Value.DOUBLE, Double.valueOf(args.get(0)));
                            }else if(args.get(1).equals("LITERAL_STRING")){
                                value = new Value<String>(Value.STRING, args.get(0));
                            }else if(args.get(1).equals("LITERAL_CHAR")){
                                value = new Value<String>(Value.CHAR, args.get(0));
                            }
                            stack.push(value);
                            pc++;
                            break;
                        case "STORE_FAST":
                            context.put(args.get(0), stack.pop());
                            pc++;
                            break;
                        case "LOAD_FAST":
                            stack.push(context.get(args.get(0)));
                            pc++;
                            break;
                        case "BINARY_ADD":
                            Value right = stack.pop();
                            Value left = stack.pop();

                            if((left.type == Value.INT || left.type == Value.DOUBLE) && (right.type == Value.INT || right.type == Value.DOUBLE)){
                                stack.push(new Value<Double>(Value.DOUBLE, Double.valueOf(left.value.toString()) + Double.valueOf(right.value.toString())));
                            }else if(left.type == Value.STRING && right.type == Value.STRING){
                                stack.push(new Value<String>(Value.STRING, left.value.toString() + right.value.toString()));
                            }
                            pc++;
                            break;

                        case "BINARY_SUB":
                            Value right1 = stack.pop();
                            Value left1 = stack.pop();

                            if((left1.type == Value.INT || left1.type == Value.DOUBLE) && (right1.type == Value.INT || right1.type == Value.DOUBLE)) {
                                stack.push(new Value<Double>(Value.DOUBLE, Double.valueOf(left1.value.toString()) - Double.valueOf(right1.value.toString())));
                            }
                            pc++;
                            break;

                        case "BINARY_MUL":
                            Value right2 = stack.pop();
                            Value left2 = stack.pop();

                            if((left2.type == Value.INT || left2.type == Value.DOUBLE) && (right2.type == Value.INT || right2.type == Value.DOUBLE)) {
                                stack.push(new Value<Double>(Value.DOUBLE, Double.valueOf(left2.value.toString()) * Double.valueOf(right2.value.toString())));
                            }
                            pc++;
                            break;
                        case "BINARY_DIV":
                            Value right3 = stack.pop();
                            Value left3 = stack.pop();

                            if((left3.type == Value.INT || left3.type == Value.DOUBLE) && (right3.type == Value.INT || right3.type == Value.DOUBLE)) {
                                stack.push(new Value<Double>(Value.DOUBLE, Double.valueOf(left3.value.toString()) / Double.valueOf(right3.value.toString())));
                            }
                            pc++;
                            break;

                        case "COMPARE_OP":
                            String op = args.get(0);
                            if(op.equals("!")){
                                stack.push(new Value<Boolean>(Value.BOOL, Boolean.valueOf(stack.pop().value.toString())));
                                pc++;
                                break;
                            }

                            Value right4 = stack.pop();
                            Value left4 = stack.pop();
                            boolean res = true;
                            switch (op){
                            }
                            pc++;
                            break;
                        case "CALL_FUNCTION":
                            if(args.get(0).equals("printf")){
                                System.out.print(stack.get(stack.size()-1).value);
                            }
                            pc++;
                            break;

                        case "POP_TOP":
                            stack.pop();
                            pc++;
                            break;

                        default:
                            throw new Exception("error!");
                    }


                }
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    private void init(){
        List<Stmt> stmts = parse(source);
        ByteCodeGen byteCodeGen = new ByteCodeGen();
        for (Stmt stmt : stmts) {
            stmt.accept(byteCodeGen);
        }

//        for (int i = 0; i < byteCodeGen.getByteCodes().size(); i++) {
//            System.out.println(i + "\t" + byteCodeGen.getByteCodes().get(i).getBytecode() + "\t\t\t" + String.join(", ", byteCodeGen.getByteCodes().get(i).getArgs()));
//        }
//        System.out.println("\n");

        Frame main = new Frame(byteCodeGen.getByteCodes());
        this.frames.push(main);
    }


    public static void main(String[] args) {
        File f = new File(".\\test\\testcase0");
        String path = "";
        try{
            path = f.getCanonicalPath();
            System.out.println(Boolean.valueOf("false"));

            Interpreter simpleInterpreter = new Interpreter(new InputFile(path).read());
            simpleInterpreter.run();
        }
        catch(Exception e){
            System.out.println(e);
        }


    }


}



class InputFile {
    private String filepath;

    public InputFile(String filepath) {
        this.filepath = filepath;
    }

    public String read(){
        StringBuffer scanned = new StringBuffer();
        try (BufferedReader fuck = new BufferedReader(new FileReader(filepath, StandardCharsets.UTF_8))){

            String line = "";
            while((line = fuck.readLine()) != null){
                scanned.append(line + "\n");
                //System.out.println(line);
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return scanned.toString();
    }
}

