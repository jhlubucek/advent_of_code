package org.example.days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.common.Helpers;
import org.example.dataReaders.DataReader;
import org.example.dataReaders.LineDataReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day5 extends Day{
    HashMap<Integer, List<Rule>> rules = new HashMap<>();


    @Override
    public void task1() {
        List<String> data = LineDataReader.getLines("day5", TEST, false);
        List<List<Integer>> sets = new ArrayList<>();

        int i = 0;
        while (data.get(i).length() > 1) {
            List<Integer> rule = Helpers.stringToIntList(data.get(i),"\\|");
            if (!rules.containsKey(rule.get(1))) {
                rules.put(rule.get(1),new ArrayList<>());
            }
            rules.get(rule.get(1)).add(new Rule(rule.get(0),rule.get(1)));
            i++;
        }
        for (i++; i < data.size(); i++) {
            List<Integer> set = Helpers.stringToIntList(data.get(i),",");
            sets.add(set);
        }
//        rules.forEach( (k,v) -> {
//            System.out.print(k  +": ");
//            v.forEach(System.out::println);
//        });
//        sets.forEach(Helpers::printList);

        int sum = 0;
        for (List<Integer> set : sets) {
            sum+=getSetNumber(set);
        }
        System.out.println(sum);
    }

    public int getSetNumber(List<Integer> set){
        List<Integer> printed = new ArrayList<>();
        Helpers.printList(set);

        for (Integer i : set) {
            if (rules.containsKey(i)) {
                for (Rule rule :rules.get(i)){
                    if (!printed.contains(rule.getBefore()) && set.contains(rule.getBefore())){
                        System.out.println("fail");
                        return 0;
                    }
                }
            }
            printed.add(i);
        }
        System.out.println("print");
        System.out.println(set.get(set.size()/2));
        return set.get(set.size()/2);
    }

    @Getter
    @AllArgsConstructor
    public class Rule{
        private int before;
        private int after;

        @Override
        public String toString() {
            return "Rule{" + before + "|" + after +  '}';
        }
    }

    @Override
    public void task2() {
        List<String> data = LineDataReader.getLines("day5", INPUT, false);
        List<List<Integer>> sets = new ArrayList<>();

        int i = 0;
        while (data.get(i).length() > 1) {
            List<Integer> rule = Helpers.stringToIntList(data.get(i),"\\|");
            if (!rules.containsKey(rule.get(1))) {
                rules.put(rule.get(1),new ArrayList<>());
            }
            rules.get(rule.get(1)).add(new Rule(rule.get(0),rule.get(1)));
            i++;
        }
        for (i++; i < data.size(); i++) {
            List<Integer> set = Helpers.stringToIntList(data.get(i),",");
            sets.add(set);
        }
//        rules.forEach( (k,v) -> {
//            System.out.print(k  +": ");
//            v.forEach(System.out::println);
//        });
//        sets.forEach(Helpers::printList);

        int sum = 0;
        for (List<Integer> set : sets) {
            if (getSetNumber(set) == 0){
                sum+=getWrongSetNumber(set);
            }
        }
        System.out.println(sum);

    }

    private int getWrongSetNumber(List<Integer> set) {
        List<Integer> printed = new ArrayList<>();
        Helpers.printList(set);

        for (Integer i : set) {
            if (rules.containsKey(i)) {
                for (Rule rule :rules.get(i)){
                    if (!printed.contains(rule.getBefore()) && set.contains(rule.getBefore())){
                        reorder(set,set.indexOf(rule.getAfter()),set.indexOf(rule.getBefore()));
                        return getWrongSetNumber(set);
                    }
                }
            }
            printed.add(i);
        }
        return set.get(set.size()/2);
    }

    private void reorder(List<Integer> set, int x, int y) {
         Integer temp = set.remove(y);
        set.add(x, temp);
    }

}
