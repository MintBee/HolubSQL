package com.designpattern;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 */
public final class StockDto {
    private final String name;
    private final int price;
    private final LocalDate expirationDate;

    public StockDto(String name, int price) {
        this(name, price, null);
    }

    /**
     * @param expirationDate if it's null, then it is not DecayingStock
     */
    public StockDto(String name, int price, LocalDate expirationDate) {
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate expirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (StockDto) obj;
        return Objects.equals(this.name, that.name) &&
            this.price == that.price &&
            Objects.equals(this.expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, expirationDate);
    }

    @Override
    public String toString() {
        return "StockDto[" +
            "name=" + name + ", " +
            "price=" + price + ", " +
            "expirationDate=" + expirationDate + ']';
    }

}
