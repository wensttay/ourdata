/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.resource;

/**
 *
 * @author kieckegard
 */
public class Column
{
    private int qtdDistinctValues;
    private String name;
    
    public Column(int distinctValues, String name){
        this.qtdDistinctValues = distinctValues;
        this.name = name;
    }

    public int getDistinctValues()
    {
        return qtdDistinctValues;
    }

    public String getName()
    {
        return name;
    }

    public void setDistinctValues(int distinctValues)
    {
        this.qtdDistinctValues = distinctValues;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Column{" + "distinctValues=" + qtdDistinctValues + ", name=" + name + '}';
    }
    
    
}
