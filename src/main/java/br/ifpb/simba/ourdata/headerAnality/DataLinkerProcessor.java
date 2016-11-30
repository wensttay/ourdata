/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import br.ifpb.simba.ourdata.dao.entity.DataRelationDao;
import br.ifpb.simba.ourdata.dao.entity.DataRelationPostgresImpl;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;

import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import br.ifpb.simba.ourdata.entity.DataRelation;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kieckegard
 */
public class DataLinkerProcessor {
    
    
    private final List<ColumnIndexDTO> listColumnIndexDTO;
    private DataRelationDao dataRelationDao;
    
    public DataLinkerProcessor(List<ColumnIndexDTO> listColumnIndexDTO) {
        this.listColumnIndexDTO = listColumnIndexDTO;
        this.dataRelationDao = new DataRelationPostgresImpl();
    }
    
    public void startProcess() {

        Set<ResourceColumn> nonPKColumns = new TreeSet<>();
        Set<ResourceColumn> alreadyVerifiedPKColumns = new TreeSet<>();
        
        for(ColumnIndexDTO columnIndexDTO : this.listColumnIndexDTO) {
            
            ResourceColumn resourceColumn = new ResourceColumn(columnIndexDTO.getResourceId(), columnIndexDTO.getColumnNumber());
            
            if(!nonPKColumns.contains(resourceColumn)) {
                if(!alreadyVerifiedPKColumns.contains(resourceColumn)) {
                    
                    if(verifyPrimaryKey(columnIndexDTO)) {
                        alreadyVerifiedPKColumns.add(resourceColumn);
                        //insere as relações
                        findAndPersistDataRelation(columnIndexDTO);
                        
                    } else {
                        nonPKColumns.add(resourceColumn);
                    }
                } else {
                    //insere as relações
                    findAndPersistDataRelation(columnIndexDTO);
                }
            } else {
            }
        }
    }
    
    private void findAndPersistDataRelation(ColumnIndexDTO current) {
       
        
        System.out.println("Procurando relações com o valor: '"+current.getValue()+"', do resource: "+current.getResourceId());
        for(ColumnIndexDTO columnIndex : listColumnIndexDTO) {
            
            if(!current.getResourceId().equals(columnIndex.getResourceId())) {
                
                if(current.getValue().equals(columnIndex.getValue())) {
                    
                    System.out.println("Achou relação com '"+columnIndex.getValue()+"', do resource: "+columnIndex.getResourceId());
                    DataRelation dataRelation = new DataRelation(current.getValue(),current.getColumnNumber(),
				current.getResourceId(), columnIndex.getValue(), columnIndex.getColumnNumber(),
				columnIndex.getResourceId());
                    
                    
                    //persistir
                    dataRelationDao.insert(dataRelation);
                    
                }
            }
        }
    }
    
    private boolean verifyPrimaryKey(ColumnIndexDTO columnIndexDTO) {
        System.out.println("===================Verifying Primary Key, resource: '"+columnIndexDTO.getResourceId()+"'======================");
        System.out.println("");
        Integer uniqueCount = 1;
        
        for(ColumnIndexDTO aux : this.listColumnIndexDTO) {
            
            if(columnIndexDTO.getResourceId().equals(aux.getResourceId()) 
                    && !columnIndexDTO.getValue().equals(aux.getValue())
                    && columnIndexDTO.getColumnNumber().equals(aux.getColumnNumber())) {

		uniqueCount += 1;
	    }
        }
        
        uniqueCount++;
        
        System.out.println("Valores únicos = "+uniqueCount);
        System.out.println("Números de linhas do resource = "+columnIndexDTO.getRowsCount());
        System.out.println("=================================================================================================================");
        
        return uniqueCount.equals(columnIndexDTO.getRowsCount());
        
    }
    
    
}
