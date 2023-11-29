package com.designpattern;

import com.designpattern.exception.NoSuchProductException;
import com.designpattern.exception.OutOfStockException;
import com.designpattern.io.InputBoundary;
import com.designpattern.io.OutputBoundary;
import com.designpattern.io.ProductDto;
import com.designpattern.io.StockDto;

import java.time.LocalDate;
import java.util.*;

public class TestController implements InputBoundary {
    private final List<ProductDto> products = new ArrayList<>();
    private final List<StockDto> stocks = new ArrayList<>();
    private final OutputBoundary outputBoundary;

    public TestController(OutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addNewProduct(String productName, int price) {
        products.add(new ProductDto(productName, price, 0));
    }

    @Override
    public void removeProduct(String productName) {
        products.remove(0);
    }

    @Override
    public void addStock(String productName, int count) {
        ProductDto item = products.stream().filter(it -> it.getName().equals(productName))
            .findFirst()
            .orElseThrow(NoSuchProductException::new);
        for (int i = 0; i < count; i++) {
            stocks.add(new StockDto(productName, item.getPrice()));
        }
    }

    @Override
    public void addStock(String productName, int count, LocalDate expirationDate) {
        ProductDto product = products.stream().filter(pr -> pr.getName().equals(productName))
            .findFirst()
            .orElseThrow(NoSuchProductException::new);

        for (int i = 0; i < count; i++) {
            stocks.add(new StockDto(productName, product.getPrice(), expirationDate));
        }
    }

    @Override
    public void sellStock(String productName) {
        ProductDto product = products.stream().filter(pr -> pr.getName().equals(productName))
                .findFirst()
                .orElseThrow(NoSuchProductException::new);

        Optional<StockDto> stockToSell = stocks.stream().filter(st -> st.getName().equals(product.getName())).findFirst();
        if (stockToSell.isEmpty()) throw new OutOfStockException();
        stocks.remove(stockToSell);
    }

    @Override
    public void sellStocks(String productName, int count) {
        ProductDto product = products.stream().filter(pr -> pr.getName().equals(productName))
                .findFirst()
                .orElseThrow(NoSuchProductException::new);

        for (int i = 0; i < count; i++) {
            Optional<StockDto> stockToSell = stocks.stream().filter(st -> st.getName().equals(product.getName())).findFirst();
            if (stockToSell.isEmpty()) throw new OutOfStockException();
            stocks.remove(stockToSell);
        }
    }

    @Override
    public void requestProduct(String productName) {
        ProductDto productDto = products.stream().filter(pr -> pr.getName().equals(productName)).findFirst()
            .orElseThrow(NoSuchProductException::new);
        outputBoundary.outputProduct(productDto);
        stocks.stream().filter((pr) -> pr.getName().equals(productDto.getName()))
            .forEach(outputBoundary::outputStock);
    }
}
