/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.geo.KeyWordBdDao;
import br.ifpb.simba.ourdata.dao.geo.PlaceBdDao;
import br.ifpb.simba.ourdata.geo.KeyWord;
import br.ifpb.simba.ourdata.geo.Place;
import br.ifpb.simba.ourdata.reader.CSVReader;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class TestKeyWordsCSV {

    public static void main(String[] args) {
        PlaceBdDao placeBdDao = new PlaceBdDao("/banco/banco.gazetteer.properties");
        List<Place> places = new ArrayList<>();
        places.addAll(placeBdDao.getAll());

//      TESTANDO PRINT POINTS  
//        int cont = 1;
//        for (Place place : places) {
//            Geometry geometry = place.getWay().getGeometry();
//            for (int i = 0; i < geometry.numPoints(); i++) {
//                System.out.println(geometry.getPoint(i).toString());
//            }
//            
//            System.out.println("\n=========================================\n");
//            cont++;
//            if(cont == 100)
//                break;
//        }

        CSVReader csv = new CSVReader();
        CkanClient cc = new CkanClient("http://dados.gov.br/");
        List<String> dataset_names = cc.getDatasetList();
        List<CkanResource> resources;
        CkanDataset dataset = null;
        KeyWordBdDao keyWordBdDao = new KeyWordBdDao("/banco/banco.properties");

        for (int i = 0; i < dataset_names.size(); i++) {

            dataset = cc.getDataset(dataset_names.get(i));
            resources = dataset.getResources();

            //Iterating resources
            for (int j = 0; j < resources.size(); j++) {
                try {
//                    Verify if the type of resource is a 'CSV'
                    if (resources.get(j).getFormat().equals("CSV")) {
                        System.out.println("=====================================================================================");
                        System.out.println("Index DataSet: " + i + " (" + dataset_names.get(i) + ")");
                        System.out.println("Resource_URL: " + resources.get(j).getUrl());

//                        Instancie a list of KeyWord with the KeyWords of CSV resource
                        List<KeyWord> keyWords = csv.getKeyWords(resources.get(j), resources.get(j).getUrl());

//                        Instancie a list with a 'lite Version' of result seach KeyWords's resource.
//                        This list has a same KeyWords of result search, but KeyWords with same place are
//                        joined in one KeyWord
                        List<KeyWord> liteVersion = new ArrayList<>();
                        if (!keyWords.isEmpty()) {
                            for (KeyWord keyWord : keyWords) {
                                boolean exist = false;
                                for (KeyWord liteVersionAux : liteVersion) {
                                    if (liteVersionAux.equals(keyWord)) {
                                        liteVersionAux.setRepeatNumber(keyWord.getRepeatNumber() + liteVersionAux.getRepeatNumber());
                                        exist = true;
                                        break;
                                    }
                                }
                                if (!exist) {
                                    liteVersion.add(keyWord);
                                }
                            }
                        }

//                        Instancie a comparator usign a Name's KeyWord
                        Comparator<KeyWord> comparador = new Comparator<KeyWord>() {
                            @Override
                            public int compare(KeyWord s1, KeyWord s2) {
                                return s1.getPlace().getNome().compareTo(s2.getPlace().getNome());
                            }
                        };
                        Collections.sort(liteVersion, comparador);

//                        Print a KeyWord name and Number of repeat cases with de same place
                        for (KeyWord keyWord : liteVersion) {
                            System.out.println(keyWord.getPlace().getNome() + " | Repetiu: " + keyWord.getRepeatNumber() + " Vezes");
                        }

//                        Insert all the CSV's KeyWord into DataBase
                        keyWordBdDao.insertAll(liteVersion);
                    }
                } catch (OutOfMemoryError ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }
}
