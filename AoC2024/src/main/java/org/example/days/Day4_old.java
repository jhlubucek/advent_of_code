package org.example.days;

import org.example.common.DataReaderDay4;
import org.example.common.Helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day4_old {

    public static void task1(){
        DataReaderDay4 data = new DataReaderDay4("data/day4-old/test.txt");
        Helpers.printList(data.getDraws());
    }
}
