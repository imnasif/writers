package me.nasif.writers;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenCsvWriter implements FileWritable {

    @Override
    public void write(String finalPath, List<?> contents) throws IOException, IllegalArgumentException, IllegalAccessException {

        try {
            try (java.io.FileWriter fileWriter = new java.io.FileWriter(finalPath + ".csv")) {
                CSVWriter csvWriter = new CSVWriter(fileWriter, ',');
                
                csvWriter.writeNext(getHeaders(contents.get(0)));
                
                for (Object content : contents) {
                    String[] valuesAsString = getStringListFromContent(content);
                    csvWriter.writeNext(valuesAsString);
                }
                csvWriter.close();
            }
        } catch (IOException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(OpenCsvWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }

    private String[] getStringListFromContent(Object content) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = content.getClass().getDeclaredFields();
        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            if (field.get(content) != null) {
                values.add(field.get(content).toString());
            } else {
                values.add("");
            }
        }

        return values.toArray(new String[values.size()]);
    }

    private String[] getHeaders(Object content) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = content.getClass().getDeclaredFields();
        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            if (field.getName() != null) {
                values.add(field.getName());
            } else {
                values.add("");
                System.err.println("INVALID HEADER");
            }
        }

        return values.toArray(new String[values.size()]);
    }
}
