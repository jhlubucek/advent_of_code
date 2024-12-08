package org.example.days;

import org.example.common.Helpers;
import org.example.dataReaders.LineDataReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day7 extends Day{
    @Override
    public void task1() {
        List<String> input = LineDataReader.getLines("day7", INPUT,false);

        long correctCounter = 0;
        for (String line : input) {
            String[] numbers = line.split(":");
            long sum = Long.parseLong(numbers[0]);
            List<Integer> data = Helpers.stringToIntList(numbers[1],"\\s+");
//
            correctCounter += tryToSolve(sum,data,data.get(0),1) > 0 ? sum : 0;

        }
        System.out.println(correctCounter);
    }

    int tryToSolve(long targetSum , List<Integer> data, long previousSum, int index){
        if (index >= data.size()) {
            if (previousSum == targetSum){
                return 1;
            }else {
                return 0;
            }
        }
        return (tryToSolve(targetSum,data,previousSum*data.get(index),index+1)) +
                (tryToSolve(targetSum,data,previousSum+data.get(index),index+1)) +
                (tryToSolve(targetSum,data,combineNumbers(previousSum,data.get(index)),index+1))
                ;
    }

    long combineNumbers(long a, int b){
        return Long.parseLong(Long.toString(a)+Integer.toString(b));
    }

    @Override
    public void task2() {

    }
}
