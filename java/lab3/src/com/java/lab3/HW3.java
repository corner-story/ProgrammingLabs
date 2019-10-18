package com.java.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Average{
    public abstract double average(List<Double> score);
}

class Caculate1 implements Average{

    @Override
    public double average(List<Double> score) {
         double sum = 0;
         for(double s : score){
             sum += s;
         }
         return score.size()==0? 0: sum/score.size();
    }
}


class Caculate2 implements Average{

    @Override
    public double average(List<Double> score) {
        if(score.size() == 0){
            return 0;
        }
        int maxindex = 0, minindex = 0;
        double sum = 0;
        for (int i = 0; i < score.size(); i++) {
            sum += score.get(i);
            if(score.get(maxindex) < score.get(i)){
                maxindex = i;
            }
            if(score.get(minindex) > score.get(i)){
                minindex = i;
            }
        }
        sum = sum - score.get(maxindex) - score.get(minindex);
        return sum / (score.size()-2);
    }
}



public class HW3 {

    public static void main(String[] args) {
        try{
            Caculate1 c1 = new Caculate1();
            Caculate2 c2 = new Caculate2();

            String input = "";
            List<Double> scores = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);
            while (!input.equals("EOF")){
                if(scanner.hasNextDouble()){
                    input = scanner.nextLine();
                    scores.add(Double.valueOf(input));
                }else{
                    input = scanner.nextLine();
                    if(!input.equals("EOF")){
                        throw new Exception("\"" + input + "\"" + " can't convert to number!");
                    }
                }

            }
            System.out.println("Cacurate1:\t" + c1.average(scores));
            System.out.println("Cacurate2:\t" + c2.average(scores));
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
