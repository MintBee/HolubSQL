package com.designpattern.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Stock {
    private final UUID id;
    private final String productName;

    public Stock(String id, String productName) {
        this.id = UUID.fromString(id);
        this.productName = productName;
    }

    public Stock(String productName) {
        this.id = UUID.randomUUID();
        this.productName = productName;
    }

    public String getId() {
        return id.toString();
    }

    public String getProductName() {
        return productName;
    }

    public abstract LocalDate getExpirationDate();
    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        return id.equals(stock.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
