package com.designpattern;

import java.time.LocalDate;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simulator for time by day
 * Run certain task per set seconds after call simulate method
 */
public class TimeSimulator extends Observable implements AppTime {
    private LocalDate currentDate = LocalDate.now();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final int secondsTakeForTomorrow;

    /**
     * Set periodic task to run per set seconds
     *
     */
    public TimeSimulator(int secondsTakeForTomorrow) {
        this.secondsTakeForTomorrow = secondsTakeForTomorrow;
    }

    @Override
    public void becomeTomorrow() {
        currentDate = currentDate.plusDays(1);
    }

    @Override
    public LocalDate now() {
        return currentDate;
    }

    public void simulate() {
        executorService.submit(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(secondsTakeForTomorrow);
                    becomeTomorrow();
                    notifyObservers();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
