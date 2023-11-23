package com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter {
	private final Writer out;
	private String tableName;
	private ArrayList<String> columnNames = new ArrayList<>();
	private int width;

	public XMLExporter( Writer out ){
		this.out = out;
	}
	public void startTable() throws IOException {
		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		out.write("\n");
	}
	public void storeMetadata( String tableName,int width,int height,Iterator columnNames ) throws IOException {
		this.width = width;
		this.tableName = tableName;
		while(columnNames.hasNext()) {
			this.columnNames.add(columnNames.next().toString());
		}
		out.write("<" + this.tableName + ">");
	}
	public void storeRow( Iterator data ) throws IOException {
		Iterator columnList = columnNames.iterator();
		out.write("<row>");

		while(data.hasNext() && columnList.hasNext()) {
			Object datum = data.next();
			Object colum = columnList.next();

			if (colum != null && datum != null) {
				out.write("<" + colum + ">");
				out.write(datum.toString());
				out.write("</" + colum + ">");
			}
		}
		out.write("</row>");
	}

	public void endTable()   throws IOException {
		out.write("</" + tableName + ">");
		out.close();
	}
}
