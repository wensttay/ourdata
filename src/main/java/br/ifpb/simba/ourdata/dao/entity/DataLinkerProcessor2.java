/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.DataRelation;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class DataLinkerProcessor2 {
    
    private final List<ResourceColumnIndex> resources;
    private final DataRelationDao dataRelationDao;
    
    public DataLinkerProcessor2(List<ResourceColumnIndex> resources) {
        this.resources = resources;
        this.dataRelationDao = new DataRelationPostgresImpl();
    }
    
    public void startProcess() {
        
        for(ResourceColumnIndex possiblePKResource : this.resources){
            
            for(ColumnIndex possiblePKColumn : possiblePKResource.getColumns()){
                
                if(verifyPrimaryKey(possiblePKColumn,possiblePKResource.getRowsQty())) {
                    
                    for(ResourceColumnIndex possibleFKResource : this.resources){
                        
                        if(!possiblePKResource.getResourceId().equals(possibleFKResource.getResourceId())) {
                            
                            for(ColumnIndex possibleFKColumn : possibleFKResource.getColumns()){
                                
                                if(possiblePKColumn.getValues().containsAll(possibleFKColumn.getValues())) {
                                    persistRelation(possiblePKResource.getResourceId(), possiblePKColumn, 
                                            possibleFKResource.getResourceId(), possibleFKColumn);
                                }
                            }
                        }
                    }
                }
                
            }
            
        }
    }
    
    private void persistRelation(String pkResourceId, ColumnIndex pk, String fkResourceId, ColumnIndex fk) {
        
        for(String pkValue : pk.getValues()) {
            
            for(String fkValue : fk.getValues()){
                
                if(pkValue.equals(fkValue)) {
                    DataRelation dataRelation = new DataRelation(pkValue,pk.getColumnNumber(),pkResourceId,
                            fkValue,fk.getColumnNumber(),fkResourceId);
                    
                    dataRelationDao.insert(dataRelation);
                }
            }
        }
    }
    
    private Boolean verifyPrimaryKey(ColumnIndex column, Integer rowsQty) { 
        return rowsQty.equals(column.getValues().size());
    }
    
    
    
    
    
}
