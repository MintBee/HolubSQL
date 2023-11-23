package com.holub.database.jdbc;

import com.holub.database.HTMLExporter;
import com.holub.database.Table;
import com.holub.database.TableFactory;
import com.holub.database.XMLExporter;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class XMLExporterTest {
    @Test
    void testXTMLExporter() throws IOException {

        Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });
        people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });

        Writer out = new FileWriter("people.xml");
        XMLExporter builder1 = new XMLExporter(out);
        people.export(builder1);
        out.close();

        File file = new File("people.xml");
        StringBuffer stringBuffer = new StringBuffer();
        FileReader fileReader = new FileReader(file);
        int index = 0;
        while ((index = fileReader.read()) != -1) {
            stringBuffer.append((char) index);

        }
//        XMLExporter테스트
        assertThat(stringBuffer.toString()).isEqualTo("<root><title>people</title><columnNames><columnName>last</columnName><columnName>first</columnName><columnName>addrId</columnName></columnNames><row><data>Holub</data><data>Allen</data><data>1</data></row><row><data>Flintstone</data><data>Wilma</data><data>2</data></row><row><data>Flintstone</data><data>Fred</data><data>2</data></row></root>");

    }
}
