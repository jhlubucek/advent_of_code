package org.example.days;

import org.example.dataReaders.LineDataReader;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day1 extends Day{

    @Override
    public void task2(){
        HashMap<Integer,Integer> leftMap = new HashMap<>();
        HashMap<Integer,Integer> rightMap = new HashMap<>();

        LineDataReader.getLines("day1", TEST,false).forEach(line -> {
            String[] numbers = line.split("\\s+");
            leftMap.merge(Integer.parseInt(numbers[0]), 1, Integer::sum);
            rightMap.merge(Integer.parseInt(numbers[1]), 1, Integer::sum);
        });

        leftMap.forEach( (k,v) -> System.out.print(k + ":" + v + " ") );
        System.out.println();
        rightMap.forEach( (k,v) -> System.out.print(k + ":" + v + " ") );
        System.out.println();

        AtomicInteger similaritySum = new AtomicInteger();

        leftMap.forEach( (k1,v1) -> {
            if (rightMap.containsKey(k1)){
                int similarity = k1*v1*rightMap.get(k1);
                System.out.print( similarity + " ");
                similaritySum.addAndGet(similarity);
            }
        });
        System.out.println();
        System.out.println(similaritySum);
    }

    @Override
    public void task1(){
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        LineDataReader.getLines("day1", TEST,false).forEach(line -> {
            String[] numbers = line.split("\\s+");
            leftList.add(Integer.parseInt(numbers[0]));
            rightList.add(Integer.parseInt(numbers[1]));
        });

        Collections.sort(leftList);
        Collections.sort(rightList);

        leftList.forEach(i -> System.out.print(i + " "));
        System.out.println();
        rightList.forEach(i -> System.out.print(i + " "));
        System.out.println();

        AtomicInteger sum = new AtomicInteger();

        IntStream.range(0, leftList.size())
                .forEach(i -> {
                            int x = Math.abs(leftList.get(i)-rightList.get(i));
                            System.out.print(x + " ");
                            sum.addAndGet(x);
                        }
                );

        System.out.println();
        System.out.println(sum);
    }

}
