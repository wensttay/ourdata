/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import br.ifpb.simba.ourdata.dao.entity.ColumnIndexDTOCassandraImpl;
import br.ifpb.simba.ourdata.dao.entity.ColumnIndexDao;
import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pedro Arthur
 */
public class CSVColumnAnalyzer {
    
    private final ColumnIndexDao columnIndexDao;
    
    public CSVColumnAnalyzer() {
        columnIndexDao = new ColumnIndexDTOCassandraImpl();
    }
   
    public void analyze(List<CkanDataset> datasets) {
        
        datasets.forEach((dataset) -> {
            dataset.getResources().stream().filter((resource) -> (resource.getUrlType().equals("CSV"))).forEachOrdered((resource) -> {
                ColumnIndexProcessor.process(resource).forEach((set) -> {
                    set.forEach((dto) -> {
                        columnIndexDao.insert(dto);
                    });
                });
            });
        });
    }
    
    
}
