package com.designpattern;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsistentDispatcherTest {
    ConsistentDispatcher consistentDispatcher = new ConsistentDispatcher(1, () -> System.out.println("hi!"));

    @Test
    void simulate() throws InterruptedException {
        consistentDispatcher.simulate();
        Thread.sleep(900000);
    }
}