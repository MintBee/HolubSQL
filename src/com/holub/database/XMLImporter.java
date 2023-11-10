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

    private final File in;
    private String[] columnNames;
    private int index = 0;
    private String tableName;
    private Iterator[] rows;
    private String[] values;

    public XMLImporter(File in){
        this.in = in;
    }

    @Override
    public void startTable() throws IOException {
        DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = DBFactory.newDocumentBuilder();
            doc = builder.parse(in);
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }

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

    @Override
    public String loadTableName() throws IOException{
        return tableName;
    }

    @Override
    public int loadWidth() throws IOException{
        return columnNames.length;
    }

    @Override
    public Iterator loadColumnNames() throws IOException{
        return new ArrayIterator(columnNames);
    }

    @Override
    public Iterator loadRow() throws IOException{
        Iterator row = null;
        if(rows != null){
            row = rows[index];
            index++;
        }
        return row;
    }

    @Override
    public void endTable() throws IOException{}
}
