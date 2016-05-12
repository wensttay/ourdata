/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.resource.Column;
import br.ifpb.simba.ourdata.resource.ResourceHeader;
import br.ifpb.simba.ourdata.resource.ResourceUtil;

/**
 *
 * @author kieckegard
 */
public class TestResourceHeader
{
    public static void main(String[] args){
        String url = "http://dadosabertos.dataprev.gov.br/opendata/act10/formato=csv";
        ResourceHeader header = ResourceUtil.getHeader(url);
        for(Column column : header.getColumns())
            System.out.println(column.getName()+" : "+column.getDistinctValues());
        System.out.println("Total Rows: "+header.getQtdRows());
    }
}
