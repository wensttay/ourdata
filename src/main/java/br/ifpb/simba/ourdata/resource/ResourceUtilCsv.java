/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.resource;

import br.ifpb.simba.ourdata.reader.CSVReader;
import br.ifpb.simba.ourdata.resource.Column;
import br.ifpb.simba.ourdata.resource.ResourceHeader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author kieckegard
 */
public class ResourceUtilCsv implements IResourceHeader<ResourceHeader>
{
    /**
     * Só funciona para arquivos CSV por enquanto!
     * @param url 
     * @return ResourceHeader, classe que contém informações sobre o arquivo.
     */
    @Override
    public ResourceHeader getHeader(String url){
        CSVReader reader = new CSVReader();
        List<String[]> rows = reader.build(url);
        Set<String> set;
        List<Column> columns = new ArrayList<>();
        String[] header = rows.get(0);
        int qtd_columns = header.length;
        System.out.println("colunas: "+qtd_columns);
        int qtd_rows = rows.size()-1;
        System.out.println("linhas: "+qtd_rows);
        
        for(int j=0;j<qtd_columns;j++){
            set = new TreeSet<>();
            for(int i=1;i<qtd_rows;i++){
                String cell = rows.get(i)[j];
                set.add(cell);
            }
            Column column = new Column(set.size(),header[j]);
            columns.add(column);
        }
        
        return new ResourceHeader(columns,qtd_rows);
    }
}
