/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import br.ifpb.simba.ourdata.dao.geo.PlaceBdDao;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class CSVReader implements Reader<List<String[]>, String> {
    
    private int NUM_ROWS_CHECK = 10;

    public CSVReader() {
    }

    public CSVReader(int num_rows_check) {
        this.NUM_ROWS_CHECK = num_rows_check;
    }
    
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

    public List<KeyWord> getKeyWords(String ckanResourceId, String urlString, PlaceBdDao placeBdDao) {
        
        float porcent = 0;
        
        System.out.println("Search for KeyWords into: " + urlString);
//        Instanciando lista que será retornada como resultado
        List<KeyWord> keyWordResultList = new ArrayList<>();

//        Instanciando a lista que contem todas as linhas(rows) de um CSV 
        List<String[]> rowListOfCsv = build(urlString);
        if (rowListOfCsv == null) {
            rowListOfCsv = new ArrayList<>();
        }

//        Instanciando a lista com os nomes das colunas (primeira linha do CSV)
        List<String> columNames = new ArrayList<>();
        if (!rowListOfCsv.isEmpty()) {
            String[] auxRow = rowListOfCsv.get(0);
            for (String str : auxRow) {
                columNames.add(str);
            }
        }
//      Iterate of Rows
        int auxSizeOfCsv = rowListOfCsv.size();

        for (int indexOfRow = 0; indexOfRow < auxSizeOfCsv; indexOfRow++) {
            String[] row = rowListOfCsv.get(indexOfRow);
            List<KeyWord> rowKeyWordsOfRow = new ArrayList<>();

//          Iterate of Columns
            for (int indexOfColumn = 0; indexOfColumn < row.length; indexOfColumn++) {
//                Dentro desse comando se faz o filtro para a lista de colunas que apresentaram
//                resutados encontrados na pesquisa no Gazetteer
                if (keyWordResultList.size() > NUM_ROWS_CHECK) {
                    for (int i = 0; i < NUM_ROWS_CHECK; i++) {
                        while (indexOfColumn < row.length && keyWordResultList.get(i).getColumNumber() != indexOfColumn) {
                            indexOfColumn++;
                        }
                    }
                    if (indexOfColumn >= row.length) {
                        break;
                    }
                }

//                Fix text into Columns
                String columValue = row[indexOfColumn].replace("\n", " ");

//                Checking if the colum Value is valid and Search a Place
                List<Place> newPlaces = new ArrayList<>();
                if (columValue != null && !columValue.equals("")) {
                    newPlaces.addAll(placeBdDao.burcarPorTitulos(columValue));
                }

//                Checking if has some KeyWords with a place that contains a new place
                if (!newPlaces.isEmpty() && !rowKeyWordsOfRow.isEmpty()) {

                    List<KeyWord> aux = new ArrayList<>();
                    aux.addAll(rowKeyWordsOfRow);

                    for (Place newPlace : newPlaces) {
                        for (KeyWord kw : rowKeyWordsOfRow) {
                            if(kw.getPlace().getWay().intersects(newPlace.getWay())){
                                aux.remove(kw);
                                break;
                            }
                        }
                    }
                    rowKeyWordsOfRow = aux;
                }

//                Instancie and increment the list of row's results with the new KeyWord
                for (Place newPlace : newPlaces) {
                    KeyWord kw = new KeyWord();
                    kw.setColumNumber(indexOfColumn);
                    kw.setColumValue(columValue);
                    kw.setIdResource(ckanResourceId);
                    kw.setMetadataCreated(new Timestamp(System.currentTimeMillis()));
                    kw.setPlace(newPlace);
                    kw.setRepeatNumber(1);
                    kw.setRowsNumber(auxSizeOfCsv);
                    rowKeyWordsOfRow.add(kw);
                }
            }

//            Increment the resultList with all news KeyWords of this row
            keyWordResultList.addAll(rowKeyWordsOfRow);

            if (indexOfRow >= NUM_ROWS_CHECK && keyWordResultList.isEmpty()) {
                System.out.println("!! ATINGIU O NUMERO MAX DE " + NUM_ROWS_CHECK + " ROWS VERIFICADAS SEM ENCONTRAR NENHUMA KEYWORD !!");
                break;
            }

            if (!rowKeyWordsOfRow.isEmpty()) {
                NumberFormat formatter = new DecimalFormat("#0.00");
                float percentRead = (((float) indexOfRow * 100) / (float) auxSizeOfCsv);
                if(porcent + 1 < percentRead){
                    System.out.println(formatter.format(percentRead) + " %");
                    porcent = percentRead;
                }
            }
        }
        
        if(!keyWordResultList.isEmpty()){
            System.out.println("100 %");
        }
        
        rowListOfCsv = null;
        columNames = null;
        System.gc();
        
        return keyWordResultList;
    }
}
