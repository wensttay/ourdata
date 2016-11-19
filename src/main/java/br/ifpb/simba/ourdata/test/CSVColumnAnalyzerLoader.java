/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.ckan.CkanDataSetBdDao;
import br.ifpb.simba.ourdata.dao.ckan.CkanResourceBdDao;
import br.ifpb.simba.ourdata.headerAnality.CSVColumnAnalyzer;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class CSVColumnAnalyzerLoader {
    
    public static void main(String[] args) {
        
        CkanResourceBdDao dao = new CkanResourceBdDao();
        
        List<CkanResource> resources = dao.getAll();
        
        CSVColumnAnalyzer csvColumnAnalyzer = new CSVColumnAnalyzer();
        
        csvColumnAnalyzer.analyze(resources);
    }
}
