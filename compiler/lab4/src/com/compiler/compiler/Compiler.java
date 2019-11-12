package com.compiler.compiler;

import com.compiler.ast.*;
import com.compiler.lex.Lex;
import com.compiler.lex.Token;
import com.compiler.parser.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*

    多个frame共用一个stack, 但有各自的context
    context构成简单的environments链, 从而构成简单的作用域!

*/
public class Compiler {

    private String source;
    private List<ByteCode> bytecodes = new ArrayList<>();
    private Stack<Frame> frames = new Stack<>();
    private HashMap<String, Frame> global;

    public Compiler(String source){
        this.source = source;
        this.global = new HashMap<>();
    }

    public static List<Token> Tokenize(String source){
        return new Lex(source).tokenize();
    }

    public static List<Stmt> Parse(String source){
        return new Parser(source).parse();
    }

    public static List<ByteCode> ByteCodes(String source){
        List<Stmt> stmts = Parse(source);
        ByteCodeGen byteCodeGen = new ByteCodeGen();
        for (Stmt stmt : stmts) {
            stmt.accept(byteCodeGen);
        }
        return byteCodeGen.getByteCodes();
    }

    public static void RunVM(String source){
        new Compiler(source).run();
    }


    public void run(){
        try{
            init();
            /*
                设置相应的上下文 context
            */

            while(!frames.empty()){

                Frame frame = frames.get(frames.size()-1);

                runBytecodeByFrame(frame);
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    private void init() throws Exception{
        List<Stmt> stmts = Compiler.Parse(this.source);

        for (Stmt stmt : stmts) {
            if(!stmt.types.equals("FuncStmt")){
                throw new Exception("the source code must be in function!");
            }
            Stmt.FuncStmt curr = (Stmt.FuncStmt)stmt;
            ByteCodeGen byteCodeGen = new ByteCodeGen();
            curr.accept(byteCodeGen);

            this.global.put(curr.funcname, new Frame(curr.funcname, byteCodeGen.getByteCodes()));
//            System.out.println(stmt);
        }

        if(this.global.getOrDefault("main", null) == null){
            throw new Exception("the source code don't have function 'main'!");
        }
        this.frames.push(this.global.get("main"));
    }


    private void runBytecodeByFrame(Frame frame) throws Exception{

        Stack<Value> stack = frame.stack;
        List<ByteCode> bytecodes = frame.bytecodes;
        Context context = frame.context;

        for(int pc = frame.pc; pc < bytecodes.size();){
            ByteCode bytecode = bytecodes.get(pc);
            List<String> args = bytecode.getArgs();

            switch (bytecode.getBytecode()){

                case "LOAD_CONST":
                    Value value = null;
                    if(args.get(1).equals("LITERAL_INT")){
                        value = new Value<Integer>(Value.INT, Integer.valueOf(args.get(0)));
                    }else if (args.get(1).equals("LITERAL_REAL")){
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
                    context.addValue(args.get(0), stack.pop());
                    pc++;
                    break;
                case "LOAD_FAST":
                    stack.push(context.getValue(args.get(0)));
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

                    Value right4 = stack.pop();
                    Value left4 = stack.pop();
                    double lv = Double.valueOf(left4.value.toString());
                    double rv = Double.valueOf(right4.value.toString());
                    boolean res = true;
                    switch (op){
                        case "<": res = lv < rv; break;
                        case "<=": res = lv <= rv; break;
                        case ">": res = lv > rv; break;
                        case ">=": res = lv >= rv; break;
                        case "=": res = lv == rv; break;

                        default:
                            res = false;
                    }
                    stack.push(new Value<Boolean>(Value.BOOL, res));
                    pc++;
                    break;

                case "POP_JUMP_IF_FALSE":
                    Value topValue = stack.pop();
                    if(Boolean.valueOf(topValue.value.toString())){
                        pc++;
                    }else{
                        pc = Integer.valueOf(args.get(0));
                    }
                    break;

                case "POP_JUMP_IF_TRUE":
                    topValue = stack.pop();
                    if(Boolean.valueOf(topValue.value.toString())){
                        pc = Integer.valueOf(args.get(0));
                    }else{
                        pc++;
                    }
                    break;

                case "JUMP_FORWARD":
                    pc = Integer.valueOf(args.get(0));
                    break;

                case "CALL_FUNCTION":
                    String funcname = args.get(0);
                    if(funcname.equals("printf")){
                        System.out.print(stack.get(stack.size()-1).value);
                        System.out.println();
                    }else if(funcname.equals("main")){
                        throw new Exception("you denfied to call function 'main'");
                    } else{
                        //创建新的frame
                        Frame nextframe = this.global.getOrDefault(funcname, null);
                        if(nextframe == null){
                            throw new Exception("I can't find the '" + funcname + "' function!");
                        }
                        //设置新栈, 参数转移
                        //参数个数
//                        int argsize = Integer.valueOf(args.get(1));
//                        Stack<Value> newstack = new Stack<>();
//                        while (argsize-- > 0){
//                            newstack.push(stack.pop());
//                        }

                        nextframe.init();
                        //设置栈
                        nextframe.stack = stack;
                        //设置context链
                        nextframe.context.setPrev(context);

                        this.frames.push(nextframe);
                        runBytecodeByFrame(nextframe);

                    }
                    pc++;
                    break;

                case "SETUP_LOOP":
                    pc++;
                    break;
                case "JUMP_ABSOLUTE":
                    pc = Integer.valueOf(args.get(0));
                    break;

                case "POP_BLOCK":
                    //循环运行完毕
                    pc++;
                    break;
                case "POP_TOP":
                    stack.pop();
                    pc++;
                    break;
                case "BREAK_LOOP":
                    pc = Integer.valueOf(args.get(0));
                    break;

                default:
                    throw new Exception("unknown bytecode '"+bytecode.getBytecode()+"'");
            }


        }


        this.frames.pop();
    }


    public static void main(String[] args) {


        File f = new File(".\\test\\testcase0");
        String path = "";
        try{
            path = f.getCanonicalPath();
            String source = new InputFile(path).read();

            Compiler.RunVM(source);

//            System.out.println("\n***************Tokens************");
//            for (Token token : Compiler.Tokenize(source)) {
//                System.out.println(token);
//            }
//
//            System.out.println("\n****************Ast**************");
//            for (Stmt stmt : Compiler.Parse(source)) {
//                System.out.println(stmt);
//            }
//
//            System.out.println("\n****************bytecode************");
//            for (ByteCode code : Compiler.ByteCodes(source)) {
//                System.out.println(code.getBytecode() + "\t\t" + code.getArgs());
//            }
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

