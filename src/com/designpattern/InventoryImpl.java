package com.designpattern;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.exception.OutOfStockException;
import com.designpattern.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryImpl implements Inventory {

    private static Inventory instance = new InventoryImpl();
    private final Map<Product, List<Stock>> inven = new ConcurrentHashMap<>();
    private final DbDeleteVisitor deleteVisitor = new DbDeleteVisitor();
    private final DbInsertVisitor insertVisitor = new DbInsertVisitor();

    private InventoryImpl() {
        for (Product product : DbModelFactory.getAllProducts()) {
            List<Stock> stocks = DbModelFactory.getAllStocksByProduct(product.getName());
            inven.put(product, stocks);
        }
    }

    public static Inventory getInstance() {
        return instance;
    }

    @Override
    public synchronized List<Stock> addStock(String productName, int count){
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

    @Override
    public synchronized void addStock(String productName, int count, LocalDate expDate){
        Product keyProduct = findProduct(productName);
        List<Stock> stocks = inven.get(keyProduct);
        if (stocks == null) {
            throw new NoSuchProductException();
        }
        for (int i = 0; i < count; i++) {
            Stock newStock = new DecayingStock(productName, expDate);
            inven.get(keyProduct).add(newStock);
            inven.get(keyProduct).sort(new Comparator<Stock>(){
                public int compare(Stock o1, Stock o2){
                    return o1.getExpirationDate().compareTo(o2.getExpirationDate());
                }
            });
            newStock.accept(insertVisitor);
        }
    }

    @Override
    public synchronized void deleteStock(Stock stock) {
        List<Stock> stocks = inven.get(new Product(stock.getProductName(), 0));
        stocks.remove(stock);
        stock.accept(deleteVisitor);
    }

    @Override
    public List<Stock> getAllStocks() {
        //generate flatten all list of stock into one list
        return inven.values().stream().flatMap(List::stream).toList();
    }

    @Override
    public synchronized void addProduct(String productName, int price){
        Product newProduct = new Product(productName, price);
        List<Stock> stockList = new ArrayList<>();
        inven.put(newProduct, stockList);
        newProduct.accept(insertVisitor);
    }

    @Override
    public int getProductsQuantity(String productName) {
        Product keyProduct = new Product(productName, 0);
        if (inven.get(keyProduct) != null) {
            return inven.get(keyProduct).size();
        } else {
            return 0;
        }
    }

    @Override
    public synchronized void deleteProduct(String productName){
        Product keyProduct = findProduct(productName);

        inven.remove(keyProduct);
        keyProduct.accept(deleteVisitor);
    }

    /**
     * @exception NoSuchProductException if there is no product with the given name
     */
    @Override
    public Product findProduct(String name){
        Product comp = new Product(name, 0);

        return inven.keySet().stream()
                .filter(stocks -> stocks.equals(comp))
                .findFirst()
                .orElseThrow(NoSuchProductException::new);
    }

    @Override
    public synchronized void sell(String productName, int count) {
        Product keyProduct = findProduct(productName);
        for (int i = 0; i < count ; i++) {
            sell(keyProduct);
        }

    }

    private synchronized void sell(Product target) {
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
