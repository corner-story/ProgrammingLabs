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
                                value = new Value.IntObject(Integer.valueOf(args.get(0)));
                            }else if(args.get(1).equals("LITERAL_STRING")){
                                value = new Value.StringObject(args.get(0));
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
                            Value left = stack.pop();
                            Value right = stack.pop();

                            if(left.type.equals("int")){
                                stack.push(new Value.IntObject(Integer.valueOf(((Value.IntObject)left).value + ((Value.IntObject)right).value)));
                            }
                            pc++;
                            break;

                        case "CALL_FUNCTION":
                            if(args.get(0).equals("printf")){
                                System.out.print(stack.get(stack.size()-1));
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

