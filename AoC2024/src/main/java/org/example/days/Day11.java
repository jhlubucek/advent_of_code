package org.example.days;

import org.example.common.Helpers;
import org.example.common.Helpers.MutableInt;
import org.example.common.TimeKeeper;
import org.example.dataReaders.StringDataReader;

import java.util.*;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day11 extends Day{
    private Map<Long,List<Long>> splits = new HashMap<>();
    private Map<Long,Integer> nonSplits = new HashMap<>();
    private static final int iter = 25;
    @Override
    public void task1() {
        String input = StringDataReader.getString("day11", TEST,false);
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
//            System.out.println("split: " + stone);
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
        splits.put(0L,Arrays.asList(3L, 20L, 24L));
        splits.put(20L,Arrays.asList(1L, 2L, 0L));
        splits.put(24L,Arrays.asList(1L, 2L, 4L));
        String input = StringDataReader.getString("day11", TEST,false);
        List<Long> stones = Helpers.stringToLongList(input, "\\s+");
        //to memory intensive
//        for (int i = 0; i < 75; i++) {
//            applyRules(stones);
//        }
//        System.out.println(stones.size());

        long size = 0;
        for (Long stone : stones) {
            size += countFinalStones(stone,new MutableInt(iter));
//            System.out.println("stone " + stone + " size: " + size);
            TimeKeeper.printTimePassed();
        }
        System.out.println(size);
    }


    public long countFinalStones(long stone, MutableInt iterationsLeft) {
        long numberOfStones = 1;
        List<Long> newStones = new ArrayList<>();
        List<Integer> newStonesIterations = new ArrayList<>();
        List<Long> evolvedStones = new ArrayList<>();
        MutableInt iterations = new MutableInt(0);

        while (iterationsLeft.value >= 0) {
            boolean skipped = false;
//            if (nonSplits.containsKey(stone) && nonSplits.get(stone) > iterationsLeft.value) {
//                skipped = true;
//                iterations += nonSplits.get(stone);
//                addTerminalNumbers(evolvedStones, iterations);
//                evolvedStones.clear();
//                iterations=0;
//            }else if (splits.containsKey(stone)) {
//                skipped = true;
//                iterationsLeft.value -= splits.get(stone).get(0);
//                iterations += splits.get(stone).get(0);
//                long left = splits.get(stone).get(1);
//                long right = splits.get(stone).get(2);
//                if (iterationsLeft.value >= 0) {
//                    numberOfStones++;
//                    executeSplit(left, right, iterationsLeft,  evolvedStones, newStones, newStonesIterations, iterations);
//                }
//                stone = left;
//                evolvedStones.clear();
//                iterations=0;
//            }

            if (iterationsLeft.value > 0) {
                if (nonSplits.containsKey(stone) && nonSplits.get(stone) > iterationsLeft.value) {
                    skipped = true;
                    iterations.value += nonSplits.get(stone);
                    addTerminalNumbers(evolvedStones, iterations.value);
                    evolvedStones.clear();
                    iterations.value=0;
                }
//                else if (splits.containsKey(stone)) {
//                    iterationsLeft.value -= splits.get(stone).get(0);
//                    iterations += splits.get(stone).get(0);
//                    if (iterationsLeft.value < 0) {
//                    }
//                    long left = splits.get(stone).get(1);
//                    long right = splits.get(stone).get(2);
//
//                    stone = left;
//                    numberOfStones++;
//                    executeSplit(left, right, iterationsLeft, evolvedStones, newStones, newStonesIterations, iterations);
//                    evolvedStones.clear();
//                    iterations = 0;
//                }
                if (stone == 0) {
                    addIteration(evolvedStones,stone,iterations,iterationsLeft);
                    stone = 1;
                } else if (hasEvenDigits(stone)) {
                    addIteration(evolvedStones,stone,iterations,iterationsLeft);
                    String str = Long.toString(stone);
                    long left = Long.parseLong(str.substring(0, str.length() / 2));
                    long right = Long.parseLong(str.substring(str.length() / 2));
//                    System.out.println("split: " + stone);
                    stone = left;
                    numberOfStones++;
                    executeSplit(left, right, iterationsLeft, evolvedStones, newStones, newStonesIterations, iterations);
                    evolvedStones.clear();
                    iterations.value = 0;
                } else {
                    addIteration(evolvedStones,stone,iterations,iterationsLeft);
                    stone *= 2024;
                }
            } else if (!newStones.isEmpty()) {
                addTerminalNumbers(evolvedStones, iterations.value);
                stone = newStones.get(0);
                newStones.remove(0);
                iterationsLeft.value = newStonesIterations.get(0);
                newStonesIterations.remove(0);
                iterations.value = 0;
                evolvedStones.clear();
            } else {
                break;
            }
        }



        return numberOfStones;
    }

    private void executeSplit(long left, long right, MutableInt iterationsLeft, List<Long> evolvedStones,List<Long> newStones, List<Integer> newStonesIterations, MutableInt iterations) {
        addTerminalSplits(evolvedStones, iterations, left, right);
        newStones.add(right);
        newStonesIterations.add(iterationsLeft.value);
    }

    public void addIteration(List<Long> evolvedStones, long stone, MutableInt iterations, MutableInt iterationsLeft){
        iterationsLeft.value--;
        iterations.value++;
        evolvedStones.add(stone);
    }


    public void addTerminalNumbers(List<Long> evolvedStones,int iterations){
        for (int i = 0; i < evolvedStones.size(); i++) {
            if (!nonSplits.containsKey(evolvedStones.get(i)) || nonSplits.get(evolvedStones.get(i)) < iterations-i){
                nonSplits.put(evolvedStones.get(i), iterations-i);
            }
        }
    }

    public void addTerminalSplits(List<Long> evolvedStones,MutableInt iterations, long left, long right){
        for (int i = 0; i < evolvedStones.size(); i++) {
            splits.put(evolvedStones.get(i),Arrays.asList((long) iterations.value-i,left,right));
        }
    }
}
