package com.designpattern;

import com.designpattern.model.DbModelFactory;
import com.designpattern.model.DecayingStock;
import com.designpattern.model.Stock;

import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DecayingChecker implements Observer {
    private LocalDate now;
    private final Inventory inventory;

    public DecayingChecker(
            Inventory inventory,
            Observable observable
    ) {
        observable.addObserver(this);
        this.inventory = inventory;
    }

    private synchronized void checkDecayingStocks(){
        List<Stock> decayingStocks = inventory.getAllStocks();

        for (Stock stock : decayingStocks) {
            System.out.println(stock.getExpirationDate());
            if(stock instanceof DecayingStock decayingStock){
                if(decayingStock.getExpirationDate().isAfter(now)){
                    decayingStock.setIsDecayed(true);
                    inventory.deleteStock(decayingStock);
                }
            }
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        now = ((TimeSimulator) o).now();
        System.out.println("now : "+now);
        checkDecayingStocks();
    }
}
