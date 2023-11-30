package com.designpattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DecayingCheckerTest {
    final String testProductName = "test product name";
    @Test
    @Order(100)
    public void testDecayingChecker(){
        //given
        Inventory inventory = InventoryForTest.getInstance();
        inventory.addProduct(testProductName, 12345);
        inventory.addStock(testProductName, 1, LocalDate.of(2023, 11, 30));
        inventory.addStock(testProductName, 1, LocalDate.of(2023, 12, 1));
        inventory.addStock(testProductName, 1, LocalDate.of(2023, 12, 2));
        inventory.addStock(testProductName, 1, LocalDate.of(2999, 12, 2));

        TimeSimulatorThreadRunner timeSimulatorThreadRunner = new TimeSimulatorThreadRunner(inventory, 100);

        //when
        timeSimulatorThreadRunner.run();

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // then
        int productsQuantity = inventory.getProductsQuantity(testProductName);
        assertThat(productsQuantity).isEqualTo(1);
    }
}
