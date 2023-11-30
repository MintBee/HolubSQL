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
    public synchronized void becomeTomorrow() {
        currentDate = currentDate.plusDays(1);
    }

    @Override
    public synchronized LocalDate now() {
        return currentDate;
    }

    public synchronized void simulate() {
        executorService.submit(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(secondsTakeForTomorrow);
                    System.out.println("하루가 지남");
                    becomeTomorrow();
                    setChanged();
                    notifyObservers();
                } catch (InterruptedException e) {
                    System.out.println("하루가 지남");
//                    e.printStackTrace();
                    System.out.println("ㅁㄴㅇ롬내ㅑㄹㄴ매ㅑ런매ㅑㄹ");
                }
            }
        });
    }
}
