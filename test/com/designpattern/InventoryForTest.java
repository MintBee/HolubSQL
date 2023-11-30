package com.designpattern;

import com.designpattern.model.DecayingStock;
import com.designpattern.model.Product;
import com.designpattern.model.Stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryForTest implements Inventory {
    private static final InventoryForTest instance = new InventoryForTest();
    public static InventoryForTest getInstance() {return instance;}

    private InventoryForTest() {}

    List<Stock> stocks = new ArrayList<Stock>();

    @Override
    public List<Stock> addStock(String productName, int count) {
        return null;
    }

    @Override
    public void addStock(String productName, int count, LocalDate expDate) {
        stocks.add(new DecayingStock(productName, expDate));
    }

    @Override
    public void deleteStock(Stock stock) {
        stocks.remove(stock);
        System.out.println("유통기한 지난 상품 삭제되었습니다.");
    }

    @Override
    public List<Stock> getAllStocks() {
        return stocks;
    }

    @Override
    public void addProduct(String productName, int price) {

    }

    @Override
    public int getProductsQuantity(String productName) {
        return stocks.size();
    }

    @Override
    public void deleteProduct(String productName) {

    }

    @Override
    public Product findProduct(String name) {
        return null;
    }

    @Override
    public void sell(String productName, int count) {

    }
}
