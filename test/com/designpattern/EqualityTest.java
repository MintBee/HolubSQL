package com.designpattern;
import com.designpattern.model.Product;
import com.designpattern.model.Stock;
import com.designpattern.model.UndecayingStock;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class EqualityTest {
    private int getRandomInt(int min, int max){
        int result = (int) (Math.random() * (max - min) + min);
        return result;
    }
    private String getRandomString(){
        return UUID.randomUUID().toString();
    }

    @Test
    void StockEqualityTest(){
        String productName = getRandomString();
        Stock stock_01 = new UndecayingStock(productName);
        Stock stock_02 = new UndecayingStock(productName);

        assertThat(stock_01.equals(stock_02))
                .isFalse();
    }

    @Test
    void ProductEqualityTest(){
        String productName = getRandomString();
        int price_01 = getRandomInt(1, 500);
        int price_02 = getRandomInt(501, 1000);
        Product standard = new Product(productName, price_01);
        Product comp = new Product(productName, price_02);
        assertThat(standard.equals(comp))
                .isTrue();
    }
}