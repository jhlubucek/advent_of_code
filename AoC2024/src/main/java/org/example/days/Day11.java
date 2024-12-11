package org.example.days;

import org.example.common.Helpers;
import org.example.common.TimeKeeper;
import org.example.dataReaders.StringDataReader;

import java.util.*;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day11 extends Day{
    private Map<Long,List<Long>> splits = new HashMap<>();
    private Map<Long,Integer> nonSplits = new HashMap<>();
    private static int iter = 25;
    @Override
    public void task1() {
        String input = StringDataReader.getString("day11", INPUT,false);
        List<Long> stones = Helpers.stringToLongList(input, "\\s+");
        for (int i = 0; i < iter; i++) {
            applyRules(stones);
        }
        System.out.println(stones.size());
    }

        public static void applyRules(List<Long> stones) {
            List<Long> newStones = stones.stream()
                    .flatMap(stone -> applyRules(stone).stream())
                    .toList();

            stones.clear();
            stones.addAll(newStones);
        }

    public static List<Long> applyRules(long stone) {
        if (stone == 0) {
            return List.of(1L);
        }else if (hasEvenDigits(stone)) {
            String str = Long.toString(stone);
            long left = Integer.parseInt(str.substring(0,str.length()/2));
            long right = Integer.parseInt(str.substring(str.length()/2));
            return Arrays.asList(left,right);
        }else {
            return List.of(stone * 2024);
        }
    }

    public static boolean hasEvenDigits(long number) {
        number = Math.abs(number);
        long digitCount = Long.toString(number).length();
        return digitCount % 2 == 0;
    }

    @Override
    public void task2() {
        String input = StringDataReader.getString("day11", INPUT,false);
        List<Long> stones = Helpers.stringToLongList(input, "\\s+");
        //to memory intensive
//        for (int i = 0; i < 75; i++) {
//            applyRules(stones);
//        }
//        System.out.println(stones.size());

        long size = 0;
        for (Long stone : stones) {
            size += countFinalStones(stone,75,true);
            System.out.println("stone " + stone + " size: " + size);
            TimeKeeper.printTimePassed();
        }
        System.out.println(size);
    }


    public long countFinalStones(long stone, int iterationsLeft, boolean isNew) {
        long numberOfStones = isNew ? 1 : 0;
        List<Long> newStones = new ArrayList<>();
        List<Long> newStonesIterations = new ArrayList<>();
        List<Long> evolvedStones = new ArrayList<>();
        int iterations = 0;

        while (iterationsLeft > 0) {
            if (shouldReturnFromNonSplit(stone, iterationsLeft, evolvedStones, iterations)) {
                return numberOfStones;
            }

            if (splits.containsKey(stone)) {
                numberOfStones = handlePrecalculatedSplit(stone, iterationsLeft, evolvedStones, numberOfStones, newStones, newStonesIterations, iterations);
                break;
            }

            iterations++;
            iterationsLeft--;
            evolvedStones.add(stone);

            if (stone == 0) {
                stone = 1;
            } else if (hasEvenDigits(stone)) {
                numberOfStones = handleEvenDigitSplit(stone, iterationsLeft, evolvedStones, numberOfStones, newStones, newStonesIterations, iterations);
                break;
            } else {
                stone *= 2024;
            }
        }

        addTerminalNumbers(evolvedStones, iterations);
        for (int i = 0; i < newStones.size(); i++) {
            numberOfStones += countFinalStones(newStones.get(i), newStonesIterations.get(i).intValue(), true);
        }

        return numberOfStones;
    }

    private boolean shouldReturnFromNonSplit(long stone, int iterationsLeft, List<Long> evolvedStones, int iterations) {
        if (nonSplits.containsKey(stone) && nonSplits.get(stone) > iterationsLeft) {
            iterations += nonSplits.get(stone);
            addTerminalNumbers(evolvedStones, iterations);
            return true;
        }
        return false;
    }

    private long handlePrecalculatedSplit(long stone, int iterationsLeft, List<Long> evolvedStones, long numberOfStones, List<Long> newStones, List<Long> newStonesIterations, int iterations) {
        iterationsLeft -= splits.get(stone).get(0);
        iterations += splits.get(stone).get(0);
        if (iterationsLeft < 0) {
            return numberOfStones;
        }
        return executeSplit(splits.get(stone).get(1), splits.get(stone).get(2), iterationsLeft, evolvedStones, numberOfStones, newStones, newStonesIterations, iterations);
    }

    private long handleEvenDigitSplit(long stone, int iterationsLeft, List<Long> evolvedStones, long numberOfStones, List<Long> newStones, List<Long> newStonesIterations, int iterations) {
        String str = Long.toString(stone);
        return executeSplit(Long.parseLong(str.substring(0, str.length() / 2)), Long.parseLong(str.substring(str.length() / 2)), iterationsLeft, evolvedStones, numberOfStones, newStones, newStonesIterations, iterations);
    }

    private long executeSplit(long left, long right, int iterationsLeft, List<Long> evolvedStones, long numberOfStones, List<Long> newStones, List<Long> newStonesIterations, int iterations) {
        addTerminalSplits(evolvedStones, iterations, left, right);
        numberOfStones += countFinalStones(left, iterationsLeft, false);
        newStones.add(right);
        newStonesIterations.add((long)iterationsLeft);
        return numberOfStones;
    }


        public void addTerminalNumbers(List<Long> evolvedStones,int iterations){
        for (int i = 0; i < evolvedStones.size(); i++) {
            if (!nonSplits.containsKey(evolvedStones.get(i)) || nonSplits.get(evolvedStones.get(i)) < iterations-i){
                nonSplits.put(evolvedStones.get(i), iterations-i);
            }
        }
    }
    public void addTerminalSplits(List<Long> evolvedStones,int iterations, long left, long right){
        for (int i = 0; i < evolvedStones.size(); i++) {
            splits.put(evolvedStones.get(i),Arrays.asList((long) iterations-i,left,right));
        }
    }
}
