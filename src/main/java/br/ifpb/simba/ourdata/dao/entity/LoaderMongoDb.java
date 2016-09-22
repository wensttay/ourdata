/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.KeyPlace;

/**
 *
 * @author kieckegard
 */
public class LoaderMongoDb {
    
    public static void main(String[] args) {
        
        KeyPlaceDaoMongoDbImpl mongo = new KeyPlaceDaoMongoDbImpl();
        
        KeyPlaceBdDao dao = new KeyPlaceBdDao();
        
        int start = 1, quantidade = 10000;
        for(int i=0; i<2700; i++) {
            
            System.out.print("Inserindo Resource_Places de "+start+" a "+(start+quantidade)+"... ");
            for(KeyPlace kp : dao.getBetween(start, quantidade)){
                
                mongo.insert(kp);
            }
            System.out.println(" Finalizado!");
            
            start += quantidade;
        } 
        
        /*Geometry geo;
        try {
            geo = GeometryUtils.fromEnvelope(-38.52641602275395, -6.868214867838102, -38.587394522753925, -6.904987327287145);
           
            mongo.getIntersected(geo);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }*/
    }
}
