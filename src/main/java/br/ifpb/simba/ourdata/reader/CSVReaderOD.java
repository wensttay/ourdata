/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import au.com.bytecode.opencsv.CSVReader;
import br.ifpb.simba.ourdata.dao.entity.PlaceBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.test.TestReaderCSV;
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
import java.util.Arrays;
import java.util.List;

/**
 * Class that know how read a CSV file and
 *
 * @author Wensttay, Pedro Arthur
 */
public class CSVReaderOD implements Reader<List<String[]>, String> {

    private static final int NUM_ROWS_CHECK_DEFAULT = 10;
    private int numRowsCheck;

    /**
     * This Construtor create a CSVReaderOD using NUM_ROWS_CHECK_DEFAULT to the
     * numRowsCheck.
     * <p>
     */
    public CSVReaderOD() {
        numRowsCheck = NUM_ROWS_CHECK_DEFAULT;
    }

    /**
     * This constructor create a CSVReaderOD using the numRowsCheck passed into
     * paramn
     *
     * @param numRowsCheck Number of rows wants to check to define if this CSV
     * have or not a KeyPlaces
     */
    public CSVReaderOD(int numRowsCheck) {
        this.numRowsCheck = numRowsCheck;
    }

    /**
     * Method used to get a CSVReader containing bytes of CSV file by a String
     * Url
     *
     * @param url String URL of CSV file
     *
     * @return A CSVReader containing bytes of CSV file
     *
     * @throws IOException
     */
    public CSVReader getCSVReaderBuild(String url) throws IOException {
        URL stackURL = new URL(url);
        stackURL.openConnection().setReadTimeout(120000);
        InputStream is = stackURL.openStream();
        return getCSVReader(is);
    }

