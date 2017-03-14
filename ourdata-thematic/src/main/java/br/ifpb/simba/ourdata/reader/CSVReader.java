/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class CSVReader {
    
    public List<String[]> read(String url) throws CSVReaderException {
        
        List<String[]> csv = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url)))) {
            
            String line = br.readLine();
            
            
            if(line != null) {
            
                char separator = getSeparator(line);
                String[] firstLine = line.split(String.valueOf(separator));

                csv.add(firstLine);
                
                while((line = br.readLine()) != null) {
                    
                    String[] currentLine = new String[firstLine.length];
                    Arrays.fill(currentLine, "");
                    
                    String[] result = line.split(String.valueOf(separator));
                    
                    for(int i = 0; i<currentLine.length; i++) {
                        if(i < result.length) {
                            currentLine[i] = result[i];
                        }
                    }
                    
                    
                    csv.add(currentLine);
                }
            }
            
        } catch (IOException ex) {
            throw new CSVReaderException("Could not read file "+url+". Cause: "+ex.getMessage());
        }
        
        return csv;
    }
    
    private InputStream getInputStreamFromUrl(String url) throws IOException {
        
        URL stockUrl = new URL(url);
        
        URLConnection conn = stockUrl.openConnection();
        conn.setReadTimeout(120000);       
        
        return  conn.getInputStream();
    }
    
    private BufferedReader getBufferedReaderFromUrl(String url) throws IOException {
        URL stockUrl = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(stockUrl.openStream()));
        return in;
    }
    
    private char getSeparator(String line) {
        
        char[] charLine;
        int comma = 0;
        int tab = 0;
        int semicolon = 0;
        
        if ( line != null ){
            charLine = line.toCharArray();
            for ( int i = 1; i < charLine.length; i++ ){
                switch ( charLine[i] ){
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
        if ( tab >= semicolon && tab >= comma ){
            return '\t';
        } else if ( semicolon >= tab && semicolon >= comma ){
            return ';';
        } else{
            return ',';
        }
    }
}
