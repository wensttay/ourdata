/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author kieckegard
 */
public abstract class ExcelReader implements Reader<List<Sheet>, String>
{   
    @Override
    public void print(String url){
        List<Sheet> sheets = build(url);
        Row row;
        Cell cell;
        Iterator<Row> rowIterator;
        Iterator<Cell> cellIterator;
        for(Sheet s : sheets){
            rowIterator = s.iterator();
            while(rowIterator.hasNext()){
                row = rowIterator.next();
                cellIterator = row.iterator();
                while(cellIterator.hasNext()){
                    cell = cellIterator.next();
                    switch(cell.getCellType()){
                        case Cell.CELL_TYPE_STRING:
                            System.out.println(cell.getStringCellValue()+" | ");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.println(cell.getNumericCellValue()+" | ");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.println(cell.getBooleanCellValue()+" | ");
                            break;
                    }
                }
            }
        }
    }
}
