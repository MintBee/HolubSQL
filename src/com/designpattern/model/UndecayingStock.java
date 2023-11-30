package com.designpattern.model;

import java.time.LocalDate;

public class UndecayingStock extends Stock {
    public UndecayingStock(String id, String productName) {
        super(id, productName);
    }

    public UndecayingStock(String name) {
        super(name);
    }

    @Override
    public LocalDate getExpirationDate() {
        return null;
    }
}
