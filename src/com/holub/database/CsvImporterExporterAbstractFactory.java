package com.holub.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CsvImporterExporterAbstractFactory implements ImporterExporterAbstractFactory {
    @Override
    public Table.Importer createImporter(File in) throws IOException {
        return new CSVImporter(new FileReader(in));
    }

    @Override
    public Table.Exporter createExporter(File out) throws IOException {
        return new CSVExporter(new FileWriter(out));
    }

    @Override
    public String fileExtension() {
        return "csv";
    }
}
