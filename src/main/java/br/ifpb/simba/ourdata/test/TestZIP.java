/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.CSVReader;
import br.ifpb.simba.ourdata.reader.ZIPReader;
import eu.trentorise.opendata.commons.internal.org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kieckegard
 */
public class TestZIP
{
    public static void main(String[] args){
        ZIPReader reader = new ZIPReader();
        String url = "http://download.inep.gov.br/informacoes_estatisticas/2011/indicadores_educacionais/taxa_distorcao_idade_serie/2009/dados_tdi_escolas_2009.zip";
        CSVReader csvReader = new CSVReader();
        try
        {
            List<String[]> rows = csvReader.build(reader.unzipUrlFile(url));
            for(String[] row : rows){
                for(String cell : row){
                    System.out.println(cell+" | ");
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(TestZIP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
