package com.designpattern;

import com.designpattern.model.Product;
import com.designpattern.model.Stock;

import java.time.LocalDate;
import java.util.List;

public interface Inventory {
    List<Stock> addStock(String productName, int count);

    void addStock(String productName, int count, LocalDate expDate);

    void deleteStock(Stock stock);

    List<Stock> getAllStocks();

    void addProduct(String productName, int price);

    int getProductsQuantity(String productName);

    void deleteProduct(String productName);

    Product findProduct(String name);

    void sell(String productName, int count);
}
