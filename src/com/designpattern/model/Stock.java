package com.designpattern.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Stock {
    private final UUID id;
    private final String productName;

    public Stock(String productName) {
        this.id = UUID.randomUUID();
        this.productName = productName;
    }

    String getId() {
        return id.toString();
    }

    public String getProductName() {
        return productName;
    }

    public abstract LocalDate getExpirationDate();
    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }
}
