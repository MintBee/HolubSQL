package com.designpattern;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.model.*;

import java.time.LocalDate;
import java.util.*;

public class Inventory {
    HashMap<Product, List<Stock>> inven;
    DbDeleteVisitor deleteVisitor = new DbDeleteVisitor();
    DbInsertVisitor insertVisitor = new DbInsertVisitor();


    public void addStock(String name, int count){
        Product keyProduct = new Product(name, 0);
        Stock newStock = new UndecayingStock(name);
        inven.get(keyProduct).add(newStock);
        newStock.accept(insertVisitor);
    }

    public void addStock(String name, int count, LocalDate expDate){
        Product keyProduct = new Product(name, 0);
        Stock newStock = new DecayingStock(name, expDate);
        inven.get(keyProduct).add(newStock);
        newStock.accept(insertVisitor);
        Collections.sort(inven.get(keyProduct), new Comparator<Stock>() {
            @Override
            public int compare(Stock o1, Stock o2) {
                return o1.getExpirationDate().compareTo(o2.getExpirationDate());
            }
        });
    }



    public void addProduct(String name, long price){
        Product newProduct = new Product(name, price);
        List<Stock> stockList = new ArrayList<Stock>();
        inven.put(newProduct, stockList);
        newProduct.accept(insertVisitor);
    }

    public void removeProduct(String name){
        Product keyProduct = new Product(name, 0);
        keyProduct.accept(deleteVisitor);
        inven.remove(keyProduct);
    }

    public Product findProduct(String name){
        Product comp = new Product(name, 0);
        Product keyProduct = inven.entrySet().stream()
                .filter(entry->entry.getKey().equals(comp))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(()->new NoSuchProductException());

        return keyProduct;
    }

    public void sell(String name, int count){
        Product keyProduct = new Product(name, 0);
        List<Stock> stocks = inven.get(keyProduct);

        for(int i =0; i<stocks.size(); i++){
            stocks.get(i).accept(deleteVisitor);
            stocks.remove(i);
        }
    }
}
