package org.example.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataReaders {

    public static void readDualLists(String fileName, List<Integer> leftList, List<Integer> rightList){
        try {
            Files.lines(Paths.get(fileName)).forEach(line -> {
                String[] numbers = line.split("\\s+");
                leftList.add(Integer.parseInt(numbers[0]));
                rightList.add(Integer.parseInt(numbers[1]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readDualLists(String fileName, Map<Integer,Integer> leftMap, Map<Integer,Integer> rightMap){
        try {
            Files.lines(Paths.get(fileName)).forEach(line -> {
                String[] numbers = line.split("\\s+");
                leftMap.merge(Integer.parseInt(numbers[0]), 1, Integer::sum);
                rightMap.merge(Integer.parseInt(numbers[1]), 1, Integer::sum);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<List<Integer>> readLists(String fileName){
        List<List<Integer>> list = new ArrayList<>();
        try {
            Files.lines(Paths.get(fileName)).forEach(line -> {
                String[] numbers = line.split("\\s+");
                List<Integer> newLine = new ArrayList<>();
                IntStream.range(0,numbers.length).forEach( i -> {
                    newLine.add(Integer.parseInt(numbers[i]));
                });
                list.add(newLine);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


}
