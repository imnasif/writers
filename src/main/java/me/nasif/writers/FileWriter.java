package me.nasif.writers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    private final String finalPath;
    private final FileWritable strategy;

    public FileWriter(String baseUrl, String fileName, FileWritable strategy) {
        this.finalPath = Paths.get(baseUrl, fileName).toString();
        this.strategy = strategy;
    }

    public void write(List<?> contents) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException {

        if (contents.isEmpty()) {
            System.err.println("Content is empty. ABORTING");
            return;
        }
        strategy.write(finalPath, contents);

    }

}
