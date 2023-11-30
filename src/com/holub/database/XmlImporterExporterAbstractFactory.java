package com.holub.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XmlImporterExporterAbstractFactory implements ImporterExporterAbstractFactory {

    @Override
    public Table.Importer createImporter(File in) {
        return new XMLImporter(in);
    }

    @Override
    public Table.Exporter createExporter(File out) throws IOException {
        return new XMLExporter(new FileWriter(out));
    }

    @Override
    public String fileExtension() {
        return "xml";
    }
}
