package org.example.dataReaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LineDataReader extends DataReader{
    public static List<String> getLines( String dayName, FileType fileType, boolean partTwo) {
        try {
            return Files.lines(Paths.get(getfileName(dayName, fileType, partTwo))).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Character[][] getCharArray( String dayName, FileType fileType, boolean partTwo) {
        List<String> stringList = getLines(dayName, fileType, partTwo);
        Character[][] characterArray = new Character[stringList.size()][];

        for (int i = 0; i < stringList.size(); i++) {
            String line = stringList.get(i);
            // Convert each String to a Character[]
            Character[] row = new Character[line.length()];
            for (int j = 0; j < line.length(); j++) {
                row[j] = line.charAt(j);
            }
            characterArray[i] = row;
        }

        return characterArray;
    }
}
