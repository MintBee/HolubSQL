package com.designpattern;

import com.designpattern.model.DecayingStock;
import com.designpattern.model.Stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DecayingChecker implements Observer {
    private LocalDate now;
    private final Inventory inventory;

    public DecayingChecker(
            Inventory inventory
    ) {
        this.inventory = inventory;
    }

    private synchronized void checkDecayingStocks(){
        List<Stock> decayingStocks = inventory.getAllStocks();

        List<DecayingStock> expiredStocks = new ArrayList<>();

        for (Stock stock : decayingStocks) {
            if(stock instanceof DecayingStock decayingStock){
                if(decayingStock.getExpirationDate().isBefore(now)){
                    expiredStocks.add(decayingStock);
                }
            }
        }

        for (DecayingStock expiredStock : expiredStocks) {
            expiredStock.setIsDecayed(true);
            inventory.deleteStock(expiredStock);
        }

        if (!expiredStocks.isEmpty()) {
            System.out.println("인벤토리에서 유통기한이 지난 제품을 " + expiredStocks.size() + "개 삭제했습니다." );
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        now = ((TimeSimulator) o).now();
        System.out.println("now : "+now);
        checkDecayingStocks();
    }
}
