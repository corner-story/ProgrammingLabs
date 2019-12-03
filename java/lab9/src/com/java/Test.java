package com.java;


import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws Exception{

        new Op().accept(new testVisit());
    }
}




abstract class AST{

    public final void accept(Object visitor) throws Exception{
        Class<?> visitClass = visitor.getClass();
        Method visit = getVisitMethod(visitClass, this.getClass());

        if(visit != null){
            visit.invoke(visitor, this);
        }
    }

    public final Method getVisitMethod(Class<?> visitClass, Class<?> args){
        try{
            return visitClass.getMethod("visit", args);
        }catch (NoSuchMethodException e){
            return getVisitMethod(visitClass, args.getSuperclass());
        }

    }

    @Override
    public String toString() {
        return "AST";
    }
}


class Literal extends AST{

    @Override
    public String toString() {
        return "literal";
    }
}

class Op extends AST{
    @Override
    public String toString() {
        return "OP";
    }
}

class testVisit{

    public void visit(AST node) {
        System.out.println("test AST!");
    }

    public void visit(Literal node) {
        System.out.println("test Literal!");
    }
}


