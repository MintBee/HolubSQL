package com.designpattern;

import java.time.LocalDate;

public class Controller implements InputBoundary {
    @Override
    public void addNewItem(String name, int price) {

    }

    @Override
    public void addStock(String name, int count) {

    }

    @Override
    public void addStock(String name, int count, LocalDate expirationDate) {

    }

    @Override
    public void removeStock(String name, int count) {

    }

    @Override
    public void requestItemInfo(String name) {

    }
}
