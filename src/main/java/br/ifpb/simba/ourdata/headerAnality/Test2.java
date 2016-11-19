/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Pedro Arthur
 */
public class Test2 {
    
    private static boolean isDoubleOrFloat(String cell) {
        
        try {
            
            //point 
            Double d = Double.parseDouble(cell);
            return true;
            
        } catch(NumberFormatException ex) {
            
            //comma
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            try {
                
                Number number = format.parse(cell);
                return true;
                
            } catch (java.text.ParseException ex1) {
                return false;
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println(isDoubleOrFloat("50075"));
    }
}
