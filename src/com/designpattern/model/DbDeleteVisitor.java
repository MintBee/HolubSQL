package com.designpattern.model;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.repository.ProductRepository;
import com.designpattern.repository.StockRepository;

import java.sql.SQLException;

public class DbDeleteVisitor implements ModelVisitor {
    private static final ProductRepository productRepository = new ProductRepository();
    private static final StockRepository stockRepository = new StockRepository();

    @Override
    public void visit(Product product) {
        try {
            stockRepository.deleteByProduct(product.getName());
            productRepository.deleteByName(product.getName());
        } catch (SQLException e) {
            throw new NoSuchProductException(e);
        }
    }

    @Override
    public void visit(Stock stock) {
        stockRepository.delete(stock.getId());
    }
}
