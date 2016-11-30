/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import br.ifpb.simba.ourdata.reader.CSVReaderOD;
import br.ifpb.simba.ourdata.reader.TextColor;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Pedro Arthur
 */
public class ColumnIndexProcessor {

    private static Long count = 0L;

    private static void printCSV(List<String[]> rows) {

        String[] header = rows.get(0);
        Integer qtd_columns = header.length;
        Integer qtd_rows = rows.size();

        for (int j = 0; j < qtd_columns; j++) {

            for (int i = 0; i < qtd_rows; i++) {

               String[] row = rows.get(i);
               
               String value = row[j];
               
                System.out.println(value);

            }
        }
    }

    public static List<Set<ColumnIndexDTO>> process(CkanResource ckanResource) throws OutOfMemoryError {

        String url = ckanResource.getUrl();
        List<String[]> rows = new CSVReaderOD().build(url);
        
        if(rows == null || rows.isEmpty()) {
            return new LinkedList<>();
        }
        //printCSV(rows);
        System.out.println("processing");
        List<Integer> ignoredColumns = new ArrayList<>();

        String[] header = rows.get(0);
        Integer qtd_columns = header.length;
        Integer qtd_rows = rows.size();

        List<Set<ColumnIndexDTO>> uniqueTable = new LinkedList<>();
        Set<ColumnIndexDTO> set;

        //iterating columns
        for (int j = 0; j < qtd_columns; j++) {

            //Ignoring Columns that contains WKT, Time, Double OR Float data.
            if (ignoredColumns.contains(j)) {
                j++;
            }

            set = new TreeSet<>();

            //iterating current column's line
            for (int i = 1; i < qtd_rows; i++) {

                //get the value of current [line][column]
                try {
                    String[] currentRow = rows.get(i);

                    ColumnIndexDTO columnIndexDTO = analyzeRowColumn(currentRow, j, qtd_rows, ckanResource);

                    if (columnIndexDTO != null) {
                        set.add(columnIndexDTO);
                    } else {
                        ignoredColumns.add(j);
                        break;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println(TextColor.ANSI_RED + ex.getMessage() + TextColor.ANSI_BLACK);
                }
            }
            uniqueTable.add(set);
        }

        return uniqueTable;
    }

    private static ColumnIndexDTO analyzeRowColumn(String[] row, Integer column, Integer qtd_rows, CkanResource resource) {

        System.out.println(Arrays.toString(row));
        String value = row[column];

        if (isValidCell(value)) {
            count++; //this count is just a SERIAL KEY to each value.
            ColumnIndexDTO columnIndexDTO = new ColumnIndexDTO(count, value, qtd_rows, column, resource.getId(), resource.getPackageId());
            return columnIndexDTO;
        }

        return null;
    }

    public static boolean isValidCell(String cell) {

        if (ColumnCellValidation.isInteger(cell)) {
            return true;
        }
        return !(ColumnCellValidation.isDoubleOrFloat(cell)
                || ColumnCellValidation.isWKT(cell)
                || ColumnCellValidation.isADate(cell));
    }

    public static boolean isEmptyRow(String[] row) {

        if (row == null) {
            return true;
        }

        Boolean empty = true;
        for (String s : row) {
            String trim = s.trim();
            if (!trim.equals("")) {
                empty = false;
            }
        }
        return false;
    }

}
