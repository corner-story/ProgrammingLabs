package com.java.lab2;



public class HW4 {

    public static void main(String[] args) {
        Shape[] sp = {new Rectangle(1,2), new Circle(2), new Triangle(2)};

        for (int i = 0; i < sp.length; i++) {
            System.out.println(sp[i].getClass() + " : " + sp[i].getArea() + ", " + sp[i].getPerimeter());
        }
    }
}


interface Shape{
    public abstract double getArea();
    public abstract double getPerimeter();
}


class Coordinates {
    public long x;
    public long y;

    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

class Rectangle extends Coordinates implements Shape{
    private double height;
    private double length;

    public Rectangle(double length, double height){
        super(0, 0);
        this.length = length;
        this.height = height;
    }

    public double getArea(){
        return height*length;
    }

    public double getPerimeter(){
        return 2 * (length + height);
    }
}


class Circle extends Coordinates implements Shape{
    private double r;

    public Circle(double r){
        super(0, 0);
        this.r = r;
    }

    public double getArea(){
        return Math.PI*r*r;
    }

    public double getPerimeter(){
        return 2 * r * Math.PI;
    }
}



class Triangle extends Coordinates implements Shape{
    private double r;

    public Triangle(double r){
        super(0, 0);
        this.r = r;
    }

    public double getArea(){
        return Math.sqrt(3) * r * r / 4;
    }

    public double getPerimeter(){
        return 3 * r;
    }
}
