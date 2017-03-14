/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.thematic.indexer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Pedro Arthur
 */
public class Log {

    public static void log(String fullFileName, String text) {
        try {
            
            File file = new File(fullFileName);
            if (!file.exists()) 
                file.createNewFile();
            
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes());
            
            fos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
