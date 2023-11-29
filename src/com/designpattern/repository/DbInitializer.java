package com.designpattern.repository;

public class DbInitializer {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        StockRepository stockRepository = new StockRepository();

        productRepository.createTable();
        stockRepository.createTable();
    }
}
