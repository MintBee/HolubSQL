package com.holub.database;

import com.holub.tools.ArrayIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import  java.io.*;
import java.util.*;

public class XMLImporter implements Table.Importer{

    private File in;
    private String[] columnNames;
    private int index = 0;
    private String tableName;
    private Iterator[] rows;
    private String[] values;

    public XMLImporter(File in){
        this.in = in;
    }

    public void startTable() throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = DBFactory.newDocumentBuilder();
        Document doc = builder.parse(in);

        Element root = doc.getDocumentElement();
        NodeList children = root.getChildNodes();

        tableName = root.getNodeName();

        for(int i=0; i<children.getLength(); i++){
            Node node = children.item(i);
            NodeList childNodes = node.getChildNodes();
            for(int j=0; j<childNodes.getLength();j++) {
                Node child = childNodes.item(j);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (columnNames.length < children.getLength()) {
                        columnNames[j] = child.getNodeName();
                    }
                    values[j] = child.getNodeValue();
                }
            }
            rows[i] = new ArrayIterator(values);
        }
    }


    public String loadTableName() throws IOException{
        return tableName;
    }

    public int loadWidth() throws IOException{
        return columnNames.length;
    }

    public Iterator loadColumnNames() throws IOException{
        return new ArrayIterator(columnNames);
    }

    public Iterator loadRow() throws IOException{
        Iterator row = null;
        if(rows != null){
           row = rows[index];
           index++;
        }
        return row;
    }

    public void endTable() throws IOException{}
}
