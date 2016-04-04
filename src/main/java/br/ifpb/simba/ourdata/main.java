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

/**
 *
 * @author kieckegard
 */
public class main {

    public static void main(String[] args) {
        String url = "http://dados.gov.br/";
        CkanClient cc = new CkanClient(url);
        CkanDataSetBdDao cdsbd = new CkanDataSetBdDao();
        List<String> datasetlist = cc.getDatasetList();

        for (int i = 0; i < datasetlist.size(); i++) {
            try {
                System.out.println("Index DataSet: " + i);
                CkanDataset dataset = cc.getDataset(datasetlist.get(i));
                cdsbd.insert(dataset);
            } catch (CkanException ex) {
                System.out.println("Acesso negado.");
            }
        }

    }
}
