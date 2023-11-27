package com.designpattern;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    final Map<Product, List<Stock>> inven = new ConcurrentHashMap<>();
    DbDeleteVisitor deleteVisitor = new DbDeleteVisitor();
    DbInsertVisitor insertVisitor = new DbInsertVisitor();


    public void addStock(String name, int count) {
        Product comp = new Product(name, 0);
        while (iter.hasNext()) {
            Map.Entry<Product, List<Stock>> entry = iter.next();
            if (entry.getKey().equals(comp)) {
                for (int i = 0; i < count; i++) {
                    Stock newStock = new UndecayingStock(name);
                    entry.getValue().add(newStock);
                    newStock.accept(insertVisitor);
                }
            }
        }
    }

    public void addStock(String name, int count, LocalDate expDate) {
        Product comp = new Product(name, 0);
        while (iter.hasNext()) {
            Map.Entry<Product, List<Stock>> entry = iter.next();
            if (entry.getKey().equals(comp)) {
                Stock newStock = new DecayingStock(name, expDate);
                entry.getValue().add(newStock);
                newStock.accept(insertVisitor);
                Collections.sort(entry.getValue(), new Comparator<Stock>() {
                    @Override
                    public int compare(Stock o1, Stock o2) {
                        return o1.getExpirationDate().compareTo(o2.getExpirationDate());
                    }
                });
            }
        }
    }


    public void addProduct(String name, long price) {
        Product newProduct = new Product(name, price);
        List<Stock> stockList = new ArrayList<Stock>();
        inven.put(newProduct, stockList);
        newProduct.accept(insertVisitor);
    }

    public void removeProduct(String name) {
        Product comp = new Product(name, 0);
        while (iter.hasNext()) {
            Map.Entry<Product, List<Stock>> entry = iter.next();
            if (entry.getKey().equals(comp)) {
                entry.getKey().accept(deleteVisitor);
                iter.remove();
            }
        }

    }

    public Product findProduct(String name) {
        Product comp = new Product(name, 0);
        return inven.keySet().stream()
            .filter(it -> it.equals(comp))
            .findFirst()
            .orElseThrow(NoSuchProductException::new);
    }

    public List<Stock> findAllStocksByProduct(String productName) {
        Product comp = new Product(productName, 0);
        return inven.getOrDefault(comp, new ArrayList<>());
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
