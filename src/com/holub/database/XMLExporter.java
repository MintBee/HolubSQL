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

	}
	public void storeRow( Iterator data ) throws IOException {
		
	}
	public void startTable() throws IOException {/*nothing to do*/}
	public void endTable()   throws IOException {/*nothing to do*/}
}
