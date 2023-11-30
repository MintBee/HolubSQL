package com.holub.database;

import java.io.File;
import java.io.IOException;

public interface ImporterExporterAbstractFactory {
    Table.Importer createImporter(File in) throws IOException;
    Table.Exporter createExporter(File out) throws IOException;
    String fileExtension();
}
