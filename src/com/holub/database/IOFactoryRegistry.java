package com.holub.database;

public class IOFactoryRegistry {
    private static IOFactory factoryInstance;

    static {
        register(new CsvIOFactory());
    }

    public static IOFactory getInstance() {
        return factoryInstance;
    }

    public static void register(IOFactory factory) {
        factoryInstance = factory;
    }
}
