package com.holub.database;

import com.holub.tools.ArrayIterator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class XMLImporterTest {
    XMLImporter xmlImporter = new XMLImporter(new File("testPeople.xml"));

    @Test
    void startTable() throws IOException {
        xmlImporter.startTable();

    }

    @Test
    void loadTableName() throws IOException {
        xmlImporter.startTable();
        assertThat(xmlImporter.loadTableName())
                .isNotEmpty()
                .contains("people");

    }

    @Test
    void loadWidth() throws IOException {
        xmlImporter.startTable();
        assertThat(xmlImporter.loadWidth())
                .isEqualTo(3);
    }

    @Test
    void loadColumnNames() throws IOException {
        xmlImporter.startTable();
        ArrayIterator iter = (ArrayIterator) xmlImporter.loadColumnNames();
        assertThat(iter.next())
                .isEqualTo("last");
        assertThat(iter.next())
                .isEqualTo("first");
        assertThat(iter.next())
                .isEqualTo("addrId");

    }

    @Test
    void loadRow() throws IOException {
        xmlImporter.startTable();
        ArrayIterator data1 = (ArrayIterator) xmlImporter.loadRow();
        ArrayIterator data2 = (ArrayIterator) xmlImporter.loadRow();
        ArrayIterator data3 = (ArrayIterator) xmlImporter.loadRow();
        assertThat(data1.next())
                .isEqualTo("Holub");
        assertThat(data1.next())
                .isEqualTo("Allen");
        assertThat(data1.next())
                .isEqualTo("1");
        assertThat(data2.next())
                .isEqualTo("Flintstone");
        assertThat(data2.next())
                .isEqualTo("Wilma");
        assertThat(data2.next())
                .isEqualTo("2");
        assertThat(data3.next())
                .isEqualTo("Flintstone");
        assertThat(data3.next())
                .isEqualTo("Fred");
        assertThat(data3.next())
                .isEqualTo("2");
    }

}