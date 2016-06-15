/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.geo.KeyWordBdDao;
import br.ifpb.simba.ourdata.dao.geo.PlaceBdDao;
import br.ifpb.simba.ourdata.geo.KeyWord;
import br.ifpb.simba.ourdata.reader.CSVReader;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class TestKeyWordsCSV {

    public static void main(String[] args) {
        
        final String CATALOG_URL = "http://dados.gov.br/";
        final String PROPERTIES_PATH_GAZETTEER = "/banco/banco.gazetteer.properties";
        final String PROPERTIES_PATH_OURDATA = "/banco/banco.properties";
        CSVReader csv = new CSVReader();
        CkanClient ckanClient = new CkanClient(CATALOG_URL); 
        KeyWordBdDao keyWordBdDao = new KeyWordBdDao(PROPERTIES_PATH_OURDATA);

        List<String> datasetNames = ckanClient.getDatasetList();      
//        Connecting to the database that has a 'Gazetter' (list of Geo poss)
        PlaceBdDao placeBdDao = new PlaceBdDao(PROPERTIES_PATH_GAZETTEER);

//        Iterating dataset's names
        int auxDatasetNamesSize = datasetNames.size();
        for (int i = 22; i < auxDatasetNamesSize; i++) {
            CkanDataset dataset = ckanClient.getDataset(datasetNames.get(i));
            
            List<CkanResource> resources = new ArrayList<>();
            
            if (dataset != null) {
                resources.addAll(dataset.getResources());
            }        

//            Iterating resources
            int auxResourceSize = resources.size();
            for (int j = 0; j < auxResourceSize; j++) {
                try {
//                    Verify if the type of resource is a 'CSV'
                    if (resources.get(j).getFormat().equals("CSV")) {
                        System.out.println("=====================================================================================");
                        System.out.println("Index DataSet: " + i + " (" + datasetNames.get(i) + ")");
                        System.out.println("Resource_URL: " + resources.get(j).getUrl());

//                        Instancie a list of KeyWord with the KeyWords of CSV resource
                        List<KeyWord> keyWords = csv.getKeyWords(resources.get(j).getId(), resources.get(j).getUrl(), placeBdDao);
                        keyWords = KeyWordUtils.getLiteVersion(keyWords);  

//                        Print a KeyWord name and Number of repeat cases with de same place
                        Collections.sort(keyWords, KeyWord.comparadorByName);
                        for (KeyWord keyWord : keyWords) {
                            System.out.println(keyWord.getPlace().getNome() + " | Repetiu: " + keyWord.getRepeatNumber() + " Vezes");
                        }

//                        Insert all the CSV's KeyWord into DataBase
                      keyWordBdDao.insertAll(keyWords);
                    }
                } catch (OutOfMemoryError ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }
}
