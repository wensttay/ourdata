/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.geo.PlaceBdDao;
import eu.trentorise.opendata.jackan.model.CkanResource;

/**
 *
 * @author kieckegard
 */
public class testRank
{
    static final String PROPERTIES_PATH_GAZETTEER = "/banco/banco.gazetteer.properties";
    static final String PROPERTIES_PATH_OURDATA = "/banco/banco.properties";
   
    final static PlaceBdDao place2 = new PlaceBdDao(PROPERTIES_PATH_OURDATA);
    /**
     * Arapiraca
     * Alagoinhas
     */
    public static void main(String[] args){
        
        String valor = "Paraíba";
        print(valor);
       
    }
    
    public static void print(String valor){
        for(CkanResource r : place2.getResourcesByPlace(valor)){
            //System.out.println("descricao = "+r.getDescription()+" | url = "+r.getUrl());
        }
    }
}

