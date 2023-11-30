package com.designpattern;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.exception.OutOfStockException;
import com.designpattern.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private final Map<Product, List<Stock>> inven = new ConcurrentHashMap<>();
    private final DbDeleteVisitor deleteVisitor = new DbDeleteVisitor();
    private final DbInsertVisitor insertVisitor = new DbInsertVisitor();

    public Inventory() {
        for (Product product : DbModelFactory.getAllProducts()) {
            List<Stock> stocks = DbModelFactory.getAllStocksByProduct(product.getName());
            inven.put(product, stocks);
        }
    }

    public List<Stock> addStock(String productName, int count){
        Product keyProduct = findProduct(productName);
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
        Product keyProduct = findProduct(productName);
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

    public int getProductsQuantity(String productName) {
        Product keyProduct = new Product(productName, 0);
        if (inven.get(keyProduct) != null) {
            return inven.get(keyProduct).size();
        } else {
            return 0;
        }
    }

    public void deleteProduct(String productName){
        Product keyProduct = findProduct(productName);
        keyProduct.accept(deleteVisitor);
        inven.remove(keyProduct);
    }

    /**
     * @exception NoSuchProductException if there is no product with the given name
     */
    public Product findProduct(String name){
        Product comp = new Product(name, 0);

        return inven.keySet().stream()
                .filter(stocks -> stocks.equals(comp))
                .findFirst()
                .orElseThrow(NoSuchProductException::new);
    }

    public void sell(String productName, int count) {
        Product keyProduct = findProduct(productName);

        for (int i = 0; i < count ; i++) {
            sell(keyProduct);
        }

    }

    private void sell(Product target) {
        synchronized (inven) {
            List<Stock> productStocks = inven.getOrDefault(target, new ArrayList<>());
            Stock stockToSell = productStocks
                .stream().findFirst()
                .orElseThrow(OutOfStockException::new);
            productStocks.remove(stockToSell);
            stockToSell.accept(deleteVisitor);
        }
    }
}
