/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

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

        for (String name : dataset_names) {
            CkanDataset dataset = cc.getDataset(name);
            resources = dataset.getResources();
            //Iterating resources
            List<KeyWord> keyWordsResource = new ArrayList<>();
            for (CkanResource resource : resources) {
                if (resource.getFormat().equals("CSV")) {
                    System.out.println("=====================================================================================");
                    System.out.println("Dataset_URL: " + dataset.getUrl());
                    System.out.println("Resource_URL: " + resource.getUrl());
                    List<KeyWord> keyWords = csv.filterKeyWord(resource.getId(), resource.getUrl(), places);

                    List<KeyWord> liteVersion = new ArrayList<>();
                    if (!keyWords.isEmpty()) {
                        for (KeyWord keyWord : keyWords) {
                            boolean exist = false;
                            for (KeyWord lvKeyWord : liteVersion) {
                                if (lvKeyWord.equals(keyWord)) {
                                    lvKeyWord.setRepeatNumber(keyWord.getRepeatNumber() + lvKeyWord.getRepeatNumber());
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist) {
                                liteVersion.add(keyWord);
                            }
                        }
                    }
                    
                    Comparator<KeyWord> comparador = new Comparator<KeyWord>() {
                        public int compare(KeyWord s1, KeyWord s2) {
                            return s1.getPlace().getNome().compareTo(s2.getPlace().getNome());
                        }
                    };
                    
                    Collections.sort(liteVersion, comparador);
                    
                    for (KeyWord keyWord : liteVersion) {
                        System.out.println(keyWord.getPlace().getNome() + " | Repetiu: "+ keyWord.getRepeatNumber() + " Vezes");
                    }

                    keyWordsResource.addAll(liteVersion);
                }
            }
        }
    }
}
