package com.designpattern.io.cli;

public enum InputOption {
    SELL_STOCK(1),
    ADD_STOCK(2),
    ADD_PRODUCT(3),
    REMOVE_PRODUCT(4),

    EXIT(5);

    private final int id;
    InputOption(int id) {this.id = id;}
    public int getValue() {return id;}
}
