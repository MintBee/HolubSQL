package com.designpattern;

import com.designpattern.io.InputBoundary;
import com.designpattern.io.OutputBoundary;
import com.designpattern.io.cli.CLIInput;
import com.designpattern.io.cli.CLIOutputBoundary;

public class TestApp {
    public static void main(String[] args) {
        OutputBoundary cliOutputBoundary = new CLIOutputBoundary();
        InputBoundary testController = new TestController(cliOutputBoundary);
        CLIInput cli = new CLIInput(testController);

        cli.start();

    }
}
