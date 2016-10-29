/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import br.ifpb.simba.ourdata.reader.CSVReaderOD;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Pedro Arthur
 */
public class ColumnIndexProcessor {
    
    private static Long count;
    
    public static List<Set<ColumnIndexDTO>> process(CkanResource ckanResource) {
        
        String url = ckanResource.getUrl();
        List<String[]> rows = new CSVReaderOD().build(url);
        List<ColumnHeader> columns = new ArrayList<>();

        String[] header = rows.get(0);
        Integer qtd_columns = header.length;
        Integer qtd_rows = rows.size() - 1;
        
        List<Set<ColumnIndexDTO>> uniqueTable = new LinkedList<>();
        Set<ColumnIndexDTO> set;
        
        for ( int j = 0; j < qtd_columns; j++ ){
            
            set = new TreeSet<>();
            for ( int i = 1; i < qtd_rows; i++ ){
                
                String value = rows.get(i)[j];
                
                if(isValidCell(value)){
                    count++;
                    ColumnIndexDTO columnIndexDTO = new ColumnIndexDTO(count, value, qtd_rows, j, ckanResource.getId(), ckanResource.getPackageId());
                    set.add(columnIndexDTO);
                }
            }
            uniqueTable.add(set);            
        }
        
        return uniqueTable;
    }
    
    private static boolean isValidCell(String cell) {
        try {
            
            Integer i = Integer.parseInt(cell);    
            
            return true;
            
        } catch(NumberFormatException ex) {            
            try {
                
                WKTReader reader = new WKTReader();
                reader.read(cell);
                
                return false; 
                
            } catch(ParseException pe) {
                //verify date
                return true;
            }
        }
    }
}
