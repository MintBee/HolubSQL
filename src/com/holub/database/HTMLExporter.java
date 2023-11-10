package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HTMLExporter implements Table.Exporter {
    private final Writer out;
    private int width;
    private int height;
    public HTMLExporter(Writer out) {this.out = out;}
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {

        this.width = width;
        this.height = height;
        out.write("<caption>");
        out.write(tableName == null ? "<anonymous>" : tableName);
        out.write("/<caption>");
        out.write("\n");

        storeTableHead(columnNames);
        out.write("\n");
    }

    public void storeRow(Iterator data) throws IOException {
        // 데코레이터로 변경 가능
        out.write("<tr>\n");
        while (data.hasNext()) {
            Object datum = data.next();
            out.write("<td>" + datum.toString() + "</td>\n");
        }
        // 데코레이터로 변경 가능
        out.write("</tr>\n");
    }

    public void startTable() throws IOException {
        out.write("<table>\n");
    }

    public void endTable() throws IOException {
        out.write("</table>");
    }

    private void storeTableHead(Iterator data) throws IOException {
        out.write("<tr>\n");
        while (data.hasNext()) {
            Object datum = data.next();
            out.write("<th>" + datum.toString() + "</th>");
            out.write("\n");
        }
        out.write("</tr>");
    }
}
