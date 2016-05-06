/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.reader.CSVReader;
import br.ifpb.simba.ourdata.reader.ZIPReader;
import eu.trentorise.opendata.commons.internal.org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        String url = "http://repositorio.dados.gov.br/transportes-transito/transito/brbrasil_1_semestre_2011.zip";
        CSVReader csvReader = new CSVReader();
        int i;
        try
        {
            /*List<Arquivo> filesStream = reader.unzipUrlFile(url);
            for(Arquivo a : filesStream){
                byte[] buffer = new byte[a.getInputStream().available()];
                a.getInputStream().read(buffer);
                File f = new File("C:\\"+a.getName());
                if(!f.exists())
                    f.createNewFile();
                OutputStream os = new FileOutputStream(f);
                os.write(buffer);
                System.out.println("saved!");
                System.out.println("-----------Reading "+a.getName()+" --------------");*/
                i=0;
                List<String[]> rows = csvReader.build(reader.unzipUrlFile(url));
                for(String[] row : rows){
                    i++;
                    for(String cell : row){
                        System.out.print(cell+" | ");
                    }
                    System.out.println();
                }
        }
        catch (IOException ex)
        {
            Logger.getLogger(TestZIP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
