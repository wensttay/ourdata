/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata;


import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author kieckegard
 */
public class TestCSV
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static void main(String[] args)
    {
        CSVUtils csv = new CSVUtils();
        //String url = "http://dadosabertos.dataprev.gov.br/opendata/act10/formato=csv";
        CkanClient cc = new CkanClient("http://dados.gov.br/");
        List<String> dataset_names = cc.getDatasetList();
        List<CkanResource> resources;
        String url;
        int success_count=0;
        int error_count=0;
        int dataset_count=0;
        int csv_count=0;
        int count_row;
        //Iterating datasets
        for(String name : dataset_names){
            CkanDataset dataset = cc.getDataset(name);
            dataset_count++;
            resources = dataset.getResources();
            //Iterating resources
            for(CkanResource resource : resources){
                if(resource.getFormat().equals("CSV")){
                    System.out.println("=====================================================================================");
                    System.out.println("Dataset_URL: "+dataset.getUrl());
                    System.out.println("Resource_URL: "+resource.getUrl());
                    url = resource.getUrl();
                    csv_count++;
                    try
                    {
                        List<String[]> allcsv = csv.getAll(url);
                        count_row=0;
                        for(String[] row : allcsv){
                            count_row++;
                            if(count_row==1) System.out.println(ANSI_BLUE);
                            else System.out.println(ANSI_BLACK);
                            for (String r : row)
                                System.out.print(r+" | ");                 
                            System.out.println();
                            if(count_row == 3) break;
                        }
                        success_count++;
                        System.out.println(ANSI_GREEN+"!Success! "+ANSI_BLACK);
                    }
                    catch (OutOfMemoryError | IOException ex)
                    {
                        error_count++;
                        System.out.println(ANSI_RED+"Error: Couldn't open the URL ["+error_count+"]"+ANSI_BLACK);
                    }
                    
                    System.out.println("\nLOG: Dataset["+dataset_count+"], Resource_csv["+csv_count+"]"+ANSI_GREEN+" SUCCESS["+success_count+"], "+ANSI_RED+" ERROR["+error_count+"]."+ANSI_BLUE+" "
                            + "percent_sucess: ["+((success_count*100)/csv_count)+"%]"+ANSI_BLACK+"\n");
                }
            }
        }
        
        
        /*try{
            CSVUtils util = new CSVUtils();
        String url = "http://repositorio.dados.gov.br/trabalho/MTE_rela%c3%a7%c3%a3o%20de%20unidades%20e%20endere%c3%a7os.csv";
        List<String[]> list = util.getAllFromUrl(url);
        for(String[] line : list){
            for(String column : line){
                System.out.print(column+"---");
            }
            System.out.println();
        }
        }catch(Exception ex){
            
        }*/
        
    }
}
