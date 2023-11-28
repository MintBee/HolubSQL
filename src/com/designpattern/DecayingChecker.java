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

    public DecayingChecker(Inventory inventory) {
        this.inventory = inventory;
    }

    private void checkDecayingStocks(){
        List<Stock> decayingStocks = DbModelFactory.getAllStocks();

        for (Stock stock : decayingStocks) {
            if(stock instanceof DecayingStock decayingStock){
                if(decayingStock.getExpirationDate().isAfter(now)){
                    decayingStock.setIsDecayed(true);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        now = ((TimeSimulator) o).now();
        checkDecayingStocks();
    }
}
