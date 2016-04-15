/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.JdomReader;
import br.ifpb.simba.ourdata.reader.URLUtils;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.exceptions.CkanException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class TesteJsomPrintAll {

    public static int funcionou = 0;
    public static int total = 0;

//There is a file named 'netbeans.conf' which can be found in <NetBeans_home>etc which you can 
//edit to increase the amount of memory that NetBeans runs with. Edit the property named 
//'netbeans_default_options'. Increasing the -Xms and -Xmx options will increase the amount 
//of RAM available to the JVM that NetBeans will be running inside of.
//
//Example:
//    netbeans_default_options="-J-Xms384m -J-Xmx512m -J-XX:PermSize=32m -J-XX:MaxPermSize=96m -J-Xverify:none"
//
//This example will allow NetBeans to run with a minimum of 384 megabytes of RAM and a maximum
//of 512 megabytes of RAM.    
    public static void main(String[] args) {

        String url = "http://dados.gov.br/";
        CkanClient cc = new CkanClient(url);
        JdomReader jdomReader = new JdomReader();

        List<String> datasetlist = null;
        List<CkanResource> auxResources = null;
        CkanDataset dataset = null;

        try {
            datasetlist = cc.getDatasetList();
        } catch (CkanException ex) {
            System.out.println("Error: Url: " + url + " (Acesso Negado à lista de DataSets)\n\n");
        }

        if (datasetlist != null && !datasetlist.isEmpty()) {

            for (int i = 0; i < datasetlist.size(); i++) {
                try {
                    System.out.println("Index DataSet: " + i + " (" + datasetlist.get(i) + ")");
                    dataset = cc.getDataset(datasetlist.get(i));

                } catch (CkanException ex) {
                    System.out.println("Error: Url: " + url + "api/3/action/package_show?id=" + datasetlist.get(i) + " (Acesso Negado ao DataSet)\n\n");
                }

                try {
                    if (dataset != null) {
                        auxResources = dataset.getResources();
                    }
                    if (auxResources != null && !auxResources.isEmpty()) {
                        TesteJsomPrintAll.total += auxResources.size();
                        for (int j = 0; j < auxResources.size(); j++) {
                            if (auxResources.get(j).getFormat().equals("XML")
                                    && URLUtils.isValidURL(auxResources.get(j).getUrl())) {
                                jdomReader.print((auxResources.get(j).getUrl()));
                            }
                        }
                    }
                } catch (CkanException ex) {
                    System.out.println("Error: Url: " + url + "api/3/action/package_show?id=" + datasetlist.get(i) + " (Não foram encontrado Resources)\n\n");
                }
            }

            System.out.println("Funcionaram: " + funcionou);
            System.out.println("Total de Recursos: " + total);
            System.out.println("Porcentagem de acerto: " + ((funcionou * 100) / total));
        }
    }

}
