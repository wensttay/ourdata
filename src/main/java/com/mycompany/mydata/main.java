/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata;

import com.mycompany.mydata.dao.CkanDataSetBdDao;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class main {

    public static void main(String[] args) {
        String url = "http://dados.gov.br/";
        CkanClient cc = new CkanClient(url);
        CkanDataSetBdDao cdsbd = new CkanDataSetBdDao();
        List<String> datasetlist = cc.getDatasetList();
       
        for (String string : datasetlist) {
            CkanDataset dataset = cc.getDataset(string);
            cdsbd.insert(dataset);
        }
        
        

    }
}
