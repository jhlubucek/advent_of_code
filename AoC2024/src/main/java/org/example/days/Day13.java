package org.example.days;

import org.example.common.Helpers;
import org.example.dataReaders.LineDataReader;

import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day13 extends Day{
    @Override
    public void task1() {
        List<String> input = LineDataReader.getLines("day13", INPUT,false);
        long[][] machines = new long[input.size()/4+1][];
        int i=0;
        while(i +3 <= input.size()){
            Matcher matcherButton1 = Helpers.matchRegex( "X\\+(\\d+), Y\\+(\\d+)",input.get(i));
            matcherButton1.find();
            Matcher matcherButton2 = Helpers.matchRegex( "X\\+(\\d+), Y\\+(\\d+)",input.get(i+1));
            matcherButton2.find();
            Matcher matcherPrice = Helpers.matchRegex( "X\\=(\\d+), Y\\=(\\d+)",input.get(i+2));
            matcherPrice.find();
            long [] machine = {
                    Long.parseLong(matcherButton1.group(1)),Long.parseLong(matcherButton1.group(2)),
                    Long.parseLong(matcherButton2.group(1)),Long.parseLong(matcherButton2.group(2)),
                    Long.parseLong(matcherPrice.group(1)),Long.parseLong(matcherPrice.group(2)),
            };
            machines[i/4] = machine;
            i+=4;
        }

        long sum = 0;
        for (long[] machine : machines){
            if (areButtonsDependant(machine)){
                throw new RuntimeException("fuck");
            }
            sum += solveClawMachineNonDependant(machine);
        }
        System.out.println(sum);
    }


    public static boolean areButtonsDependant(long[] m) {
        return (m[0] * m[3] - m[1] * m[2]) == 0;
    }

    public static long solveClawMachineNonDependant(long[] m) {
        long denominator = m[0] * m[3] - m[1] * m[2];
        long numeratorI = m[4] * m[3] - m[5] * m[2];
        long numeratorJ = m[0] * m[5] - m[1] * m[4];

        if (numeratorI % denominator != 0 || numeratorJ % denominator != 0) {
            return 0;
        }

        long i = (m[4]*m[3]-m[5]*m[2])/(m[0]*m[3]-m[1]*m[2]);
        long j = (m[0]*m[5]-m[1]*m[4])/(m[0]*m[3]-m[1]*m[2]);
        return i*3+j;
    }

    @Override
    public void task2() {
        List<String> input = LineDataReader.getLines("day13", INPUT,false);
        long[][] machines = new long[input.size()/4+1][];
        int i=0;
        while(i +3 <= input.size()){
            Matcher matcherButton1 = Helpers.matchRegex( "X\\+(\\d+), Y\\+(\\d+)",input.get(i));
            matcherButton1.find();
            Matcher matcherButton2 = Helpers.matchRegex( "X\\+(\\d+), Y\\+(\\d+)",input.get(i+1));
            matcherButton2.find();
            Matcher matcherPrice = Helpers.matchRegex( "X\\=(\\d+), Y\\=(\\d+)",input.get(i+2));
            matcherPrice.find();
            long [] machine = {
                    Long.parseLong(matcherButton1.group(1)),Long.parseLong(matcherButton1.group(2)),
                    Long.parseLong(matcherButton2.group(1)),Long.parseLong(matcherButton2.group(2)),
                    Long.parseLong(matcherPrice.group(1))+10000000000000L,Long.parseLong(matcherPrice.group(2))+10000000000000L,
            };
            machines[i/4] = machine;
            i+=4;
        }

        long sum = 0;
        for (long[] machine : machines){
            if (areButtonsDependant(machine)){
                throw new RuntimeException("fuck");
            }
            sum += solveClawMachineNonDependant(machine);
        }
        System.out.println(sum);
    }
}
