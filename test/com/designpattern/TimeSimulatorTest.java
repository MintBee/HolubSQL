package com.designpattern;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TimeSimulatorTest {
    TimeSimulator timeSimulator = new TimeSimulator(1);

    @Disabled
    @Test
    void simulate() throws InterruptedException {
        timeSimulator.simulate();
        Thread.sleep(900000);
    }
}