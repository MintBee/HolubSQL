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
        assertThat(stringBuffer.toString()).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<people><row><last>Holub</last><first>Allen</first><addrId>1</addrId></row><row><last>Flintstone</last><first>Wilma</first><addrId>2</addrId></row><row><last>Flintstone</last><first>Fred</first><addrId>2</addrId></row></people>");

    }
}
