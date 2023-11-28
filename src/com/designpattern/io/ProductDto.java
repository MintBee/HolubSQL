package com.designpattern.io;

import java.util.Objects;

public final class ProductDto {
    private final String name;
    private final int price;
    private final int quantity;

    public ProductDto(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ProductDto) obj;
        return Objects.equals(this.name, that.name) &&
            this.price == that.price &&
            this.quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity);
    }

    @Override
    public String toString() {
        return "ItemDto[" +
            "name=" + name + ", " +
            "price=" + price + ", " +
            "quantity=" + quantity + ']';
    }

}
