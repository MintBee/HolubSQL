package com.designpattern;

import com.designpattern.io.InputBoundary;
import com.designpattern.io.ItemDto;
import com.designpattern.io.OutputBoundary;
import com.designpattern.io.StockDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class TestController implements InputBoundary {
    private final List<ItemDto> items = new ArrayList<>();
    private final List<StockDto> stocks = new ArrayList<>();
    private final OutputBoundary outputBoundary;

    public TestController(OutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void addNewProduct(String productName, int price) {
        items.add(new ItemDto(productName, price));
    }

    @Override
    public void removeProduct(String productName) {
        items.remove(0);
    }

    @Override
    public void addStock(String productName, int count) {
        ItemDto item = items.stream().filter(it -> it.getName().equals(productName))
            .findFirst()
            .orElseThrow(NoSuchElementException::new);
        for (int i = 0; i < count; i++) {
            stocks.add(new StockDto(productName, item.getPrice()));
        }
    }

    @Override
    public void addStock(String productName, int count, LocalDate expirationDate) {
        ItemDto item = items.stream().filter(it -> it.getName().equals(productName))
            .findFirst()
            .orElseThrow(NoSuchElementException::new);

        for (int i = 0; i < count; i++) {
            stocks.add(new StockDto(productName, item.getPrice(), expirationDate));
        }
    }

    @Override
    public void sellStock(String productName) {

    }

    @Override
    public void requestProductInfo(String productName) {
        ItemDto itemDto = items.stream().filter(it -> it.getName().equals(productName)).findFirst()
            .orElseThrow(NoSuchElementException::new);
        outputBoundary.outPutItemInfo(itemDto);
        stocks.stream().filter((it) -> it.getName().equals(itemDto.getName()))
            .forEach(outputBoundary::outputStockInfo);
    }
}
