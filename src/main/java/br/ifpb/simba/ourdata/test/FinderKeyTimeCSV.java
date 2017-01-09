package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.ckan.CkanDataSetBdDao;
import br.ifpb.simba.ourdata.dao.ckan.CkanResourceBdDao;
import br.ifpb.simba.ourdata.dao.entity.KeyTimeBdDao;
import br.ifpb.simba.ourdata.entity.KeyTime;
import br.ifpb.simba.ourdata.entity.utils.KeyTimeUtils;
import br.ifpb.simba.ourdata.reader.KeyTimeBo;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.JDOMException;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class FinderKeyTimeCSV {

    public static void main(String[] args) {

        KeyTimeBo keyTimeBo = new KeyTimeBo(KeyTimeBo.NUM_ROWS_CHECK_DEFAULT);

        CkanResourceBdDao resourceBdDao = new CkanResourceBdDao();
        CkanDataSetBdDao dataSetBdDao = new CkanDataSetBdDao();

        KeyTimeBdDao keyPlaceBdDao = new KeyTimeBdDao();
        List<CkanDataset> datasets = new ArrayList<>();

        int keyTimesSaved = 0;
        int sucess = 0;
        int totalResources = 0;
        int csvResources = 0;

        datasets.addAll(dataSetBdDao.getAll());
        int datasetSize = datasets.size();

//        Iterating dataset's datasets
        for (int i = 0; i < datasetSize; i++) {
            CkanDataset currentDataset = datasets.get(i);
            List<CkanResource> resources = new ArrayList<>();
            resources.addAll(resourceBdDao.searchByDatasetId(currentDataset.getId()));

//            Iterating resources
            int auxResourceSize = resources.size();
            for (int j = 0; j < auxResourceSize; j++) {
                CkanResource currentResource = resources.get(j);
                ++totalResources;
                List<KeyTime> keyTimes = new ArrayList<>();

                try {
//                    Verify if the type of resource is a 'CSV'
                    if (resources.get(j).getFormat().equals("CSV")) {
                        csvResources++;

                        System.out.println("=====================================================================================");
                        System.out.println("Index DataSet: " + i + " (" + datasets.get(i).getName() + ")");
                        System.out.println("Resource_URL: " + resources.get(j).getUrl());

//                        Instancie a list of KeyTime with the keyPlaces of CSV resource
                        keyTimes.addAll(keyTimeBo.getKeyTimes(currentResource, currentDataset));
                        keyTimes = KeyTimeUtils.getLiteVersion(keyTimes);

//                        Pri   nt a KeyTime name and Number of repeat cases with de same place
//                        Collections.sort(keyTimes, KeyTime.getComparadorByName());
//                        for (KeyTime keyTime : keyTimes) {
//                            System.out.println(keyTime.getPeriod().toString() + " | Repetiu: " + keyTime.getRepeatNumber() + " Vezes");
//                        }
//                        Insert all the CSV's KeyTime into DataBase
                        if (!keyTimes.isEmpty()) {
                            System.out.println("Inserindo keyTimes ...");
                        } else {
                            System.out.println("Nenhuma keyTimes foi encontrada!");
                        }

                        if (!keyTimes.isEmpty() && keyPlaceBdDao.insertAll(keyTimes)) {
                            sucess++;
                            keyTimesSaved += keyTimes.size();
                            System.out.println("Sucess: " + sucess);
                            System.out.println("Total de Resources: " + totalResources);
                            System.out.println("Total de Resources de Tipo CSV: " + csvResources);
                            System.out.println("Total de keyPlaces salvos no Banco: " + keyTimesSaved);
                        }
                    }
                } catch (OutOfMemoryError | IOException | JDOMException | DocumentCreationTimeMissingException ex) {
//                    System.out.println(TextColor.ANSI_RED.getCode() + " " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("Sucess: " + sucess);
        System.out.println("Total de Resources: " + totalResources);
        System.out.println("Total de Resources de Tipo CSV: " + csvResources);
        System.out.println("Total de keyPlaces salvos no Banco: " + keyTimesSaved);
    }
}
