package com.designpattern.model;

import java.time.LocalDate;

public class DecayingStock extends Stock {
    private final LocalDate expirationDate;
    private boolean isDecayed;

    public DecayingStock(String name, LocalDate expirationDate) {
        super(name);
        this.expirationDate = expirationDate;
        this.isDecayed = false;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public void setIsDecayed(boolean isDecayed){
        this.isDecayed = isDecayed;
    }
    public boolean getIsDecayed(){
        return isDecayed;
    }
}
