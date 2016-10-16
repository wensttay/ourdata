/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

/**
 *
 * @author kieckegard
 */
public class TestPerformance {
    
    public static void main(String[] args) {
        
        ResourceBdDao dao = new ResourceBdDao();
        
        String wkt = "POLYGON((-73.98 5.27, -29.34 5.27, -29.34 -33.75, -73.98 -33.75, -73.98 5.27))";
        
        dao.getResourcesIntersectedBy(wkt);
    }
    
}
