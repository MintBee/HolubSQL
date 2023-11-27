package com.designpattern.model;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.repository.ProductRepository;
import com.designpattern.repository.StockRepository;

import java.sql.SQLException;

public class DbInsertVisitor implements ModelVisitor {
    private final static ProductRepository productRepository = new ProductRepository();
    private final static StockRepository stockRepository = new StockRepository();

    @Override
    public void visit(Product product) {
        try {
            productRepository.save(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visit(Stock stock) {
        try {
            stockRepository.save(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
