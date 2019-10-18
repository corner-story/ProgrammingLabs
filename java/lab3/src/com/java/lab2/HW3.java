package com.java.lab2;

public class HW3 {
}


class Vehicle{
    public int wheels;
    public double weight;

    public int getWheels() {
        return wheels;
    }

    public double getWeight() {
        return weight;
    }

    public Vehicle(int wheels, double weight){
        this.wheels = wheels;
        this.weight = weight;
    }
}


class Car extends Vehicle{
    public int loader;

    public Car(int wheels, double weight, int loader){
        super(wheels, weight);
        this.loader = loader;
    }

    public int getLoader() {
        return loader;
    }
}


class Truck extends Car{
    public double payload;
    public Truck(int wheels, double weight, int loader, double payload){
        super(wheels, weight, loader);
        this.payload = payload;
    }

    public double getPayload() {
        return payload;
    }
}