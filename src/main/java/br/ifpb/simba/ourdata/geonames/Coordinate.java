/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.geonames;

/**
 *
 * @author kieckegard
 */
public class Coordinate
{
    public double latitude;
    public double longitude;
    
    public Coordinate(){
        
    }
    
    public Coordinate(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
    
    @Override
    public String toString(){
        return "| Latitude: "+latitude+" | Longitude: "+longitude;
    }
}
