/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.XMLReader;
import eu.trentorise.opendata.jackan.CkanClient;
import eu.trentorise.opendata.jackan.exceptions.CkanException;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class TesteXMLPrint {
    
    public final static int NUMERO_DE_TENTATIVAS = 10;
    public static int total = 0;
    public static int funcionou = 0;
    
    
    public static void main(String[] args) {
        String url = "http://dados.gov.br/";
        CkanClient cc = new CkanClient(url);
        XMLReader jdomReader = new XMLReader();

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
                int tentativas = 1;
                while (dataset == null && tentativas <= NUMERO_DE_TENTATIVAS) {
                    try {
                        System.out.println("Index DataSet: " + i + " (" + datasetlist.get(i) + ")");
                        dataset = cc.getDataset(datasetlist.get(i));
                    } catch (CkanException ex) {
                        System.out.println("Error: Url: " + url + "api/3/action/package_show?id=" + datasetlist.get(i) + " (Acesso Negado ao DataSet)\n\n");
                        ++tentativas;
                    }
                }
                try {
                    if (dataset != null) {
                        auxResources = dataset.getResources();
                    }
                    if (auxResources != null && !auxResources.isEmpty()) {
                        total += auxResources.size();
                        for (int j = 0; j < auxResources.size(); j++) {
                            if (auxResources.get(j).getFormat().equals("XML")) {
                                jdomReader.print((auxResources.get(j).getUrl()));
                            }
                        }
                    }
                } catch (CkanException ex) {
                    System.out.println("Error: Url: " + url + "api/3/action/package_show?id=" + datasetlist.get(i) + " (Não foram encontrado Resources)\n\n");
                }
                
                auxResources = null;
                dataset = null;
            }

            System.out.println("Funcionaram: " + funcionou);
            System.out.println("Total de Recursos: " + total);
            System.out.println("Porcentagem de acerto: " + ((funcionou * 100) / total));
        }
    }
}
