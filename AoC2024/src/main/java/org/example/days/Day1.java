package org.example.days;

import org.example.common.DataReaders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day1 {

    public static void calculateSimilarity(){
        HashMap<Integer,Integer> leftMap = new HashMap<>();
        HashMap<Integer,Integer> rightMap = new HashMap<>();

        DataReaders.readDualLists("data/day1/input.txt", leftMap, rightMap);

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

    public static void calculateDistance(){
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        DataReaders.readDualLists("data/day1/input.txt", leftList, rightList);

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
