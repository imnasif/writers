package me.nasif.writers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CommonCsvWriter implements FileWritable {

    private static final String NEW_LINE_SEPARATOR = "\n";

    java.io.FileWriter fileWriter = null;
    CSVPrinter csvFilePrinter = null;
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

    @Override
    public void write(String finalPath, List<?> contents) throws IOException, IllegalArgumentException, IllegalAccessException {

        try {
            fileWriter = new java.io.FileWriter(finalPath + ".csv");
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            csvFilePrinter.printRecord(getHeaders(contents.get(0)));

            for (Object content : contents) {
                csvFilePrinter.printRecord(getStringListFromContent(content));
            }
            fileWriter.close();
        } catch (IOException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(CommonCsvWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }

    private List<String> getStringListFromContent(Object content) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = content.getClass().getFields();
        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            if (field.get(content) == null) {
                values.add("");
            } else {
                values.add(field.get(content).toString());
            }
        }

        return values;
    }

    private List<String> getHeaders(Object content) throws IllegalArgumentException, IllegalAccessException {

        Field[] fields = content.getClass().getFields();
        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            if (field.getName() == null) {
                values.add("");
                System.err.println("INVALID HEADER");
            } else {
                values.add(field.getName());
            }
        }
        return values;
    }

}
