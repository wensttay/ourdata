/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.osm;

import org.postgis.Geometry;


/**
 *
 * @author kieckegard
 */
public class Place
{
    private int id;
    private String name;
    private String amenity;
    private Geometry geo;
    
    
    public Place(String name, String amenity, Geometry geo){
        this.name=name;
        this.amenity=amenity;
        this.geo=geo;
    }
    
    public Place(int id, String name, String amenity, Geometry geo){
        this.id=id;
        this.name=name;
        this.amenity=amenity;
        this.geo=geo;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getAmenity()
    {
        return amenity;
    }
    
    public Geometry getGeometry(){
        return geo;
    }
    
    public void setId(int id){
        this.id=id;
    }
    
    
}
