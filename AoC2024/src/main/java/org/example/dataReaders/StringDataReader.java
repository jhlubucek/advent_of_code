package org.example.dataReaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StringDataReader extends DataReader {



    public static String getString(String dayName, FileType fileType, boolean partTwo){
        try {
            return Files.readString(Path.of(getfileName(dayName, fileType, partTwo)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
