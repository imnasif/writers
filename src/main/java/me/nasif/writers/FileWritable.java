package me.nasif.writers;

import java.io.IOException;
import java.util.List;

public interface FileWritable {

    void write(String fileNameWithPath, List<?> contents) throws IOException, IllegalArgumentException, IllegalAccessException;

}
