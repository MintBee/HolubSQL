package com.designpattern.model;

import java.time.LocalDate;

public class DecayingStock extends Stock {
    private final LocalDate expirationDate;

    public DecayingStock(String name, LocalDate expirationDate) {
        super(name);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
