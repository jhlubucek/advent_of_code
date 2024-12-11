package org.example.days;

import org.example.common.Helpers;
import org.example.common.TimeKeeper;
import org.example.dataReaders.StringDataReader;

import java.util.*;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day11 extends Day{
    private Map<Integer,List<Integer>> splits = new HashMap<>();
    private Map<Integer,Integer> nonSplits = new HashMap<>();
    private static int iter = 25;
    @Override
    public void task1() {
        String input = StringDataReader.getString("day11", TEST,false);
        List<Integer> stones = Helpers.stringToIntList(input, "\\s+");
        for (int i = 0; i < iter; i++) {
            applyRules(stones);
        }
        System.out.println(stones.size());
    }

        public static void applyRules(List<Integer> stones) {
            List<Integer> newStones = stones.stream()
                    .flatMap(stone -> applyRules(stone).stream())
                    .toList();

            stones.clear();
            stones.addAll(newStones);
        }

    public static List<Integer> applyRules(int stone) {
        if (stone == 0) {
            return List.of(1);
        }else if (hasEvenDigits(stone)) {
            String str = Integer.toString(stone);
            int left = Integer.parseInt(str.substring(0,str.length()/2));
            int right = Integer.parseInt(str.substring(str.length()/2));
            return Arrays.asList(left,right);
        }else {
            return List.of(stone * 2024);
        }
    }

    public static boolean hasEvenDigits(int number) {
        number = Math.abs(number);
        int digitCount = Integer.toString(number).length();
        return digitCount % 2 == 0;
    }

    @Override
    public void task2() {
        String input = StringDataReader.getString("day11", TEST,false);
        List<Integer> stones = Helpers.stringToIntList(input, "\\s+");
        //to memory intensive
//        for (int i = 0; i < 75; i++) {
//            applyRules(stones);
//        }
//        System.out.println(stones.size());

        long size = 0;
        for (Integer stone : stones) {
            size += countFinalStones(stone,25,true);
            System.out.println("stone " + stone + " size: " + size);
            TimeKeeper.printTimePassed();
        }
        System.out.println(size);
    }


    public long countFinalStones(int stone, int iterationsLeft, boolean isNew) {
        long numberOfStones = isNew ? 1 : 0;
        int iterations = 0;
        List<Integer> newStones = new ArrayList<>();
        List<Integer> newStonesTerations = new ArrayList<>();
        List<Integer> evolvedStones = new ArrayList<>();


        while(iterationsLeft > 0) {
            if (nonSplits.containsKey(stone) && nonSplits.get(stone) > iterationsLeft) {
                iterations += nonSplits.get(stone);
                addTerminalNumbers(evolvedStones,iterations);
                return numberOfStones;
            }
            if (splits.containsKey(stone)) {
                iterationsLeft -= splits.get(stone).get(0);
                iterations += splits.get(stone).get(0);
                if (iterationsLeft < 0) {
                    return numberOfStones;
                }
                int left = splits.get(stone).get(1);
                int right = splits.get(stone).get(2);
                addTerminalSplits(evolvedStones,iterations,left,right);
                numberOfStones+=countFinalStones(left,iterationsLeft,false);
                newStones.add(right);
                newStonesTerations.add(iterationsLeft);
                break;
            }
            iterations++;
            iterationsLeft--;
            evolvedStones.add(stone);

            if (stone == 0) {
                stone = 1;
            } else if (hasEvenDigits(stone)) {

                String str = Integer.toString(stone);
                int left = Integer.parseInt(str.substring(0,str.length()/2));
                int right = Integer.parseInt(str.substring(str.length()/2));
                addTerminalSplits(evolvedStones,iterations,left,right);
                numberOfStones+=countFinalStones(left,iterationsLeft,false);
                newStones.add(right);
                newStonesTerations.add(iterationsLeft);

                break;
            } else {
                stone *= 2024;
            }
        }
        addTerminalNumbers(evolvedStones,iterations);
        for (int i = 0; i < newStones.size(); i++) {
            numberOfStones+= countFinalStones(newStones.get(i), newStonesTerations.get(i),true);
        }

        return numberOfStones;
    }

    public void addTerminalNumbers(List<Integer> evolvedStones,int iterations){
        for (int i = 0; i < evolvedStones.size(); i++) {
            if (!nonSplits.containsKey(evolvedStones.get(i)) || nonSplits.get(evolvedStones.get(i)) < iterations-i){
                nonSplits.put(evolvedStones.get(i), iterations-i);
            }
        }
    }
    public void addTerminalSplits(List<Integer> evolvedStones,int iterations, int left, int right){
        for (int i = 0; i < evolvedStones.size(); i++) {
            splits.put(evolvedStones.get(i),Arrays.asList( iterations-i,left,right));
        }
    }
}
