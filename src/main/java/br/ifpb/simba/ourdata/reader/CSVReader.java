/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import br.ifpb.simba.ourdata.geo.KeyWord;
import br.ifpb.simba.ourdata.geo.Place;
import br.ifpb.simba.ourdata.test.TestCSV;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_BLACK;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_BLUE;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_GREEN;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_RED;
import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class CSVReader implements Reader<List<String[]>, String> {

    public au.com.bytecode.opencsv.CSVReader getCSVReaderBuild(String url) throws IOException {
        URL stackURL = new URL(url);
        stackURL.openConnection().setReadTimeout(120000);
        InputStream is = stackURL.openStream();
        return getCSVReader(is);
    }

    private au.com.bytecode.opencsv.CSVReader getCSVReader(InputStream is) throws IOException {
        char separator, quote = 34;
        byte[] bytes = IOUtils.toByteArray(is);
        List<String> lines = getLines(byteArrayToBufferedReader(bytes));
        String first_line = lines.get(0);
        separator = getSeparator(first_line);
        BufferedReader br = byteArrayToBufferedReader(bytes);
        au.com.bytecode.opencsv.CSVReader reader = new au.com.bytecode.opencsv.CSVReader(br, separator, quote);
        return reader;
    }

    private au.com.bytecode.opencsv.CSVReader getCSVReader(BufferedReader br) throws IOException {
        char separator, quote = 34;
        String first_line;
        List<String> lines = getLines(br);
        if (!lines.isEmpty()) {
            first_line = lines.get(0);
            separator = getSeparator(first_line);
            au.com.bytecode.opencsv.CSVReader reader = new au.com.bytecode.opencsv.CSVReader(br, separator, quote);
            return reader;
        }
        return null;
    }

    private BufferedReader byteArrayToBufferedReader(byte[] bytes) {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.ISO_8859_1));
    }

    private List<String> getLines(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String nextLine;
        while ((nextLine = br.readLine()) != null) {
            lines.add(nextLine);
        }
        return lines;
    }

    private char getSeparator(String line) throws IOException {
        char[] charLine;
        int comma = 0;
        int tab = 0;
        int semicolon = 0;
        if (line != null) {
            charLine = line.toCharArray();
            for (int i = 1; i < charLine.length; i++) {
                switch (charLine[i]) {
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
        if (tab >= semicolon && tab >= comma) {
            return '\t';
        } else if (semicolon >= tab && semicolon >= comma) {
            return ';';
        } else {
            return ',';
        }
    }

    @Override
    public List<String[]> build(String url) {
        try {
            au.com.bytecode.opencsv.CSVReader reader = getCSVReaderBuild(url);
            return reader.readAll();
        } catch (IOException ex) {
            TestCSV.error_count++;
            System.out.println(ANSI_RED + "Error: Couldn't open the URL [" + TestCSV.error_count + "]" + ANSI_BLACK);
        }
        return null;
    }

    public List<String[]> build(InputStream is) {
        try {
            return getCSVReader(is).readAll();
        } catch (IOException ex) {
            TestCSV.error_count++;
            System.out.println(ANSI_RED + "Error: Couldn't open the URL [" + TestCSV.error_count + "]" + ANSI_BLACK);
        }
        return null;
    }

    public List<String[]> build(BufferedReader br) throws IOException {
        au.com.bytecode.opencsv.CSVReader reader = getCSVReader(br);
        if (reader != null) {
            return reader.readAll();
        }
        return new ArrayList<>();
    }

    @Override
    public void print(String urlString) {
        int count_row;
        try {
            List<String[]> allcsv = build(urlString);
            if (allcsv == null) {
                allcsv = new ArrayList<>();
            }
            count_row = 0;
            for (String[] row : allcsv) {
                count_row++;
                if (count_row == 1) {
                    System.out.print(ANSI_BLUE);
                } else {
                    System.out.print(ANSI_BLACK);
                }

                for (String cell : row) {
                    System.out.print(cell + " | ");
                }
                System.out.println();

                if (count_row == 3) {
                    break;
                }
            }
            TestCSV.success_count++;
            System.out.println(ANSI_GREEN + "!Success! " + ANSI_BLACK);

        } catch (OutOfMemoryError ex) {
            TestCSV.error_count++;
            System.out.println(ANSI_RED + "Error: Couldn't open the URL [" + TestCSV.error_count + "]" + ANSI_BLACK);
        }
    }

    @Override
    public List<KeyWord> filterKeyWord(String resourceId, String urlString, List<Place> PlaceList) {

        List<KeyWord> keyWordResultList = new ArrayList<>();

        List<String[]> allcsv = build(urlString);
        if (allcsv == null) {
            allcsv = new ArrayList<>();
        }

//          Interate of Rows
        for (String[] row : allcsv) {
//              Interate of Colum
            for (int i = 0; i < row.length; i++) {
//                  Text into Colum
                String wordOfColum = row[i].replace("\n", " ");
                for (Place place : PlaceList) {
                    
//                      Checking if the text contains a some keyword
                    boolean nameValid = (place.getNome() != null 
                            && !place.getNome().equals("") 
//                            && wordOfColum.contains(place.getNome())
                            && wordOfColum.equals(place.getNome()));
//                    boolean siglaValid = place.getSigla() != null 
//                            && !place.getSigla().equals("") 
//                            && wordOfColum.contains(place.getSigla());
                    if (nameValid) {
//                            || siglaValid
                        KeyWord kw = new KeyWord();
                        kw.setColumNumber(i);
                        kw.setColumValue(wordOfColum);
                        kw.setIdResource(resourceId);
                        kw.setMetadataCreated(new Timestamp(System.currentTimeMillis()));
                        kw.setPlace(place);
                        kw.setRepeatNumber(kw.getRepeatNumber() + 1);
                        kw.setRowsNumber(allcsv.size());
                        keyWordResultList.add(kw);
                    }
                }
            }
        }
        
        return keyWordResultList;
    }
}
