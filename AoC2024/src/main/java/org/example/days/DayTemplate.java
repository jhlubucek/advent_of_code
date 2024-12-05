package org.example.days;

import org.example.common.Helpers;
import org.example.dataReaders.DataReader;
import org.example.dataReaders.LineDataReader;

import java.util.List;

import static org.example.common.Helpers.*;
import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class DayTemplate extends Day{
    @Override
    public void task1() {
        List<String> input = LineDataReader.getLines("day", TEST,false);

    }

    @Override
    public void task2() {

    }
}
