/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.resource.Column;
import br.ifpb.simba.ourdata.resource.IResourceHeader;
import br.ifpb.simba.ourdata.resource.ResourceHeader;
import br.ifpb.simba.ourdata.resource.ResourceUtilCsv;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class TestResourceHeader
{
    public static void main(String[] args){
        CkanClient cc = new CkanClient("http://dados.gov.br/");
        List<String> dataset_names = cc.getDatasetList();
        List<CkanResource> resources;
        
        for(String dataset_name : cc.getDatasetList()){
            CkanDataset dataset = cc.getDataset(dataset_name);
            for(CkanResource resource : dataset.getResources()){
                if(resource.getFormat().equals("CSV")){
                    String url = resource.getUrl();
                    IResourceHeader<ResourceHeader> headerBuilder = new ResourceUtilCsv();
                    ResourceHeader header = headerBuilder.getHeader(url);
                    if(header != null){
                        System.out.println("Quantidade de linhas: "+header.getQtdRows());
                        System.out.println("Quantidade de Colunas: "+header.getColumns().size());
                        System.out.println("---------Attributes---------");
                        for(Column column : header.getColumns()){
                            System.out.println("> "+column.getName()+" : "+column.getDistinctValues());
                        }
                    }else System.out.println("Couldn't open URL!");
                    System.out.println("\n");
                }
                
            }
        }
       
    }
}
