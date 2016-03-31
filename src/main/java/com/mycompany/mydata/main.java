/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata;

import com.mycompany.mydata.dao.CkanDataSetBdDao;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanActivity;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanOrganization;
import eu.trentorise.opendata.jackan.model.CkanPair;
import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.jackan.model.CkanState;
import eu.trentorise.opendata.jackan.model.CkanTag;
import eu.trentorise.opendata.jackan.model.CkanTrackingSummary;
import eu.trentorise.opendata.jackan.model.CkanUser;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class main
{
    public static void main(String[] args){
        String url = "http://dados.gov.br/";
        CkanClient cc = new CkanClient(url);
        List<String> datasetlist = cc.getDatasetList();

            CkanDataset dataset = cc.getDataset(datasetlist.get(2));
            
            CkanDataSetBdDao cdsbd = new CkanDataSetBdDao();
            cdsbd.insert(dataset);
        
        
    }
}
