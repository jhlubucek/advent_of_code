package org.example;


import org.example.days.Day;
import org.example.days.Day1;
import org.example.days.Day2;
import org.example.days.Day3;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Day day = new Day3();
        day.runTask();
        long finish = System.currentTimeMillis();
        System.out.println("run time: " + (finish - start) + "ms");
    }


}
