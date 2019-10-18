package com.compiler.lex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Lex {
    private String input;
    private char cur; // 当前字符
    private int index = 0;
    private int row = 1;  //默认为第一行第一列
    private int clo = 0;
    private boolean isfinished = false;
    private List<Token> tokens = new ArrayList<>();

    public Lex(String filepath){
        this.input = new InputFile(filepath).read();
    }

    //更新当前char, 并前进一个单位
    public void advance(){
        if(index < input.length()){
            checkline();
            cur = input.charAt(index);

            index += 1;
            isfinished = false;
        }else{
            isfinished = true;
        }
    }

    //查看下一个字符, 不改变当前字符
    public char looknext(){
        int next = index + 1;
        if(next >= input.length()){
            isfinished = true;
            return cur;
        }
        return input.charAt(next);
    }

    //计算行, 列信息
    //在 advance() 中, 每次程序请求下一个字符时更新行列值
    //row,clo的值一直是 cur 的当前位置
    public void checkline(){
        switch (cur){
            case ' ': clo += 1; break;
            case '\t': clo += 4; break;
            case '\n': row += 1; clo = 1; break;

            default:
                clo += 1;
        }
    }


    public List<Token> tokenize(){

        try{
            advance();
            //只要没有到输入字符串的结尾就一直tokenize
            while(!isfinished){
                if(cur == ' ' || cur == '\t' || cur == '\r' || cur == '\n'){

                    advance();
                    continue;
                }
                if(Character.isAlphabetic(cur)){
                    tokenAlpha();
                    continue;

                }else if(Character.isDigit(cur) || cur == '.'){
                    //识别并计算整数或者小数
                    tokenDigit();
                    continue;

                }else{
                    tokenShit();
                }
            }


        }catch (Exception e){
            System.out.println(e);
        }
        return tokens;
    }

    //识别标识符和关键字
    public void tokenAlpha(){
        //当前字符是字母, 接着识别字母, 数字, '_'(这样的话会识别所有的标识符和保留字)
        String token = "";
        while (Character.isAlphabetic(cur) || Character.isDigit(cur) || cur == '_'){
            token += String.valueOf(cur);
            advance();
        }
        //查看当前token是否为保留字
        if(Table.lookUp(token)){
            tokens.add(new Token(token.toUpperCase(), token, row, clo-token.length(), clo));
        }else{
            tokens.add(new Token("ID", token, row, clo-token.length(), clo));
        }
    }


    //识别整数或者小数
    public void tokenDigit() throws Exception{
        //识别并计算整数或者小数
        int res = 0;
        int offest = 0;
        int point = 0;  //记录匹配的小数点数量 正确 0.11 .12  错误 1.2.3
        int type = Table.INT;  //记录匹配到的数值类型=> 整数, 小数, 八进制, 十六进制
        int flag = 1;
        int fuckup = 0;
        Deque<Character> stack = new LinkedList<>();

        int state = 0;

        int beginclo = clo, endclo = clo;

        while(!isfinished && state != -1){
            switch(state){
                case 0:
                    if(cur == '.'){
                        point+=1;
                        if(point > 2){ throw new Exception("point error at"  + row + " line " + clo + " column !");}
                        type = Table.REAL;advance(); state=1; break;}
                    if(cur == '0'){ advance(); state=2; break;}
                    if(cur >= '1' && cur <= '9'){
                        res=res*10 + Integer.valueOf(String.valueOf(cur));
                        advance();state=3; break;}

                    throw new Exception("unknown token at " + row + " line " + clo + " column !");
                case 1:
                    if(cur >= '0' && cur <= '9'){
                        if(type == Table.REAL){
                            offest -= 1;
                        }
                        res = res*10 + Integer.valueOf(String.valueOf(cur));
                        state = 1; advance();break;
                    }
                    if(cur == 'e' || cur == 'E'){
                        type = Table.REAL; advance(); state = 4;break;
                    }
                    //进入终态
                    state = -1; break;
                case 2:
                    if(cur == '.'){
                        point += 1;
                        if(point > 2){ throw new Exception(" point error at "  + row + " line " + clo + " column !");}
                        type = Table.REAL; state = 1; advance(); break;
                    }
                    if(cur >= '0' && cur <= '7'){
                        //八进制
                        type = Table.OCTAL;
                        state = 5; stack.push(cur); advance(); break;
                    }else if(Character.toLowerCase(cur) == 'x'){
                        //十六进制
                        type = Table.HEX;
                        state = 6; advance(); break;
                    }
                    //进入终态
                    state = -1; break;
                case 3:
                    if(cur >= '0' && cur <= '9'){
                        res = res*10 + Integer.valueOf(String.valueOf(cur));
                        state = 3; advance(); break;
                    }else if(cur == '.'){
                        point += 1;
                        if(point > 2){ throw new Exception("point error at "  + row + " line " + clo + " column !");}
                        type = Table.REAL; state = 1; advance(); break;
                    }else if(cur == 'E' || cur == 'e'){
                        type = Table.REAL; state = 4; advance(); break;
                    }
                    //进入终态
                    state = -1;break;
                case 4:
                    //识别E/e 后面的数值
                    if(cur == '-'){
                        flag = -1; state = 4; advance(); break;
                    }else if(cur == '+'){
                        state = 4; advance(); break;
                    }else if(Character.isDigit(cur)) {
                        fuckup = fuckup * 10 + Integer.valueOf(String.valueOf(cur));
                        state = 4; advance(); break;
                    }
                    //进入终态
                    state = -1; break;
                case 5:
                    //识别八进制数
                    if(cur >= '0' && cur <= '7'){
                        stack.push(cur); state = 5; advance(); break;
                    }
                    //进入终态
                    state = -1; break;
                case 6:
                    //识别十六进制数
                    if((cur >= '0' && cur <= '9') || (cur >= 'a' && cur <= 'f') || (cur >= 'A' && cur <= 'F')){
                        stack.push(cur); state = 6; advance(); break;
                    }
                    //进入终态
                    state = -1; break;

                default:
                    throw new Exception("unknown token: " + cur + " at " + row + " line " + clo + " column !");
            }
        }
        endclo = clo;

        if(isfinished){
            return;
        }

        switch (type){
            case Table.INT:
                tokens.add(new Token("INT", String.valueOf(res), row, beginclo, endclo));break;
            case Table.REAL:
                double result = res * Math.pow(10, offest + flag*fuckup);
                tokens.add(new Token("REAL", String.valueOf(result), row, beginclo, endclo)); break;
            case Table.OCTAL:
                //八进制
                //System.out.println("八进制!");
                int weight = 0;
                int octal = 0;
                while(!stack.isEmpty()){
                    int skr = Integer.valueOf(String.valueOf(stack.pop()));
                    for (int i = 0; i < weight; i++) {
                        skr = skr * 8;
                    }
                    octal = octal + skr;
                    weight += 1;
                }
                tokens.add(new Token("INT", String.valueOf(octal), row, beginclo, endclo)); break;

            case Table.HEX:
                //十六进制
                //System.out.println("十六进制!");
                int weighthex = 0; //十六进制的权重
                int hex = 0;
                int skr = 0;
                Map<Character, Integer> helper = new HashMap<>(); helper.put('a', 10); helper.put('b', 11);
                helper.put('c', 12); helper.put('d', 13); helper.put('e', 14); helper.put('f', 15);

                while(!stack.isEmpty()){
                    Character c = stack.pop();
                    skr = Character.isDigit(c) ? Integer.valueOf(String.valueOf(c)) : helper.get(Character.toLowerCase(c));
                    for (int i = 0; i < weighthex; i++) {
                        skr = skr * 16;
                    }
                    hex = hex + skr;
                    weighthex += 1;
                }
                tokens.add(new Token("INT", String.valueOf(hex), row, beginclo, endclo));
        }

        return;





        /*
        boolean isDouble = false;
        while(Character.isDigit(cur) || cur == '.' || cur == 'E' || cur == 'e'){

            if(Character.isDigit(cur)){
                res = res * 10 + Integer.valueOf(String.valueOf(cur));
                if(isDouble){
                    offest -= 1;
                }
                advance();
            }else if(cur == '.'){
                point += 1;
                if(point > 1){
                    //throw Exception
                    throw new Exception("小数点错误");
                }
                isDouble = true;
                advance();
            }else if(cur == 'E' || cur == 'e'){
                advance();
                int flag;
                int funckE = 0;
                if(cur == '-'){
                    flag = -1;
                    advance();
                }else if(cur == '+'){
                    flag = 1;
                    advance();
                }else{
                    flag = 1;
                }

                while(Character.isDigit(cur)){
                    funckE = funckE * 10 + Integer.valueOf(String.valueOf(cur));
                    advance();
                }
                funckE = flag * funckE;
                offest += funckE;
                break;
            }
        }

        //解析为整数
        if(offest == 0 && !isDouble){
            tokens.add(new Token("INT", String.valueOf(res)));
            return;
        }
        //解析为小数
        //System.out.println(res+" "+offest);
        double shit = res * Math.pow(10, offest);
        tokens.add(new Token("REAL", String.valueOf(shit)));
        */
    }



    //识别一坨不知道怎么叫的东西
    public void tokenShit() throws Exception{
        String res = "";
        switch (cur){
            case '<':
                advance();
                if(cur == '='){
                    tokens.add(new Token("LE", "<=", row, clo-1, clo+1));advance();
                }else if (cur == '>'){
                    tokens.add(new Token("NE", "<>", row, clo-1, clo+1));advance();
                }else{
                    tokens.add(new Token("LT", "<", row, clo-1, clo));
                }
                return;
            case '=': tokens.add(new Token("EQ","=", row, clo, clo+1));advance();return;
            case '>':
                advance();
                if(cur == '='){
                    tokens.add(new Token("GE",">=", row, clo-1, clo+1));advance();
                }else{
                    tokens.add(new Token("GT",">", row, clo-1, clo));
                }
                return;
            case ':':
                advance();
                if(cur != '='){
                    //throw a fucking exception
                    throw new Exception("except a '=' after ':' at " + row + " line " + clo + " column !");

                }
                tokens.add(new Token("IS",":=", row, clo-1, clo+1));advance();
                return;

            case '+': tokens.add(new Token("PL", "+", row, clo, clo+1));advance();return;
            case '-': tokens.add(new Token("MI","-", row, clo, clo+1));advance();return;
            case '*': tokens.add(new Token("MU","*", row, clo, clo+1));advance();return;
            case '/':
                //识别注释  => 单行注释://   多行注释/**/, 可识别嵌套注释
                advance();
                if(cur != '/' && cur != '*'){
                    tokens.add(new Token("DI", "/", row, clo-1, clo));return;
                }
                if(cur == '/'){
                    //单行注释, 直接解析到下一行
                    advance();
                    while(!isfinished && cur != '\n'){ advance();}
                    return;
                }
                //多行注释
                Deque<String> fucks = new LinkedList<>();
                fucks.push("/*");
                char next;
                int line = row, column = clo + 1;
                while(!isfinished && !fucks.isEmpty()){
                    advance();
                    if(isfinished){
                        break;
                    }
                    if(cur == '/'){
                        //可能有嵌套注释
                        advance();
                        if(cur == '*'){ fucks.push("/*");}
                    }else if(cur == '*'){
                        //可能是*/
                        advance();
                        if(cur == '/'){ fucks.pop();}
                    }
                }
                //只要栈中为空, 则代表注释个数匹配成功, 此时cur为最后一个注释的/
                if(fucks.isEmpty()){ advance(); return;}
                throw new Exception("except " + fucks.size() + " \"*/\" at " + row + " line " + clo + " column!");


            case ';': tokens.add(new Token("SE", ";", row, clo, clo+1));advance();return;
            case ',': tokens.add(new Token("CM", ",", row, clo, clo+1));advance();return;

            case '(': tokens.add(new Token("LB", "(", row, clo, clo+1));advance();return;
            case ')': tokens.add(new Token("RB", ")", row, clo, clo+1));advance();return;

            case '{': tokens.add(new Token("LBR", "{", row, clo, clo+1));advance();return;
            case '}': tokens.add(new Token("RBR", "}", row, clo, clo+1));advance();return;



            case '\'':
                //except a char like 'a'
                advance();
                Character c = cur;
                advance();
                if(isfinished || cur != '\''){
                    //throw a exception
                    throw new Exception("except a \"'\"  at " + row + " line !");
                }

                tokens.add(new Token("CHAR", String.valueOf(c), row, clo-2, clo));advance();return;
            case '"':
                //except a string like "aaa"
                advance();
                StringBuffer sb = new StringBuffer();
                while(!isfinished && cur!='"'){
                    sb.append(cur);
                    advance();
                }
                if(isfinished){
                    //throw a exception
                    throw new Exception("except a '\"'  at " + row + " line !");
                }

                tokens.add(new Token("STRING", sb.toString(), row, clo-sb.length()-1, clo));advance();return;


            default:
                //throw throw throw
                throw new Exception("unexcepted token: " + cur + " at " + row + " line " + clo + " column !");
        }
    }




    public static void main(String[] args) {

        //System.out.println(0.1*0.1*789);
        //System.out.println(1.0*26/10/10/1);
        File f = new File(".\\test\\testcase6");
        String path = "";
        try{
            //System.out.println(f.getCanonicalPath());
            path = f.getCanonicalPath();
        }
        catch(Exception e){

        }

        var lex = new Lex(path);
        var tokens = lex.tokenize();
        tokens.forEach(fuck->{
            System.out.println(fuck);
        });
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