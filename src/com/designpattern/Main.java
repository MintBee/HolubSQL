package com.designpattern;

import com.designpattern.io.InputBoundary;
import com.designpattern.io.OutputBoundary;
import com.designpattern.io.cli.CLIInput;
import com.designpattern.io.cli.CLIOutputBoundary;

public class Main {
    public static void main(String[] args) {
        OutputBoundary cliOutputBoundary = new CLIOutputBoundary();
        Inventory inventory = new InventoryImpl();
        InputBoundary shop = new Shop(inventory, cliOutputBoundary);
        CLIInput cli = new CLIInput(shop);

        cli.start();
    }
}
