/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata;

import au.com.bytecode.opencsv.CSVReader;
import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class CSVUtils
{

    public CSVUtils()
    {

    }

    public CSVReader getCSVReader(String url) throws IOException{
       char separator,quote=34;
       URL stackURL = new URL(url);
       stackURL.openConnection().setReadTimeout(120000);
       InputStream is = stackURL.openStream();
       byte[] bytes = IOUtils.toByteArray(is);
       List<String> lines = getLines(byteArreyToBufferedReader(bytes));
       String first_line = lines.get(0);
       separator = getSeparator(first_line);
       BufferedReader br = byteArreyToBufferedReader(bytes);
       CSVReader reader = new CSVReader(br,separator,quote);
       return reader;
    }
    
    private BufferedReader byteArreyToBufferedReader(byte[] bytes){
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
    }
    
    private List<String> getLines(BufferedReader br) throws IOException{
        List<String> lines = new ArrayList<>();
        String nextLine;
        while((nextLine = br.readLine()) != null)
            lines.add(nextLine);
        return lines; 
    }
    
    public List<String[]> getAll(String url) throws IOException
    {
        List<String[]> lines = new ArrayList<>();
        CSVReader reader = getCSVReader(url);
        return reader.readAll();
    }

    private char getSeparator(String line) throws IOException{
        char[] charLine;
        int comma = 0;
        int tab = 0;
        int semicolon = 0;
        if (line != null){          
            charLine = line.toCharArray();      
            for (int i = 1; i < charLine.length; i++){
                    switch (charLine[i]){
                        case '\t':
                            tab++;
                            break;
                        case ';':
                            semicolon++;
                            break;
                        case ',':
                            comma++;
                            break;
                    }
            }
        }
        System.out.println("LOG[separator]: tab = " + tab + " | ; = " + semicolon + " | , = " + comma);
        if (tab >= semicolon && tab >= comma)
            return '\t';
        else if (semicolon >= tab && semicolon >= comma)
            return ';';
        else
            return ',';
    }
}
