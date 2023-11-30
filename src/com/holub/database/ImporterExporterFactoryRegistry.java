package com.holub.database;

public class ImporterExporterFactoryRegistry {
    private static ImporterExporterAbstractFactory factoryInstance;

    static {
        register(new CsvImporterExporterAbstractFactory());
    }

    public static ImporterExporterAbstractFactory getInstance() {
        return factoryInstance;
    }

    public static void register(ImporterExporterAbstractFactory factory) {
        factoryInstance = factory;
    }
}
