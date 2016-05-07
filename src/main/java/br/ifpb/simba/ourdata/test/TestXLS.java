/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.ExcelReader;
import br.ifpb.simba.ourdata.reader.XLSReader;
import br.ifpb.simba.ourdata.reader.XLSXReader;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_BLACK;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_BLUE;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_GREEN;
import static br.ifpb.simba.ourdata.test.TestCSV.ANSI_RED;
import static br.ifpb.simba.ourdata.test.TestCSV.error_count;
import static br.ifpb.simba.ourdata.test.TestCSV.success_count;
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
public class TestXLS
{
    public static void main(String[] args)
    {
        XLSReader xlsr = new XLSReader();
        CkanClient cc = new CkanClient("http://dados.gov.br/");
        List<String> dataset_names = cc.getDatasetList();
        List<CkanResource> resources;
        String url;
        int dataset_count = 0;
        int xls_count = 0;
        NumberFormat formatter = new DecimalFormat("#0.00");
        
        //Iterating datasets
        for (String name : dataset_names) {
            CkanDataset dataset = cc.getDataset(name);
            dataset_count++;
            resources = dataset.getResources();
            //Iterating resources
            for (CkanResource resource : resources) {
                if (resource.getFormat().equals("XLS")) {
                    System.out.println("=====================================================================================");
                    System.out.println("Dataset_URL: " + dataset.getUrl());
                    System.out.println("Resource_URL: " + resource.getUrl());
                    url = resource.getUrl();
                    xls_count++;
                    xlsr.print(url);
                    
                    float percentSucess = (((float) success_count * 100) / (float) xls_count);
                    System.out.println("\nLOG: Dataset[" + dataset_count + "], Resource_csv[" + xls_count + "]" + ANSI_GREEN + " SUCCESS[" + success_count + "], " + ANSI_RED + " ERROR[" + error_count + "]." + ANSI_BLUE + " "
                            + "percent_sucess: [" + formatter.format(percentSucess) + " %]" + ANSI_BLACK + "\n");
                }
            }
        }
        
    }
}
