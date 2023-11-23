package com.designpattern;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConsistentDispatcher implements AppTime {
    private LocalDate currentDate = LocalDate.now();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private int secondsTakeForTomorrow;
    private Runnable task;

    public ConsistentDispatcher(int secondsTakeForTomorrow, Runnable task) {
        this.secondsTakeForTomorrow = secondsTakeForTomorrow;
        this.task = task;
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
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
