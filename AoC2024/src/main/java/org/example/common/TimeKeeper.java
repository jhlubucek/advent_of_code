package org.example.common;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.Duration;



public class TimeKeeper {
    private static final TimeKeeper INSTANCE = new TimeKeeper();
    private final long start;

    private TimeKeeper() {
        this.start = System.currentTimeMillis();
    }

    public static TimeKeeper getInstance() {
        return INSTANCE;
    }

    public static void printTimePassed() {
        long finish = System.currentTimeMillis();
        System.out.println("Time passed: " + (finish - INSTANCE.start) + " ms");
    }
}