    /**
     * Method used to get a CSVReader containing bytes of CSV file by a
     * InputStream
     *
     * @param is String URL of CSV file
     *
     * @return A CSVReader containing bytes of CSV file
     *
     * @throws IOException
     */
    private CSVReader getCSVReader(InputStream is) throws IOException {

        char separator, quote = 34;
        byte[] bytes = IOUtils.toByteArray(is);
        List<String> lines = getLines(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.ISO_8859_1)));
        String first_line = lines.get(0);
        separator = getSeparator(first_line);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), StandardCharsets.ISO_8859_1));
        
        return new au.com.bytecode.opencsv.CSVReader(br, separator, quote);
    }
    /**
     * Method used to get a CSVReader containing bytes of CSV file by a
     * BufferedReader
     *
     * @param is String URL of CSV file
     *
     * @return A CSVReader containing bytes of CSV file
     *
     * @throws IOException
     */
    private CSVReader getCSVReader(BufferedReader br) throws IOException {
        char separator, quote = 34;
        String first_line;
        List<String> lines = getLines(br);
        if (!lines.isEmpty()) {
            first_line = lines.get(0);
            separator = getSeparator(first_line);
            CSVReader reader = new au.com.bytecode.opencsv.CSVReader(br, separator, quote);
            return reader;
        }
        return null;
    }

    /**
     * Method used to get list of Lines (list of Strings) using a BufferedReader
     *
     * @param br BufferedReader with file array bites
     *
     * @return List of Strings, each of strings represent a one line of file
     *
     * @throws IOException
     */
    private List<String> getLines(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String nextLine;
        while ((nextLine = br.readLine()) != null) {
            lines.add(nextLine);
        }
        return lines;
    }

    /**
     * Get which separators are used in this line of file
     *
     * @param line
     *
     * @return A char use in this line of file
     *
     * @throws IOException
     */
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

    /**
     * Method used to build a CSV file by String URL
     *
     * @param url String url to CSV file
     *
     * @return List of each word for each line
     */
    @Override
    public List<String[]> build(String url) {
        try {
            CSVReader reader = getCSVReaderBuild(url);
            return reader.readAll();
        } catch (IOException ex) {
            TestReaderCSV.error_count++;
            System.out.println(TextColor.ANSI_RED + "Error: Couldn't open the URL [" + TestReaderCSV.error_count + "]" + TextColor.ANSI_BLACK);
        }
        return null;
    }

    /**
     * Method used to build a CSV file by InputStream
     *
     * @param is InputStream to CSV file
     *
     * @return List of each word for each line
     */
    public List<String[]> build(InputStream is) {
        try {
            return getCSVReader(is).readAll();
        } catch (IOException ex) {
            TestReaderCSV.error_count++;
            System.out.println(TextColor.ANSI_RED + "Error: Couldn't open the URL [" + TestReaderCSV.error_count + "]" + TextColor.ANSI_BLACK);
        }
        return null;
    }

    /**
     * Method used to build a CSV file by BufferedReader
     *
     * @param br BufferedReader to CSV file
     *
     * @return List of each word for each line
     */
    public List<String[]> build(BufferedReader br) throws IOException {
        au.com.bytecode.opencsv.CSVReader reader = getCSVReader(br);
        if (reader != null) {
            return reader.readAll();
        }
        return new ArrayList<>();
    }

    /**
     * Method used to print a CSV file
     *
     * @param urlString String url of CSV file
     */
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
                    System.out.print(TextColor.ANSI_BLUE);
                } else {
                    System.out.print(TextColor.ANSI_BLACK);
                }

                for (String cell : row) {
                    System.out.print(cell + " | ");
                }
                System.out.println();

                if (count_row == 3) {
                    break;
                }
            }
            TestReaderCSV.success_count++;
            System.out.println(TextColor.ANSI_GREEN + "!Success! " + TextColor.ANSI_BLACK);

        } catch (OutOfMemoryError ex) {
            TestReaderCSV.error_count++;
            System.out.println(TextColor.ANSI_RED + "Error: Couldn't open the URL [" + TestReaderCSV.error_count + "]" + TextColor.ANSI_BLACK);
        }
    }

    @Deprecated
    /**
     * Deprecated method, use KeyPlacesBo.getKeyPlaces(CkanResource);
     * Method used to get all KeyPlaces on a CSV file
     *
     * @param ckanResourceId CkanResourceId of CSV file belongs
     * @param urlString String URL CSV file
     *
     * @return
     */
    public List<KeyPlace> getKeyPlaces(String ckanResourceId, String urlString) {
        PlaceBdDao placeBdDao = new PlaceBdDao();
        float porcent = 0;

        System.out.println("Search for KeyWords into: " + urlString);
//        Instanciando lista que será retornada como resultado
        List<KeyPlace> keyWordResultList = new ArrayList<>();

//        Instanciando a lista que contem todas as linhas(rows) de um CSV 
        List<String[]> rowListOfCsv = build(urlString);
        if (rowListOfCsv == null) {
            rowListOfCsv = new ArrayList<>();
        }

//        Instanciando a lista com os nomes das colunas (primeira linha do CSV)
        List<String> columNames = new ArrayList<>();
        if (!rowListOfCsv.isEmpty()) {
            String[] auxRow = rowListOfCsv.get(0);
            columNames.addAll(Arrays.asList(auxRow));
        }

//      Iterate of Rows
        int auxSizeOfCsv = rowListOfCsv.size();

        for (int indexOfRow = 0; indexOfRow < auxSizeOfCsv; indexOfRow++) {
            String[] row = rowListOfCsv.get(indexOfRow);
            List<KeyPlace> rowKeyWordsOfRow = new ArrayList<>();

//          Iterate of Columns
            for (int indexOfColumn = 0; indexOfColumn < row.length; indexOfColumn++) {
//              Dentro desse comando se faz o filtro para a lista de colunas que apresentaram
//              resutados encontrados na pesquisa no Gazetteer
                if (keyWordResultList.size() > numRowsCheck) {
                    for (int i = 0; i < numRowsCheck; i++) {
                        while (indexOfColumn < row.length && keyWordResultList.get(i).getColumNumber() != indexOfColumn) {
                            indexOfColumn++;
                        }
                    }
                    if (indexOfColumn >= row.length) {
                        break;
                    }
                }

//              Fix text into Columns
                String columValue = row[indexOfColumn].replace("\n", " ");

//              Checking if the colum Value is valid and Search a Place
                List<Place> newPlaces = new ArrayList<>();
                if (columValue != null && !columValue.equals("")) {
                    newPlaces.addAll(placeBdDao.burcarPorTitulos(columValue));
                }

//              Checking if has some KeyWords with a place that contains a new place
               if (!newPlaces.isEmpty() && !rowKeyWordsOfRow.isEmpty()) {
                    List<KeyPlace> aux = new ArrayList<>();
                    aux.addAll(rowKeyWordsOfRow);

                    for (Place newPlace : newPlaces) {
                        for (KeyPlace kw : rowKeyWordsOfRow) {
                            if (kw.getPlace().getWay().intersects(newPlace.getWay())) {
                                aux.remove(kw);
                                break;
                            }
                        }
                    }
                    rowKeyWordsOfRow = aux;
                }

//              Instancie and increment the list of row's results with the new KeyPlace
                for (Place newPlace : newPlaces) {
                    KeyPlace kw = new KeyPlace();
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

//          Increment the resultList with all news KeyWords of this row
            keyWordResultList.addAll(rowKeyWordsOfRow);
            if (indexOfRow >= numRowsCheck && keyWordResultList.isEmpty()) {
                System.out.println("!! ATINGIU O NUMERO MAX DE " + numRowsCheck + " ROWS VERIFICADAS SEM ENCONTRAR NENHUMA KEYWORD !!");
                break;
            }

            if (!rowKeyWordsOfRow.isEmpty()) {
                NumberFormat formatter = new DecimalFormat("#0.00");
                float percentRead = (((float) indexOfRow * 100) / (float) auxSizeOfCsv);
                if (porcent + 1 < percentRead) {
                    System.out.println(formatter.format(percentRead) + " %");
                    porcent = percentRead;
                }
            }
        }

        if (!keyWordResultList.isEmpty()) {
            System.out.println("100 %");
        }
        rowListOfCsv = null;
        columNames = null;
        System.gc();

        return keyWordResultList;
    }
}
