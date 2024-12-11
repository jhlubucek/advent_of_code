package org.example.days;

import org.example.common.Helpers;
import org.example.dataReaders.StringDataReader;

import java.util.*;

import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day11 extends Day{
    private Map<Long,List<Long>> splits = new HashMap<>();
    private static int iter = 25;
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

    public static List<Long> applyRules(Long stone) {
        if (stone == 0) {
            return List.of(1L);
        }else if (hasEvenDigits(stone)) {
            String str = Long.toString(stone);
            Long left = Long.parseLong(str.substring(0,str.length()/2));
            Long right = Long.parseLong(str.substring(str.length()/2));
            return Arrays.asList(left,right);
        }else {
            return List.of(stone * 2024);
        }
    }

    public static boolean hasEvenDigits(long number) {
        number = Math.abs(number);
        int digitCount = Long.toString(number).length();
        return digitCount % 2 == 0;
    }

    @Override
    public void task2() {
        String input = StringDataReader.getString("day10", TEST,false);
        List<Long> stones = Helpers.stringToLongList(input, "\\s+");
        //to memory intensive
//        for (int i = 0; i < 75; i++) {
//            applyRules(stones);
//        }
//        System.out.println(stones.size());

        long size = 0;
        for (Long stone : stones) {
            size += countFinalStones(stone,iter,true);
        }
        System.out.println(size);
    }


    public long countFinalStones(long stone, int iterationsLeft, boolean isNew) {
        long numberOfStones = isNew ? 1 : 0;
        int iterations = 0;
        List<Long> newStones = new ArrayList<>();
        List<Integer> newStonesTerations = new ArrayList<>();
        List<Long> evolvedStones = new ArrayList<>();


        while(iterationsLeft > 0) {
            if (splits.containsKey(stone)) {
                iterationsLeft -= splits.get(stone).get(0);
                if (iterationsLeft < 0) {
                    return numberOfStones;
                }
                long left = splits.get(stone).get(1);
                long right = splits.get(stone).get(2);
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

                String str = Long.toString(stone);
                long left = Long.parseLong(str.substring(0,str.length()/2));
                long right = Long.parseLong(str.substring(str.length()/2));
                for (int i = 0; i < evolvedStones.size(); i++) {
                    splits.put(evolvedStones.get(i),Arrays.asList((long) iterations-i,left,right));
                }
                numberOfStones+=countFinalStones(left,iterationsLeft,false);
                newStones.add(right);
                newStonesTerations.add(iterationsLeft);

                break;
            } else {
                stone *= 2024;
            }
        }
        for (int i = 0; i < newStones.size(); i++) {
            numberOfStones+= countFinalStones(newStones.get(i), newStonesTerations.get(i),true);
        }

        return numberOfStones;
    }
}
