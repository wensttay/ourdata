/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.ckan.CkanDataSetBdDao;
import br.ifpb.simba.ourdata.dao.ckan.CkanResourceBdDao;
import br.ifpb.simba.ourdata.dao.entity.KeyPlaceBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.utils.KeyPlaceUtils;
import br.ifpb.simba.ourdata.reader.KeyPlaceBo;
import br.ifpb.simba.ourdata.reader.TextColor;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Wensttay
 */
public class FinderKeyPlaceCSV {

    public static void main(String[] args) {

        KeyPlaceBo keyPlacesBo = new KeyPlaceBo(KeyPlaceBo.NUM_ROWS_CHECK_DEFAULT);

        CkanResourceBdDao resourceBdDao = new CkanResourceBdDao();
        CkanDataSetBdDao dataSetBdDao = new CkanDataSetBdDao();

        KeyPlaceBdDao keyPlaceBdDao = new KeyPlaceBdDao();
        List<CkanDataset> datasets = new ArrayList<>();

        int keyPlacesSaved = 0;
        int sucess = 0;
        int totalResources = 0;
        int csvResources = 0;

        datasets.addAll(dataSetBdDao.getAll());
        int datasetSize = datasets.size();

//        Iterating dataset's datasets
        for ( int i = 0; i < datasetSize; i++ ){
            CkanDataset currentDataset = datasets.get(i);
            List<CkanResource> resources = new ArrayList<>();
            resources.addAll(resourceBdDao.searchByDatasetId(currentDataset.getId()));

//            Iterating resources
            int auxResourceSize = resources.size();
            for (int j = 0; j < auxResourceSize; j++) {
                CkanResource currentResource = resources.get(j);
                ++totalResources;
                List<KeyPlace> keyPlaces = new ArrayList<>();

                try {
//                    Verify if the type of resource is a 'CSV'
                    if (resources.get(j).getFormat().equals("CSV")) {
                        csvResources++;

                        System.out.println("=====================================================================================");
                        System.out.println("Index DataSet: " + i + " (" + datasets.get(i).getName() + ")");
                        System.out.println("Resource_URL: " + resources.get(j).getUrl());

//                        Instancie a list of KeyPlace with the keyPlaces of CSV resource
                        keyPlaces.addAll(keyPlacesBo.getKeyPlaces(currentResource, currentDataset));
                        keyPlaces = KeyPlaceUtils.getLiteVersion(keyPlaces);

//                        Print a KeyPlace name and Number of repeat cases with de same place
                        Collections.sort(keyPlaces, KeyPlace.getComparadorByName());
                        for (KeyPlace keyPlace : keyPlaces) {
                            System.out.println(keyPlace.getPlace().getNome() + " | Repetiu: " + keyPlace.getRepeatNumber() + " Vezes");
                        }
//                        Insert all the CSV's KeyPlace into DataBase
                        if (!keyPlaces.isEmpty()) {
                            System.out.println("Inserindo keyPlaces ...");
                        } else {
                            System.out.println("Nenhuma keyPlace foi encontrada!");
                        }

                        if (!keyPlaces.isEmpty() && keyPlaceBdDao.insertAll(keyPlaces)) {
                            sucess++;
                            keyPlacesSaved += keyPlaces.size();
                            System.out.println("Sucess: " + sucess);
                            System.out.println("Total de Resources: " + totalResources);
                            System.out.println("Total de Resources de Tipo CSV: " + csvResources);
                            System.out.println("Total de keyPlaces salvos no Banco: " + keyPlacesSaved);
                        }
                    }
                } catch (IOException | OutOfMemoryError ex) {
                    System.out.println(TextColor.ANSI_RED.getCode() + " " + ex.getMessage());
                }
            }
        }
        System.out.println("Sucess: " + sucess);
        System.out.println("Total de Resources: " + totalResources);
        System.out.println("Total de Resources de Tipo CSV: " + csvResources);
        System.out.println("Total de keyPlaces salvos no Banco: " + keyPlacesSaved);
    }
}
