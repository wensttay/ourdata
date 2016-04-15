/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.reader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author wensttay
 */
public class URLUtils {
    
    public static final int ONE_MINUTE = 60000;
    public static final int DEFAULT_TIMEOUT = 120000;
    
    public static boolean isValidURL(String url, int timeOut) {
        try {
            URL auxURL = new URL(url);

            if (auxURL.getHost().length() == 0 || auxURL.getHost() == null) {
                System.out.println("Site is down");
                return false;
            }

            HttpURLConnection conn = (HttpURLConnection) auxURL.openConnection();

            if (conn != null) {
                conn.setReadTimeout(timeOut);
                conn.setRequestMethod("GET");
                System.out.println(String.format("Testando URL: %s ...", url));
                conn.getResponseCode();
                conn.disconnect();
                conn = null;
                return true;
            } else {
                return false;
            }

        } catch (RuntimeException | IOException ex) {
            System.out.println("Site is down (" + ex.getMessage() + ")");
            return false;
        }
    }
    
    public static boolean isValidURL(String url) {
        return URLUtils.isValidURL(url, URLUtils.DEFAULT_TIMEOUT);
    }
   
}
