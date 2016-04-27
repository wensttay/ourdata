/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.CSVReader;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class TestCSV {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static int error_count = 0;
    public static int success_count = 0;

    public static void main(String[] args) {
        CSVReader csv = new CSVReader();
        //String url = "http://dadosabertos.dataprev.gov.br/opendata/act10/formato=csv";
        CkanClient cc = new CkanClient("http://dados.gov.br/");
        List<String> dataset_names = cc.getDatasetList();
        List<CkanResource> resources;
        String url;
        int dataset_count = 0;
        int csv_count = 0;
        NumberFormat formatter = new DecimalFormat("#0.00");
        
        //Iterating datasets
        for (String name : dataset_names) {
            CkanDataset dataset = cc.getDataset(name);
            dataset_count++;
            resources = dataset.getResources();
            //Iterating resources
            for (CkanResource resource : resources) {
                if (resource.getFormat().equals("CSV")) {
                    System.out.println("=====================================================================================");
                    System.out.println("Dataset_URL: " + dataset.getUrl());
                    System.out.println("Resource_URL: " + resource.getUrl());
                    url = resource.getUrl();
                    csv_count++;
                    csv.print(url);
                    
                    float percentSucess = (((float) success_count * 100) / (float) csv_count);
                    System.out.println("\nLOG: Dataset[" + dataset_count + "], Resource_csv[" + csv_count + "]" + ANSI_GREEN + " SUCCESS[" + success_count + "], " + ANSI_RED + " ERROR[" + error_count + "]." + ANSI_BLUE + " "
                            + "percent_sucess: [" + formatter.format(percentSucess) + " %]" + ANSI_BLACK + "\n");
                }
            }
        }

    }
}
