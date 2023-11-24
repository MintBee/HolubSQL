package com.designpattern;

public class TestApp {
    public static void main(String[] args) {
        OutputBoundary cliOutputBoundary = new CLIOutputBoundary();
        InputBoundary testController = new TestController(cliOutputBoundary);
        CLIInput cli = new CLIInput(testController);

        cli.start();
    }
}
