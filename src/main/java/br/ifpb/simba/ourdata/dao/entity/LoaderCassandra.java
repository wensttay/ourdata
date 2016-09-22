/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.dao.entity.KeyPlaceBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.utils.GeometryUtils;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kieckegard
 */
public class LoaderCassandra {
    
    public static void main(String... args) {
        
        KeyPlaceBdDao dao = new KeyPlaceBdDao();
        KeyPlaceDaoCassandraDbImpl cassandra = new KeyPlaceDaoCassandraDbImpl();
        
        /*for(KeyPlace kp : dao.getBetween(1, 2)) {
            cassandra.insert(kp);
        }*/
        
        int start = 1, quantidade = 10000;
        for(int i=0; i<2700; i++) {
            
            System.out.print("Inserindo Resource_Places de "+start+" a "+(start+quantidade)+"... ");
            for(KeyPlace kp : dao.getBetween(start, quantidade)){
                
                cassandra.insert(kp);
            }
            System.out.println(" Finalizado!");
            
            start += quantidade;
        } 
       
        
        /*Geometry geo;
        try {
            geo = GeometryUtils.fromEnvelope(-38.52641602275395, -6.868214867838102, -38.587394522753925, -6.904987327287145);
            
            int count = 0;
            for(KeyPlace kp : cassandra.getIntersectedBy(geo)) {
                count++;
            }
            System.out.println("KeyPlaces found: "+count+"!");
        }
        catch (ParseException ex) {
            Logger.getLogger(LoaderCassandra.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
    }
}
