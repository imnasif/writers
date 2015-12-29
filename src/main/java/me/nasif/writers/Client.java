package me.nasif.writers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.nasif.writers.model.Chocolate;

public class Client {

    static FileWriter writer;
    static List<Chocolate> dataList;

    public static void main(String[] args) throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {

        dataList = getDummyData();

        writer = new FileWriter("export", "Written With OpenCsv", new OpenCsvWriter());
        writer.write(dataList);

        writer = new FileWriter("export", "Written With CommonCsv", new CommonCsvWriter());
        writer.write(dataList);

        writer = new FileWriter("export", "Written With ApachePoi", new XlsxWriter());
        writer.write(dataList);
    }

    private static List<Chocolate> getDummyData() {
        List<Chocolate> dummyDataList = new ArrayList();

        dummyDataList.add(new Chocolate("Mars", 10));
        dummyDataList.add(new Chocolate("Perk", 20));
        dummyDataList.add(new Chocolate("Cadbury", 10));

        return dummyDataList;

    }
}
