package org.example.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DataReaderDay4 {
    private List<String> lines;

    public DataReaderDay4(String fileName) {
        try {
            lines = Files.lines(Paths.get(fileName)).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getDraws(){
        List<Integer> draws = new ArrayList<>();

        String[] numbers = lines.get(0).split(",");
        IntStream.range(0,numbers.length).forEach(i -> {
            draws.add(Integer.parseInt(numbers[i]));
        });


        return draws;
    }

    public List<List<Integer>> getWiningCombos(int size){
        for (int i = 0; i<(lines.size()-1)/6; i++){
            lines.get(3+6*i);
        }
        return null;
    }
}
