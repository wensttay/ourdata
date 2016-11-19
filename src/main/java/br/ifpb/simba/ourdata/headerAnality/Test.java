/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import br.ifpb.simba.ourdata.dao.ckan.CkanResourceBdDao;
import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



/**
 *
 * @author Pedro Arthur
 */
public class Test {
    
    public static void main(String[] args) {
        
        CkanResourceBdDao resourceBdDao = new CkanResourceBdDao();
        
        List<CkanResource> resources = resourceBdDao.getAll();
        
        Integer count = 0;
        
        for(CkanResource resource : resources) {
            
            count++;
            
            System.out.println("URL TYPE: "+resource.getFormat());
            if(resource.getFormat().equals("CSV")){

                List<Set<ColumnIndexDTO>> result = ColumnIndexProcessor.process(resource);
                for (Iterator<Set<ColumnIndexDTO>> it = result.iterator(); it.hasNext();) {
                    Set<ColumnIndexDTO> column = it.next();
                    column.forEach((columnIndex) -> {
                        System.out.println(columnIndex);
                    });
                }
            }
            
            if(count.equals(20)) break;
            
            
        }
       
    }
}
