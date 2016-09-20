/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

import br.ifpb.simba.ourdata.dao.entity.KeyPlaceBdDao;
import com.github.filosganga.geogson.gson.GeometryAdapterFactory;
import com.github.filosganga.geogson.jts.JtsAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 *
 * @author kieckegard
 */
public class Loader {
    
    public static void main(String[] args) {
        KeyPlaceBdDao dao = new KeyPlaceBdDao();
        KeyPlaceDaoMongoDbImpl mongo = new KeyPlaceDaoMongoDbImpl();
        
        /*Integer i = new Integer(0);
        for(KeyPlace kp : dao.getAll()) {
            i++;
            System.out.println("Inserindo Resource_place ["+i+"]...");
            mongo.insert(kp);
        }*/
        
        /*int start = 12001, quantidade = 10000;
        for(int i=0; i<2700; i++) {
            
            System.out.print("Inserindo Resource_Places de "+start+" a "+(start+quantidade)+"... ");
            for(KeyPlace kp : dao.getBetween(start, quantidade)){
                
                mongo.insert(kp);
            }
            System.out.println(" Finalizado!");
            
            start += quantidade;
        } */
        
        Envelope envelope = new Envelope(-6.868214867838102, -38.52641602275395, -6.904987327287145, -38.587394522753925);
        
        GeometryFactory fac = new GeometryFactory();
        Geometry geometry = fac.toGeometry(envelope);   
        
        //System.out.println(mongo.getIntersected(geometry).size());
        
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new GeometryAdapterFactory())
                .registerTypeAdapterFactory(new JtsAdapterFactory())
                .create();
        
        String geometryJson = gson.toJson(geometry, Geometry.class);
        
        System.out.println(geometryJson);
        
        for(KeyPlace kp : mongo.getIntersected(geometry)) {
            System.out.println(kp);
        }
        

        
        
        
        String a = "Bounding Box North-east corner: -6.868981799999999, -38.526072699999986 South-west corner: -6.9057542, -38.58705120000002";
    }
}
