/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import eu.trentorise.opendata.traceprov.internal.org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author kieckegard
 */
public class ZIPReader
{
    public InputStream unzipUrlFile(String url) throws IOException{
        /*byte[] buffer;
        InputStream in = getInputStream(url);
        //ZipInputStream zin = new ZipInputStream(in);
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze = zin.getNextEntry();
        if(ze != null){
            buffer = IOUtils.toByteArray(zin);
        }*/
        return null;
    } 
    
    private InputStream getInputStream(String url) throws MalformedURLException, IOException{
        URL stackUrl = new URL(url);
        return stackUrl.openStream();
    }
    
   
    
}
