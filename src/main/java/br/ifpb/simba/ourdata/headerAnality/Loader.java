/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 *
 * @author Pedro Arthur
 */
public class Loader {
    
    public static void main(String[] args) {
        
        String cell = "1";
        
        try {
            
            Integer i = Integer.parseInt(cell);    
            //deu certo
            
        } catch(NumberFormatException ex) {
            
            try {
                
                WKTReader reader = new WKTReader();
                reader.read(cell);
                
                break;
                
            } catch(ParseException pe) {
                
                try {
                    
                } catch(DataException) {
                    //considera.
                }
            }
        }
        
        
    }
}
