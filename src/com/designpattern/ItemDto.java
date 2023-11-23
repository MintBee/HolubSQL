package com.designpattern;

import java.util.Objects;

public final class ItemDto {
    private final String name;
    private final int price;
    private final int quantity;

    public ItemDto(String name, int price) {
        this(name, price, 0);
    }

    public ItemDto(String name, int price, int quantity) {
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
        var that = (ItemDto) obj;
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
