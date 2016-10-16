/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.KeyPlace;

/**
 * Simple class to migrate postgres data to cassandra database.
 * @author kieckegard
 */
public class LoaderCassandra {
    
    public static void main(String... args) {
        
        KeyPlaceBdDao dao = new KeyPlaceBdDao();
        KeyPlaceDaoCassandraDbImpl cassandra = new KeyPlaceDaoCassandraDbImpl();
        
        int start = 1, quantidade = 1000;
        for(int i=0; i<27000; i++) {
            
            System.out.print("Inserindo Resource_Places de "+start+" à "+(start+quantidade)+"... ");
            for(KeyPlace kp : dao.getBetween(start, quantidade)) {
                
                cassandra.insert(kp);
            }
            System.out.println(" Finalizado!");
            
            start += quantidade;
        }         
    }
}
