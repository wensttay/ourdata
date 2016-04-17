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
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author wensttay
 */
public class TesteXMLPrint {

    public final static int NUMERO_DE_TENTATIVAS = 10;
    public static int total = 0;
    public static int funcionou = 0;

    public static void main(String[] args) {
        String urlString = "http://dados.gov.br/";
        CkanClient cc = new CkanClient(urlString);
        XMLReader jdomReader = new XMLReader();

        List<String> datasetlist = null;
        List<CkanResource> auxResources = null;
        CkanDataset dataset = null;

        try {
            datasetlist = cc.getDatasetList();
        } catch (CkanException ex) {
            System.out.println("Error: Url: " + urlString + " (Acesso Negado à lista de DataSets)\n\n");
        }

        if (datasetlist != null && !datasetlist.isEmpty()) {

            for (int i = 0; i < datasetlist.size(); i++) {
                int tentativas = 1;
                while (dataset == null && tentativas <= NUMERO_DE_TENTATIVAS) {
                    try {
                        System.out.println("Index DataSet: " + i + " (" + datasetlist.get(i) + ")");
                        dataset = cc.getDataset(datasetlist.get(i));
                    } catch (CkanException ex) {
                        System.out.println("Error: Url: " + urlString + "api/3/action/package_show?id=" + datasetlist.get(i) + " (Acesso Negado ao DataSet)\n\n");
                        ++tentativas;
                    }
                }
                try {
                    if (dataset != null) {
                        auxResources = dataset.getResources();
                    }
                    if (auxResources != null && !auxResources.isEmpty()) {
                        for (int j = 0; j < auxResources.size(); j++) {
                            if (auxResources.get(j).getFormat().equals("XML")) {
                                total++;
                                jdomReader.print((auxResources.get(j).getUrl()));
                            }
                        }
                    }
                } catch (CkanException ex) {
                    System.out.println("Error: Url: " + urlString + "api/3/action/package_show?id=" + datasetlist.get(i) + " (Não foram encontrado Resources)\n\n");
                }

                auxResources = null;
                dataset = null;
            }
            NumberFormat formatter = new DecimalFormat("#0.00");
            float percentSucess = (((float) funcionou * 100) / (float) total);
            
            String resut =  "\n Total de Recursos (XML) Funcionando: " + funcionou +
                    "\n Total de Recursos (XML): " + total +
                    "\n Porcentagem de Acerto Funcional: " + formatter.format(percentSucess) + " %\n";
            saveLog(resut);
        }
    }

    public static void saveLog(String text) {
        Logger logger = Logger.getLogger("XML-PrintLog");
        FileHandler fh;
        try {
            // This block configure the logger with handler and formatter  
            fh = new FileHandler(System.getProperty("user.dir") + "\\src\\main\\resources\\log\\LogPrintXML.log"); 
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages  
            logger.info(text);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
