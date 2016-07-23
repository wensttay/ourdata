/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.entity.utils.KeyPlaceUtils;
import br.ifpb.simba.ourdata.dao.entity.KeyPlaceBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.reader.KeyPlacesBo;
import br.ifpb.simba.ourdata.reader.TextColor;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.exceptions.JackanException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wensttay
 */
public class FinderKeyPlaceCSV{

    public static void main( String[] args ){

        KeyPlacesBo keyPlacesBo = new KeyPlacesBo(KeyPlacesBo.NUM_ROWS_CHECK_DEFAULT);
        final String CATALOG_URL = "http://dados.gov.br/";

        CkanClient ckanClient = new CkanClient(CATALOG_URL);
        KeyPlaceBdDao keyWordBdDao = new KeyPlaceBdDao();
        List<String> datasetNames = new ArrayList<>();

        try{
            datasetNames.addAll(ckanClient.getDatasetList());
        } catch ( JackanException ex ){
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        }

        int keyWordsSaved = 0;
        int sucess = 0;
        int totalResources = 0;
        int csvResources = 0;

//        Iterating dataset's names
        int auxDatasetNamesSize = datasetNames.size();

        for ( int i = 0; i < auxDatasetNamesSize; i++ ){
            CkanDataset dataset = null;
            List<CkanResource> resources = new ArrayList<>();
            try{
                dataset = ckanClient.getDataset(datasetNames.get(i));
                if ( dataset != null ){
                    resources.addAll(dataset.getResources());
                }
            } catch ( JackanException ex ){
                System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            }

//            Iterating resources
            int auxResourceSize = resources.size();
            for ( int j = 0; j < auxResourceSize; j++ ){
                CkanResource currentResource = resources.get(j);
                ++totalResources;
                List<KeyPlace> keyWords = null;

                try{
//                    Verify if the type of resource is a 'CSV'
                    if ( resources.get(j).getFormat().equals("CSV") ){
                        csvResources++;

                        System.out.println("=====================================================================================");
                        System.out.println("Index DataSet: " + i + " (" + datasetNames.get(i) + ")");
                        System.out.println("Resource_URL: " + resources.get(j).getUrl());

//                        Instancie a list of KeyPlace with the KeyWords of CSV resource
                        //keyWords = csv.getKeyPlaces(resources.get(j).getId(), resources.get(j).getUrl());
                        keyWords = keyPlacesBo.getKeyPlaces2(currentResource);
                        keyWords = KeyPlaceUtils.getLiteVersion(keyWords);

//                        Print a KeyPlace name and Number of repeat cases with de same place
//                        Collections.sort(keyWords, KeyPlace.getComparadorByName());
//                        for (KeyPlace keyWord : keyWords) {
//                            System.out.println(keyWord.getPlace().getNome() + " | Repetiu: " + keyWord.getRepeatNumber() + " Vezes");
//                        }
//                        Insert all the CSV's KeyPlace into DataBase
                        if ( keyWordBdDao.insertAll(keyWords) && !keyWords.isEmpty() ){
                            sucess++;
                            keyWordsSaved += keyWords.size();
                            System.out.println("Sucess: " + sucess);
                            System.out.println("Total de Resources: " + totalResources);
                            System.out.println("Total de Resources de Tipo CSV: " + csvResources);
                            System.out.println("Total de KeyWords salvos no Banco: " + keyWordsSaved);
                        }
                    }
                } catch ( IOException | OutOfMemoryError ex ){
                    System.out.println(TextColor.ANSI_RED.getCode() + " " + ex.getMessage());
                }
            }
        }
        System.out.println("Sucess: " + sucess);
        System.out.println("Total de Resources: " + totalResources);
        System.out.println("Total de Resources de Tipo CSV: " + csvResources);
        System.out.println("Total de KeyWords salvos no Banco: " + keyWordsSaved);
    }
}
