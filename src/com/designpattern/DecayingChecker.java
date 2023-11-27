package com.designpattern;

import com.designpattern.model.DbDeleteVisitor;
import com.designpattern.model.DbSelectVisitor;
import com.designpattern.model.DecayingStock;
import com.designpattern.model.Stock;

import java.time.LocalDate;
import java.util.List;

public class DecayingChecker {
    private final TimeSimulator timeSimulator;
    private final DbSelectVisitor dbSelectVisitor;
    private final DbDeleteVisitor dbDeleteVisitor;


    public DecayingChecker(
            DbSelectVisitor dbSelectVisitor,
            DbDeleteVisitor dbDeleteVisitor
    ){
        this.timeSimulator = new TimeSimulator(1000, this::checkDecayingStocks);
        this.dbSelectVisitor = dbSelectVisitor;
        this.dbDeleteVisitor = dbDeleteVisitor;
    }

    private void checkDecayingStocks(){
        List<Stock> decayingStocks = dbSelectVisitor.visitStock();
        LocalDate now = timeSimulator.now();

        for (Stock stock : decayingStocks) {
            if(stock instanceof DecayingStock){
                if(((DecayingStock) stock).getExpirationDate().isAfter(now)){
                    dbDeleteVisitor.visit(stock);
                }
            }
        }
    }
}
