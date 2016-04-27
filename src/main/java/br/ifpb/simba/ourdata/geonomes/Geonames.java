/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.geonomes;

import org.geonames.Style;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

/**
 *
 * @author kieckegard
 */
public class Geonames
{   
    private String username;
    private Style style;
    
    public Geonames(String username, Style style){
        this.username = username;
        this.style = style;
        WebService.setUserName(username);
        WebService.setDefaultStyle(style);
    }
    
    public Coordinate search(String cidade) throws Exception{
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        searchCriteria.setNameEquals(cidade);
        searchCriteria.setCountryCode("BR");
        searchCriteria.setMaxRows(1);
        ToponymSearchResult searchResult = WebService.search(searchCriteria);
        Coordinate c;
        Toponym toponym = null;
        for(Toponym t : searchResult.getToponyms()){
            toponym = t;
        }
        if(toponym != null){
            c = new Coordinate(toponym.getLatitude(), toponym.getLongitude());
            System.out.println(c);
            return c;
        }
        System.out.println("Toponym not found.\n");
        return null;
    }
    
    public String getUsername(){
        return username;
    }
    
    public Style getStyle(){
        return style;
    }
}
