package com.holub.database;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HTMLExporterTest {
    @Test
    void testHTMLExporter() throws IOException {
        Table people = TableFactory.create("people", new String[] { "last", "first", "addrId" });
        people.insert(new Object[] { "Holub", "Allen", "1" });
        people.insert(new Object[] { "Flintstone", "Wilma", "2" });
        people.insert(new String[] { "addrId", "first", "last" }, new Object[] { "2", "Fred", "Flintstone" });

        Writer out = new FileWriter("people.html");
        people.export(new HTMLExporter(out));
        out.close();

        File file = new File("people.html");
        StringBuffer stringBuffer = new StringBuffer();
        FileReader fileReader = new FileReader(file);
        int index = 0;
        while ((index = fileReader.read()) != -1) {
            stringBuffer.append((char) index);
        }

        assertThat(stringBuffer.toString()).isEqualTo("<table><caption>people/<caption><tr><th>last</th><th>first</th><th>addrId</th></tr><tr><td>Holub</td><td>Allen</td><td>1</td></tr><tr><td>Flintstone</td><td>Wilma</td><td>2</td></tr><tr><td>Flintstone</td><td>Fred</td><td>2</td></tr></table>");
    }
}
