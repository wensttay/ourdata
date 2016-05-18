/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.resource;

import br.ifpb.simba.ourdata.reader.ExcelReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author kieckegard
 */
public class ResourceUtilExcel implements IResourceHeader<List<ResourceHeader>>
{
    private ExcelReader reader;
    
    public ResourceUtilExcel(ExcelReader er){
        this.reader = er;
    }
    
    @Override
    public List<ResourceHeader> getHeader(String url)
    {
        int qtdRows,qtdColumns;
        Set<String> set;
        List<Sheet> sheets = reader.build(url);
        if(sheets == null) sheets = new ArrayList<>();
        List<ResourceHeader> resourcesHeaders = new ArrayList<>();
        ResourceHeader resourceHeader;
        List<Column> columns = new ArrayList<>();
        for(Sheet sheet : sheets){
            if(!sheet.isDisplayGridlines()) break;
            List<Row> rows = IteratorUtils.toList(sheet.iterator());
            //getting the number of rows in that sheet.
            qtdRows = rows.size();
            Row first_row = rows.get(0);
            //getting the number of cells in the first row.
            qtdColumns = first_row.getLastCellNum();
            System.out.println("Quantidade de Colunas: "+qtdColumns);
            System.out.println("Quantidade de Linhas: "+qtdRows);
            for(int j=0;j<qtdColumns;j++){
                set = new TreeSet<>();
                for(int i=0;i<qtdRows;i++){
                    Cell cell = rows.get(i).getCell(j);
                    System.out.println(cell);
                    if(cell.getCellType() == Cell.CELL_TYPE_STRING){     
                        set.add(cell.getStringCellValue());
                    }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                        set.add(String.valueOf(cell.getNumericCellValue()));
                    else break;
                }
                Column column = new Column(set.size(),first_row.getCell(j).getStringCellValue());
                columns.add(column);
            }
            resourceHeader = new ResourceHeader();
            resourceHeader.setColumns(columns);
            resourceHeader.setQtdRows(qtdRows);
            resourcesHeaders.add(resourceHeader);
        }
        return resourcesHeaders;
    }
    
    public void setReader(ExcelReader er){
        this.reader = er;
    }   
}
