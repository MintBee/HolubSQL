package com.designpattern;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private final Map<Product, List<Stock>> inven = new ConcurrentHashMap<>();
    DbDeleteVisitor deleteVisitor = new DbDeleteVisitor();
    DbInsertVisitor insertVisitor = new DbInsertVisitor();

    public List<Stock> addStock(String productName, int count){
        Product keyProduct = new Product(productName, 0);
        List<Stock> stocks = inven.get(keyProduct);
        if (stocks == null) {
            throw new NoSuchProductException();
        }
        for (int i = 0; i < count; i++) {
            Stock newStock = new UndecayingStock(productName);
            inven.get(keyProduct).add(newStock);
            newStock.accept(insertVisitor);
        }
        return stocks;
    }

    public void addStock(String productName, int count, LocalDate expDate){
        Product keyProduct = new Product(productName, 0);
        List<Stock> stocks = inven.get(keyProduct);
        if (stocks == null) {
            throw new NoSuchProductException();
        }
        for (int i = 0; i < count; i++) {
            Stock newStock = new DecayingStock(productName, expDate);
            inven.get(keyProduct).add(newStock);
            newStock.accept(insertVisitor);
        }
    }

    public void removeStock(Stock stock) {
        List<Stock> stocks = inven.get(new Product(stock.getProductName(), 0));
        stocks.remove(stock);
        stock.accept(deleteVisitor);
    }

    public void addProduct(String productName, int price){
        Product newProduct = new Product(productName, price);
        List<Stock> stockList = new ArrayList<>();
        inven.put(newProduct, stockList);
        newProduct.accept(insertVisitor);
    }

    public int getProductsSize() {
        return inven.size();
    }

    public void removeProduct(String productName){
        Product keyProduct = new Product(productName, 0);
        keyProduct.accept(deleteVisitor);
        inven.remove(keyProduct);
    }

    public Product findProduct(String name){
        Product comp = new Product(name, 0);

        return inven.keySet().stream()
                .filter(stocks -> stocks.equals(comp))
                .findFirst()
                .orElseThrow(NoSuchProductException::new);
    }

    public void sell(String productName, int count) {
        for (int i = 0; i < count ; i++) {
            sell(productName);
        }
    }

    private void sell(String productName) {
        synchronized (inven) {
            Product comp = new Product(productName, 0);
            List<Stock> productStocks = inven.getOrDefault(comp, new ArrayList<>());
            Stock stockToSell = productStocks
                .stream().findFirst()
                .orElseThrow(NoSuchProductException::new);
            productStocks.remove(stockToSell);
            stockToSell.accept(new DbDeleteVisitor());
        }
    }
}
