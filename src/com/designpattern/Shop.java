package com.designpattern;

import com.designpattern.io.InputBoundary;
import com.designpattern.io.OutputBoundary;

import java.time.LocalDate;

public class Shop implements InputBoundary {
    private final OutputBoundary outputBoundary;

    public Shop(OutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    //todo implement Shop
    @Override
    public void addNewProduct(String productName, int price) {

    }

    @Override
    public void removeProduct(String productName) {

    }

    @Override
    public void addStock(String productName, int count) {

    }

    @Override
    public void addStock(String productName, int count, LocalDate expirationDate) {

    }

    @Override
    public void sellStock(String productName) {

    }

    @Override
    public void sellStocks(String productName, int count) {

    }

    @Override
    public void requestProductInfo(String productName) {

    }
}
