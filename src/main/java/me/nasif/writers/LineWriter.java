package me.nasif.writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LineWriter implements FileWritable {

    @Override
    public void write(String fileNameWithPath, List<?> contents) throws IOException, IllegalArgumentException, IllegalAccessException {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(fileNameWithPath))) {
            for (Object s : contents) {
                br.write((String) s);
                br.write("\n");
            }
        }
    }
}
