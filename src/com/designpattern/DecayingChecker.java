package com.designpattern;

import com.designpattern.model.DbSelectVisitor;
import com.designpattern.model.DecayingStock;
import com.designpattern.model.Stock;

import java.time.LocalDate;
import java.util.List;
import java.util.Observable;

public class DecayingChecker extends Observable {
    private final TimeSimulator timeSimulator;
    private final DbSelectVisitor dbSelectVisitor;

    public DecayingChecker(DbSelectVisitor dbSelectVisitor){
        this.timeSimulator = new TimeSimulator(1000, this::checkDecayingStocks);
        this.dbSelectVisitor = dbSelectVisitor;
    }

    public void notifyDataSetChanged(){
        setChanged();
        notifyObservers();
    }

    private void checkDecayingStocks(){
        List<Stock> decayingStocks = dbSelectVisitor.visitStock();
        LocalDate now = timeSimulator.now();

        for (Stock stock : decayingStocks) {
            if(stock instanceof DecayingStock decayingStock){
                if(decayingStock.getExpirationDate().isAfter(now)){
                    decayingStock.setIsDecayed(true);
                }
            }
        }
        notifyDataSetChanged();
    }
}
