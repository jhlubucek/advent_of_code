package org.example;


import org.example.days.*;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Day day = new Day10();
        day.runTask();
        long finish = System.currentTimeMillis();
        System.out.println("run time: " + (finish - start) + "ms");
    }


}
