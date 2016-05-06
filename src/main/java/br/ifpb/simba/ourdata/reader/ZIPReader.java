/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;



/**
 *
 * @author kieckegard
 */
public class ZIPReader
{
    public InputStream unzipUrlFile(String url) throws IOException{
        InputStream in = getInputStream(url);
        try (ZipInputStream zin = new ZipInputStream(in))
        {
            ZipEntry ze;
            
            while((ze = zin.getNextEntry()) != null){
                if(!ze.isDirectory()){
                    System.out.println("Reading: "+ze.getName());
                    System.out.println("Size: "+(int)ze.getSize());
                    byte[] buffer = new byte[(int)ze.getSize()];
                    int len;
                    while((len = zin.read(buffer)) > 0){
                    }
                    zin.close();
                    ByteArrayInputStream bin = new ByteArrayInputStream(buffer);
                    return bin;
                }
            }
            zin.closeEntry();            
        }
        return null;
    } 
    
    private InputStream getInputStream(String url) throws MalformedURLException, IOException{
        URL stackurl = new URL(url);
        URLConnection conn = stackurl.openConnection();
        return conn.getInputStream();
    }
}
