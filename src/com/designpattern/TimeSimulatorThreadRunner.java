package com.designpattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeSimulatorThreadRunner {
    private final int millisecondsTakeForTomorrow;
    private final Inventory inventory;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public TimeSimulatorThreadRunner(Inventory inventory, int millisecondsTakeForTomorrow) {
        this.inventory = inventory;
        this.millisecondsTakeForTomorrow = millisecondsTakeForTomorrow;
    }

    public void run() {
        executorService.submit(() -> {
            TimeSimulator timeSimulator = new TimeSimulator(millisecondsTakeForTomorrow);
            DecayingChecker decayingChecker = new DecayingChecker(inventory);
            timeSimulator.addObserver(decayingChecker);
            timeSimulator.simulate();
        });
    }
}
