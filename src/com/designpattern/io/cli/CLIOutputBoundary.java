package com.designpattern.io.cli;

import com.designpattern.io.ItemDto;
import com.designpattern.io.OutputBoundary;
import com.designpattern.io.StockDto;

import java.time.format.DateTimeFormatter;

public class CLIOutputBoundary implements OutputBoundary {
    @Override
    public void outPutItemInfo(ItemDto item) {
        String name = item.getName();
        String price = Integer.toString(item.getPrice());
        String quantity = Integer.toString(item.getQuantity());

        System.out.println();
        System.out.println("상품 정보");
        System.out.println("품명: " + name);
        System.out.println("가격: " + price + "원");
        System.out.println("수량: " + quantity);
        System.out.println();
    }

    @Override
    public void outputStockInfo(StockDto stock) {
        String name = stock.getName();
        String price = Integer.toString(stock.getPrice());
        if (stock.expirationDate() == null) {
            System.out.println("품명: " + name + " | " + "가격: " + price);
            return;
        }
        String expirationDate = stock.expirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println("품명: " + name + " | " + "가격: " + price + " | " + "유통 기한: " + expirationDate);
    }
}
