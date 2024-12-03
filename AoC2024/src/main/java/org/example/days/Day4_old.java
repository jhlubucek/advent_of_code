package org.example.days;


import org.example.common.Helpers;
import org.example.dataReaders.LineDataReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day4_old extends Day{

     @Override
    public void task1(){
         List<String> data = LineDataReader.getLines("day4-old", INPUT, false);

         HashMap<Integer, Integer> draws = new HashMap<>();
         List<Integer> drawsList = Helpers.stringToIntList(data.get(0),",");
         for (int i = 0; i < drawsList.size(); i++) {
             draws.put(drawsList.get(i), i+1);
         }

         Helpers.printMap(draws);

         List<List<List<Integer>>> matrices = new ArrayList<>();

         int startIndex = 2;
         while (startIndex < data.size()) {
             List<String> sublist = data.subList(startIndex,startIndex+5);
             startIndex += 6;

             matrices.add(stringListToMatrice(sublist));
         }

         int earliestDraw = Integer.MAX_VALUE;
         int matrixIndex = -1;
         for (int i = 0; i < matrices.size(); i++) {
             int min = getEarliestRowOrCol(matrices.get(i),draws);

             if (min < earliestDraw) {
                 earliestDraw = min;
                 matrixIndex = i;
             }
         }

         int sum = 0;
         List<List<Integer>> finalMatrix = matrices.get(matrixIndex);

         for (int i = 0; i < finalMatrix.size(); i++) {
             for (int j = 0; j < finalMatrix.get(i).size(); j++) {
                 int value = finalMatrix.get(i).get(j);
                 if (draws.get(value) > earliestDraw) {
                     sum+=value;
                 }

             }
         }
         int drawValue = Helpers.getMapKeyByValue(draws,earliestDraw);
         System.out.println(sum*drawValue);
    }

    private static int getEarliestRowOrCol(List<List<Integer>> matrix, HashMap<Integer, Integer> draws){
         int lowesRow = Integer.MAX_VALUE;
         int lowesCol = Integer.MAX_VALUE;
         for (int i = 0; i < matrix.size(); i++) {
             int highestRowVal = Integer.MIN_VALUE;
             int highestColVal = Integer.MIN_VALUE;
             for (int j = 0; j < matrix.get(i).size(); j++) {
                 int rowVal = matrix.get(i).get(j);
                 int rowDrawNum = draws.get(rowVal);
                 if (rowDrawNum > highestRowVal){
                     highestRowVal = rowDrawNum;
                 };

                 int colVal = matrix.get(j).get(i);
                 int colDrawNum = draws.get(colVal);
                 if (colDrawNum > highestColVal){
                     highestColVal = colDrawNum;
                 };
             }
             if (lowesRow > highestRowVal){
                 lowesRow = highestRowVal;
             }
             if (lowesCol > highestColVal){
                 lowesCol = highestColVal;
             }
         }
         return Math.min(lowesRow, lowesCol);
    }

    private static List<List<Integer>> stringListToMatrice(List<String> list){
        List<List<Integer>> matrix = new ArrayList<>();
        int size = list.size();

        for (int i = 0; i < size; i++) {
            matrix.add(Helpers.stringToIntList(list.get(i).stripLeading(),"\\s+"));
        }
        return matrix;
    }

    @Override
    public void task2() {
        List<String> data = LineDataReader.getLines("day4-old", INPUT, false);

        HashMap<Integer, Integer> draws = new HashMap<>();
        List<Integer> drawsList = Helpers.stringToIntList(data.get(0),",");
        for (int i = 0; i < drawsList.size(); i++) {
            draws.put(drawsList.get(i), i+1);
        }

        Helpers.printMap(draws);

        List<List<List<Integer>>> matrices = new ArrayList<>();

        int startIndex = 2;
        while (startIndex < data.size()) {
            List<String> sublist = data.subList(startIndex,startIndex+5);
            startIndex += 6;

            matrices.add(stringListToMatrice(sublist));
        }

        int latestDraw = Integer.MIN_VALUE;
        int matrixIndex = -1;
        for (int i = 0; i < matrices.size(); i++) {
            int max = getEarliestRowOrCol(matrices.get(i),draws);

            if (max > latestDraw) {
                latestDraw = max;
                matrixIndex = i;
            }
        }

        int sum = 0;
        List<List<Integer>> finalMatrix = matrices.get(matrixIndex);

        for (int i = 0; i < finalMatrix.size(); i++) {
            for (int j = 0; j < finalMatrix.get(i).size(); j++) {
                int value = finalMatrix.get(i).get(j);
                if (draws.get(value) > latestDraw) {
                    sum+=value;
                }

            }
        }
        int drawValue = Helpers.getMapKeyByValue(draws,latestDraw);
        System.out.println(sum*drawValue);

    }
}
