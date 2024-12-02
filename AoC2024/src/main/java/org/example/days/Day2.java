package org.example.days;

import org.example.common.DataReaders;
import org.example.common.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day2 {

    public static void day2(){
        List<List<Integer>> data = DataReaders.readLists("data/day2/input.txt");

        AtomicInteger safe = new AtomicInteger(0);

        data.forEach( l -> {
            l.forEach( i -> System.out.print(i + ","));
            System.out.println();
            if(isListSafe(l, true)){
                safe.getAndIncrement();
            }
        });

        System.out.println();
        System.out.println("VYSLEDEK: " + safe.get());
    }

    public static boolean isListSafe(List<Integer> list, boolean canRemove){
        if (list.size()==1) return true;

        int upperBound = list.get(0) > list.get(1) ? 3 : -1;
        int lowerBound = list.get(0) > list.get(1) ? 1 : -3;

        for (int i = 1; i < list.size(); i++){
            int distance = list.get(i-1) - list.get(i);
            if (distance > upperBound || distance < lowerBound){
                if (canRemove){
                    return testSublists(list, i);
                }
                System.out.println("NOT SAFE");
                return false;
            }
        };
        System.out.println("SAFE");
        return true;
    }

    public static boolean testSublists(List<Integer> list, int index){
        System.out.println("test sublists");
        List<Integer> indexes = new ArrayList<>();
        indexes.add(0);

        if (!indexes.contains(index)){
            indexes.add(index);
        }
        if (!indexes.contains(index-1)){
            indexes.add(index-1);
        }

        Helpers.printList("original",list);

        for (int i = 0 ; i < indexes.size(); i++){
            List<Integer> sublist = deepCloneList(list);
            int x = indexes.get(i);
            sublist.remove(x);
            Helpers.printList("sublist"+i,sublist);
            if (isListSafe(sublist,false)){
                return true;
            }
        }
        return false;
    }

    public static List<Integer> deepCloneList(List<Integer> list) {
        List<Integer> copy = new ArrayList<>();
        for (Integer item : list) {
            copy.add(Integer.valueOf(item));
        }
        return copy;
    }
}
