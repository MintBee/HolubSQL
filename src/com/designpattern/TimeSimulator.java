package com.designpattern;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simulator for time by day
 * Run certain task per set seconds after call simulate method
 */
public class TimeSimulator implements AppTime {
    private LocalDate currentDate = LocalDate.now();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final int secondsTakeForTomorrow;
    private final Runnable periodicTask;

    /**
     * Set periodic task to run per set seconds
     * @param periodicTask () -> System.out.prinlin("hi") then, print "hi" per secondsTakeForTomorrow.
     */
    public TimeSimulator(int secondsTakeForTomorrow, Runnable periodicTask) {
        this.secondsTakeForTomorrow = secondsTakeForTomorrow;
        this.periodicTask = periodicTask;
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
                    periodicTask.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
