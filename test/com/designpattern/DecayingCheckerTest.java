package com.designpattern;

import com.designpattern.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryForTest implements Inventory {
    List<Stock> stocks = new ArrayList<Stock>();

    @Override
    public List<Stock> addStock(String productName, int count) {
        return null;
    }

    @Override
    public void addStock(String productName, int count, LocalDate expDate) {
        stocks.add(new DecayingStock(productName, expDate));
    }

    @Override
    public void deleteStock(Stock stock) {
        stocks.remove(stock);
        System.out.println("유통기한 지난 상품 삭제되었습니다.");
    }

    @Override
    public List<Stock> getAllStocks() {
        return stocks;
    }

    @Override
    public void addProduct(String productName, int price) {

    }

    @Override
    public int getProductsQuantity(String productName) {
        return stocks.size();
    }

    @Override
    public void deleteProduct(String productName) {

    }

    @Override
    public Product findProduct(String name) {
        return null;
    }

    @Override
    public void sell(String productName, int count) {

    }
}

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DecayingCheckerTest {
    final String testProductName = "test product name";
    @Test
    @Order(100)
    public void testDecayingChecker(){
        //given
        Inventory inventory = new InventoryForTest();
        inventory.addProduct(testProductName, 12345);
        inventory.addStock(testProductName, 1, LocalDate.of(2023, 11, 30));
        inventory.addStock(testProductName, 1, LocalDate.of(2023, 12, 1));
        inventory.addStock(testProductName, 1, LocalDate.of(2023, 12, 2));

        TimeSimulator timeSimulator = new TimeSimulator(1000);
        DecayingChecker decayingChecker = new DecayingChecker(
                inventory,
                timeSimulator
        );

        timeSimulator.simulate();

        try {
            TimeUnit.MILLISECONDS.sleep(10000);
            System.out.println("asdfio");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("여긴가?");
        }

        //when
        int productsQuantity = inventory.getProductsQuantity(testProductName);

        // then
        assertThat(productsQuantity).isZero();
    }
}
