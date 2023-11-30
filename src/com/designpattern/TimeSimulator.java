package com.designpattern;

import java.time.LocalDate;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Simulator for time by day
 * Run certain task per set seconds after call simulate method
 */
public class TimeSimulator extends Observable implements AppTime {
    private LocalDate currentDate = LocalDate.now();
    private final int secondsTakeForTomorrow;

    /**
     * Set periodic task to run per set seconds
     */
    public TimeSimulator(int millisecondsTakeForTomorrow) {
        this.secondsTakeForTomorrow = millisecondsTakeForTomorrow;
    }

    @Override
    public synchronized void becomeTomorrow() {
        currentDate = currentDate.plusDays(1);
    }

    @Override
    public synchronized LocalDate now() {
        return currentDate;
    }

    public synchronized void simulate() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(secondsTakeForTomorrow);
                becomeTomorrow();
                setChanged();
                notifyObservers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
