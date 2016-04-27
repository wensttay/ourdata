/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.ckan.CkanResourceBdDao;
import br.ifpb.simba.ourdata.geonomes.Coordinate;
import br.ifpb.simba.ourdata.geonomes.Geonames;
import br.ifpb.simba.ourdata.reader.CSVReader;
import br.ifpb.simba.ourdata.reader.ExcelReader;
import br.ifpb.simba.ourdata.reader.XLSReader;
import br.ifpb.simba.ourdata.reader.XLSXReader;
import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.traceprov.geojson.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.geonames.Style;

/**
 *
 * @author kieckegard
 */
public class GeonamesTest
{

    public static void main(String[] args)
    {
        CkanResourceBdDao dao = new CkanResourceBdDao();
        List<CkanResource> resources = dao.list();
        String format;
        CSVReader csv = new CSVReader();
        ExcelReader excel;
        Geonames g = new Geonames("kieckegard", Style.MEDIUM);
        List<Sheet> sheets;
        Point p;
        Row row;
        Cell cell;
        Iterator<Row> rowIterator;
        Iterator<Cell> cellIterator;
        int i;
        for (CkanResource resource : resources)
        {
            format = resource.getFormat().toUpperCase();
            System.out.println("current format: "+format);
            switch (format)
            {
                case "CSVasdasd":
                    System.out.println("\n!!CSV file!!\n");
                    List<String[]> csv_files = csv.build(resource.getUrl());
                    if(csv_files == null) csv_files = new ArrayList<>();
                    i=0;
                    for (String[] linha : csv_files){
                        i++;
                        for (String celula : linha)
                            search(celula);
                        if(i==4)break;
                    }
                    break;
                case "XLSasd":
                    System.out.println("\n!!XLS file!!\n");
                    excel = new XLSReader();
                    System.out.println("Building Excel file...");
                    sheets = excel.build(resource.getUrl());
                    if(sheets == null) sheets = new ArrayList<>();
                    System.out.println("Done!");
                    for(Sheet s : sheets){
                        i=0;
                        rowIterator = s.iterator();
                        while(rowIterator.hasNext()){
                            System.out.println("Start iterating row...");
                            i++;
                            row = rowIterator.next();
                            cellIterator = row.iterator();
                            while(cellIterator.hasNext()){
                                System.out.println("Start iterating cell...");
                                cell = cellIterator.next();
                                if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                                    search(cell.getStringCellValue());
                                else
                                    System.out.println("Non-String cell type!");
                            }
                            if(i==4)break;
                        }
                    }
                    break;
                case "XLSX":
                    System.out.println("\n!!XLSX file!!\n");
                    excel = new XLSXReader();
                    sheets = excel.build(resource.getUrl());
                    if(sheets == null) sheets = new ArrayList<>();
                    for(Sheet s : sheets){
                        i=0;
                        rowIterator = s.iterator();
                        while(rowIterator.hasNext()){
                            i++;
                            row = rowIterator.next();
                            cellIterator = row.iterator();
                            while(cellIterator.hasNext()){
                                cell = cellIterator.next();
                                if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                                    search(cell.getStringCellValue());
                            }
                            if(i==4)break;
                        }
                    }
                    break;
            }
        }
    }

    public static void search(String city)
    {
        Geonames g = new Geonames("kieckegard", Style.MEDIUM);
        try
        {
            System.out.println("Searching for " + city + "... ");
            Coordinate c = g.search(city);            
            if (c != null){
                System.out.println("Found! Latidude: " + c.getLatitude() + " | Longitude: " + c.getLongitude());
            }
            else{
                System.out.println("City not found!");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
