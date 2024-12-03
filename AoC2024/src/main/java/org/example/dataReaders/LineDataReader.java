package org.example.dataReaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LineDataReader extends DataReader{
    public static List<String> getLines( String dayName, FileType fileType, boolean partTwo) {
        try {
            return Files.lines(Paths.get(getfileName(dayName, fileType, partTwo))).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
