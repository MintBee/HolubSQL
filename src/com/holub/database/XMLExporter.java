package com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter {
	private final Writer out;
	private 	  int	 width;

	public XMLExporter( Writer out ){
		this.out = out;
	}
	public void storeMetadata( String tableName,int width,int height,Iterator columnNames ) throws IOException {
		this.width = width;
		out.write("<root>");
		out.write("<title>" + tableName + "</title>");
		out.write("<width>" + width + "</width>");
		out.write("<height>" + height + "</height>");
		out.write("<columnNames>");
		while( columnNames.hasNext() )
			out.write("<columnName>" + columnNames.next() + "</columnName>");
		out.write("</columnNames>");

	}
	public void storeRow( Iterator data ) throws IOException {
		out.write("<row>");
		for( int i = 0; i < width; ++i ) {
			Object datum = data.next();
			if( datum != null )
				out.write("<data>" + datum.toString() + "</data>");
		}
		out.write("</row>");

	}
	public void startTable() throws IOException {/*nothing to do*/}
	public void endTable()   throws IOException {/*nothing to do*/}
}
