package com.designpattern;

import java.time.LocalDate;

public interface InputBoundary {
    void addNewItem(String name, int price);
    void addStock(String name, int count);
    void addStock(String name, int count, LocalDate expirationDate);
    void removeStock(String name, int count);
    void requestItemInfo(String name);
}
