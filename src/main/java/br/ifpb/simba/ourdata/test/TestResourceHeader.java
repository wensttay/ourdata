/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.XLSReader;
import br.ifpb.simba.ourdata.resource.Column;
import br.ifpb.simba.ourdata.resource.IResourceHeader;
import br.ifpb.simba.ourdata.resource.ResourceHeader;
import br.ifpb.simba.ourdata.resource.ResourceUtilExcel;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class TestResourceHeader
{
    public static void main(String[] args){
        String url = "http://www.capes.gov.br/images/stories/download/avaliacaotrienal/planilhascomparativastrienal2007/Odontologia.xls";
        IResourceHeader<List<ResourceHeader>> util = new ResourceUtilExcel(new XLSReader());
        List<ResourceHeader> headers = util.getHeader(url);
        for(ResourceHeader header : headers){
            for(Column column : header.getColumns())
                System.out.println(column.getName()+" : "+column.getDistinctValues());
            System.out.println("Total Rows: "+header.getQtdRows());
        }
       
    }
}
