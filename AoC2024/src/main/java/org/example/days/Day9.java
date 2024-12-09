package org.example.days;

import org.example.dataReaders.LineDataReader;

import java.util.List;

import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day9 extends Day{
    @Override
    public void task1() {
        List<String> input = LineDataReader.getLines("day9", TEST,false);

    }

    @Override
    public void task2() {

    }
}
