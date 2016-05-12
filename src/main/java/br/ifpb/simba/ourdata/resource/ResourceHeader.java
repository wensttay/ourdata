/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.resource;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class ResourceHeader
{
    private List<Column> columns;
    private int qtdRows;
    
    public ResourceHeader(){
        columns = new ArrayList<>();
        qtdRows = 0;
    }
    
    public ResourceHeader(List<Column> columns, int qtdRows){
        this.columns = columns;
        this.qtdRows = qtdRows;
    }
    
    public void setColumns(List<Column> columns){
        this.columns = columns;
    }
    
    public List<Column> getColumns(){
        return columns;
    }
    
    public int getQtdRows(){
        return qtdRows;
    }
    
    public void setQtdRows(int qtdRows){
        this.qtdRows = qtdRows;
    }

    @Override
    public String toString()
    {
        return "ResourceHeader{" + "columns=" + columns + ", qtdRows=" + qtdRows + '}';
    }
    
    
    
}
