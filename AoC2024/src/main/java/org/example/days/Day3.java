package org.example.days;

import org.example.common.Helpers;
import static org.example.common.Helpers.regexMatches;
import org.example.dataReaders.StringDataReader;

import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;

public class Day3 extends Day {

    public void task1() {
        String input = StringDataReader.getString("day3",INPUT, false);

        System.out.println(mulCounter(input));


            // bad regular expression

    }

    public void task2() {
        String input = StringDataReader.getString("day3",INPUT, false);


        System.out.println(mulCounter2(input));


        // bad regular expression

    }

    public int mulCounter(String input){
        List<String> operations = regexMatches("mul\\(\\d*,\\d*\\)",input);
        Helpers.printList(operations);

        int sum = 0;

        for(String operation : operations){
            List<String>number1 = regexMatches("\\d*(?=,)",operation);
            List<String>number2 = regexMatches("\\d*(?=\\))",operation);
            sum+=Integer.parseInt(number1.get(0))*Integer.parseInt(number2.get(0));
        }

        return sum;
    }

    public int mulCounter2(String input){
        List<String> operations = regexMatches("(mul\\(\\d*,\\d*\\))|(do\\(\\)|(don't\\(\\)))",input);
        Helpers.printList(operations);

        int sum = 0;
        int docounter = 0;
        boolean d = false;
        boolean dn = false;

        for(String operation : operations){
            if (operation.equals("do()")) {
                d=true;
                dn=false;
                docounter++;
            } else if (operation.equals("don't()")) {
                d=false;
                dn=true;
                docounter++;
            } else {
                if (d || docounter == 0) {
                    List<String> number1 = regexMatches("\\d*(?=,)", operation);
                    List<String> number2 = regexMatches("\\d*(?=\\))", operation);
                    sum += Integer.parseInt(number1.get(0)) * Integer.parseInt(number2.get(0));
                }if (dn){
                }

            }
        }

        return sum;
    }
}
