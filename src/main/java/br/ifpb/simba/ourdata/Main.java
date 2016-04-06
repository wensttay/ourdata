/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata;

import br.ifpb.simba.ourdata.dao.ckan.CkanDataSetBdDao;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.exceptions.CkanException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kieckegard
 */
public class Main extends Thread {

    public static int datasetTag = 0;
    String url = "http://dados.gov.br/";
    CkanClient cc;
    CkanDataSetBdDao cdsbd;
    List<String> datasetlist;
    
    public void run() {
        while (true) {
            cc = new CkanClient(url);
            cdsbd = new CkanDataSetBdDao();
            datasetlist = cc.getDatasetList();
            
            for (int i = 0; i < datasetlist.size(); i++) {
                try {
                    System.out.println("Index DataSet: " + i);
                    CkanDataset dataset = cc.getDataset(datasetlist.get(i));
                    cdsbd.insertOrUpdate(dataset);
                } catch (CkanException ex) {
                    System.out.println("Acesso negado.");
                }
            }
            
            System.out.println("DATASET TAG |O| : " + datasetTag);
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
