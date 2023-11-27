package com.designpattern.io;
//
//import java.io.*;
//
//public class TableImporter {
////    public static void main(String[] args) {
////        importTable("sdlfks.csv");
////
////    }
//
//}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StockCSVImporter {
    private String item;
    private int count;
    private int cost;
    private String expDate;
    private String fileName;
    public void StockCSVImporter(String fileName) {
        this.fileName = fileName;
    }
    public void CSVParser() {
        //fileName = "Dbase/example.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {

                String[] fields = line.split(",");

                item = fields[0];
                count = Integer.parseInt(fields[1]);
                cost = Integer.parseInt(fields[2]);
                if (fields.length > 3)
                    expDate = fields[3];
                else
                    expDate = null;

                //StockImporter();
                TestImporter();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void StockImporter() {
//        if (requestItemInfo(item) == false) {
//            addNewItem(item, cost);
//        }
//        if (expDate == null)
//            addStock(item, count);
//        else {
//            addStock(item, count, expDate);
//        }
//    }
    public void TestImporter() {
        System.out.println(item +", "+count+", "+cost+", "+expDate);
    }
}


















