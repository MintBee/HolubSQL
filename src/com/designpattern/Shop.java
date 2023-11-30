package com.designpattern;

import com.designpattern.io.InputBoundary;
import com.designpattern.io.OutputBoundary;
import com.designpattern.io.ProductDto;
import com.designpattern.io.StockDto;
import com.designpattern.model.Product;

import java.time.LocalDate;

public class Shop implements InputBoundary {
    private final OutputBoundary outputBoundary;
    private final Inventory inventory;

    public Shop(Inventory inventory, OutputBoundary outputBoundary) {
        this.inventory = inventory;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addNewProduct(String productName, int price) {
        inventory.addProduct(productName, price);
        outputBoundary.outputProduct(new ProductDto(productName, price, inventory.getProductsQuantity(productName)));
    }

    @Override
    public void deleteProduct(String productName) {
        Product product = inventory.findProduct(productName);
        inventory.deleteProduct(productName);
        outputBoundary.outputProduct(new ProductDto(product.getName(), product.getPrice(), inventory.getProductsQuantity(productName)));
    }

    @Override
    public void addStock(String productName, int count) {
        inventory.addStock(productName, count);
        Product product = inventory.findProduct(productName);
        outputBoundary.outputStock(new StockDto(product.getName(), product.getPrice()));
    }

    @Override
    public void addStock(String productName, int count, LocalDate expirationDate) {
        inventory.addStock(productName, count, expirationDate);
        Product product = inventory.findProduct(productName);
        outputBoundary.outputStock(new StockDto(product.getName(), product.getPrice(), expirationDate));
    }

    @Override
    public void sellStock(String productName) {
        inventory.sell(productName, 1);
    }

    @Override
    public void sellStocks(String productName, int count) {
        inventory.sell(productName, count);
    }

    @Override
    public void requestProduct(String productName) {
        Product product = inventory.findProduct(productName);
        outputBoundary.outputProduct(new ProductDto(product.getName(), product.getPrice(), inventory.getProductsQuantity(product.getName())));
    }
}
