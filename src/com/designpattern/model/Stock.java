package com.designpattern.model;

public abstract class Stock {
    private final Long id;
    private final String productName;

    public Stock(Long id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    public Stock(String productName) {
        this(null, productName);
    }

    Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void accept(ModelVisitor visitor) {
        visitor.visit(this);
    }
}
