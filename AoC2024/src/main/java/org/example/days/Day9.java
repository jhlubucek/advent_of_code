package org.example.days;

import lombok.AllArgsConstructor;
import org.example.common.Helpers;
import org.example.dataReaders.LineDataReader;
import org.example.dataReaders.StringDataReader;

import java.util.ArrayList;
import java.util.List;

import static org.example.dataReaders.DataReader.FileType.INPUT;
import static org.example.dataReaders.DataReader.FileType.TEST;

public class Day9 extends Day{
    @Override
    public void task1() {
        String input = StringDataReader.getString("day9", INPUT,false);
        List<Integer> list = new ArrayList<>();

        boolean isFree = false;
        int fileCounter = 0;
        for (char c : input.toCharArray()) {
            for(int i = 0; i < Integer.parseInt(String.valueOf(c)); i++) {
                list.add( isFree ? -1 : fileCounter);
            }
            fileCounter+= isFree ? 0 : 1;
            isFree = !isFree;
        }


        int i = 0;
        int j = list.size() - 1;
        while (i < j) {
            boolean iFound = list.get(i) == -1;
            boolean jFound = list.get(j) != -1;
            if (iFound && jFound) {
                swapElements(list, i, j);
            }else  {
                if (!iFound) {
                    i++;
                }
                if (!jFound) {
                    j--;
                }
            }
        }

        System.out.println(getHash(list));
    }

    public static long getHash(List<Integer> list) {
        long sum = 0;
        for (int k = 0; k < list.size(); k++) {
            if (list.get(k)>0){
                sum += list.get(k)*k;
            }
        }
        return sum;
    }

    public static void swapElements(List<Integer> list, int index1, int index2) {
        Integer temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    @Override
    public void task2() {
        String input = StringDataReader.getString("day9", INPUT,false);
        List<Integer> list = new ArrayList<>();
        List<Space> files = new ArrayList<>();
        int index = 0;

        boolean isFree = false;
        int fileCounter = 0;
        for (char c : input.toCharArray()) {
            if (!isFree){
                files.add(new File(index,Character.getNumericValue(c),fileCounter));
            }else {
                files.add(new Space(index,Character.getNumericValue(c)));
            }

            index += Character.getNumericValue(c);
            fileCounter+= isFree ? 0 : 1;
            isFree = !isFree;
        }

        for (int i = files.size()-1; i >= 0; i--) {
            if (files.get(i) instanceof File file){
                int j = 0;
                while (j < files.size() && j < i){
                    if (files.get(j).getClass() == Space.class && files.get(j).lenght >= files.get(i).lenght){
                        moveFile(files,file,i,j);
                        break;
                    }
                    j++;
                }
            }
        }
        System.out.println(getHashFiles(files));
    }

    public void moveFile(List<Space> files, File file, int i, int j) {
        Space newSpace = new Space(file.index, file.lenght);
        files.set(i, newSpace);
        files.get(j).lenght -= file.lenght;
        files.add(j,file);

    }

    @AllArgsConstructor
    public class Space{
        public int index;
        public int lenght;

        @Override
        public String toString() {
            return ".".repeat(Math.max(0, lenght));
        }

    }

    public class File extends Space{
        public int number;

        public File(int index, int lenght, int number) {
            super(index, lenght);
            this.number = number;
        }

        @Override
        public String toString() {
            return Integer.toString(number).repeat(Math.max(0, lenght));
        }
    }

    public static long getHashFiles(List<Space> list) {
        long sum = 0;
        int i=0;
        for (Space s : list) {
            for (int j = 0; j < s.lenght; j++) {
                if (s instanceof File file){
                    sum+=file.number*i;
                }
                i++;
            }
        }
        return sum;
    }
}
