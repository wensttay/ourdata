/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.geonames;

import java.util.List;
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
        //Style has 4 types: SHORT, MEDIUM, LONG e FULL
        this.style = style;
        WebService.setUserName(username);
        WebService.setDefaultStyle(style);
    }
    
    public Coordinate search(String cidade) throws Exception{
        //ToponymSearchCriteria, defines the Query U' gona make.
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        //here you can search from the exact city name
        searchCriteria.setNameEquals(cidade);
        //here you can search from the exact Country Code
        searchCriteria.setCountryCode("BR");
        searchCriteria.setMaxRows(1);
        
        /*
          This is how it works... You concatenate querys and then starts the query
          and then will return a object with all the gotten results.
        
          in the example above, the Search will get all the Brasil cities with the name
          passed from parameter. And from this result, will get only one row.
        */
        
        //WebService, Class w/ static methods like Search, Connect, setUserName, getStyle, etc.
        //ToponymSearchResult, object which contains the Query result.
        ToponymSearchResult searchResult = WebService.search(searchCriteria);
        Coordinate c;
        Toponym t;
        //Toponym is the result geographic object
        List<Toponym> toponyms = searchResult.getToponyms();
        if(!toponyms.isEmpty()){
            t = toponyms.get(0);  
            c = new Coordinate(t.getLatitude(), t.getLongitude());
            return c;
        }
        return null;
    }
    
    public String getUsername(){
        return username;
    }
    
    public Style getStyle(){
        return style;
    }
}
