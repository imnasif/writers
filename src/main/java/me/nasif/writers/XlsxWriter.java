package me.nasif.writers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxWriter implements FileWritable {

    private XSSFSheet sheet;
    private int columnCount;
    private int rowCount;

    @Override
    public void write(String fileNameWithPath, List<?> contents) {

        try {
            FileOutputStream outputStream = new FileOutputStream(fileNameWithPath + ".xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook();
            sheet = workbook.createSheet();

            rowCount = 1;
            Object contentStructure = contents.get(0);
            columnCount = contentStructure.getClass().getFields().length;

            createHeader(sheet, contentStructure, columnCount);

            contents.stream().forEach((content) -> {
                createRow(content);
            });
            workbook.write(outputStream);

            System.out.println(fileNameWithPath + " is saved to disk");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(XlsxWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XlsxWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createHeader(XSSFSheet sheet, Object contentStructure, int columnCount) {
        Field[] headerCells = contentStructure.getClass().getFields();
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            Field column = headerCells[i];
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(column.getName());
        }

    }

    private void createRow(Object content) {
        Field[] cellValues = content.getClass().getFields();
        Row row = sheet.createRow(rowCount++);
        for (int i = 0; i < columnCount; i++) {
            Field cellvalue = cellValues[i];
            Cell cell = row.createCell(i);
            try {
                if (cellvalue.get(content) != null) {
                    cell.setCellValue(cellvalue.get(content).toString());
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(XlsxWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
