/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.thread;

import br.ifpb.simba.ourdata.dao.ckan.CkanDataSetBdDao;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.exceptions.CkanException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import java.util.List;

/**
 *
 * @author Wensttay
 */
public class AtualizadorDeBanco extends Thread {

    String url;
    CkanClient cc;
    CkanDataSetBdDao cdsbd;
    List<String> datasetlist;
    long intervalTime = 5000;

    public AtualizadorDeBanco(String url) {
        this.url = url;
        cc = new CkanClient(this.url);
        cdsbd = new CkanDataSetBdDao();
        datasetlist = cc.getDatasetList();
    }
    
    public AtualizadorDeBanco(String url, Long intervalTime) {
        this.url = url;
        this.intervalTime = intervalTime;
        cc = new CkanClient(this.url);
        cdsbd = new CkanDataSetBdDao();
        datasetlist = cc.getDatasetList();
    }

    public void run() {
        while (true) {
            for (int i = 0; i < datasetlist.size(); i++) {
                try {
                    System.out.println("Index DataSet: " + i);
                    CkanDataset dataset = cc.getDataset(datasetlist.get(i));
                    cdsbd.insertOrUpdate(dataset);

                } catch (CkanException ex) {
                    System.out.println("Acesso negado.");
                }
            }

            try {
                Thread.sleep(intervalTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
