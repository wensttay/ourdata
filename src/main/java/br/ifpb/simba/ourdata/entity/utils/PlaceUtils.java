/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.Place;

/**
 *
 * @author Wensttay
 */
public class PlaceUtils {

    private double controlVariable = 0.5;
    
    
    /**
     * Method to get the area of intesect two places
     *
     * @param place the first place
     * @param otherPlace the secound place
     * @return Area of intesect two places pass on params
     */
    public static double getIntersectArea(Place place, Place otherPlace) {
        if (place.getMinX() <= otherPlace.getMinX()
                && place.getMinY() <= otherPlace.getMinY()
                && place.getMaxX() >= otherPlace.getMaxX()
                && place.getMaxY() >= otherPlace.getMaxY()) {

            return otherPlace.getWay().getArea();
        } else if (place.getMinX() >= otherPlace.getMinX()
                && place.getMinY() >= otherPlace.getMinY()
                && place.getMaxX() <= otherPlace.getMaxX()
                && place.getMaxY() <= otherPlace.getMaxY()) {
            return place.getWay().getArea();

        } else {
            double altura = Math.max(0, Math.min(place.getMaxY(), otherPlace.getMaxY()) - Math.max(place.getMinY(), otherPlace.getMinY()));
            double largura = Math.max(0, Math.min(place.getMaxX(), otherPlace.getMaxX()) - Math.max(place.getMinX(), otherPlace.getMinX()));
            return altura * largura;
        }
    }
    
    public double getOverlap(Place place, Place otherPlace){
        double intersc = getIntersectArea(place, otherPlace);
        return intersc / 
                (intersc + 
                    (controlVariable * (place.getWay().getArea() - otherPlace.getWay().getArea())) +
                    ((1 - controlVariable) * (otherPlace.getWay().getArea() - place.getWay().getArea()))
                );
    }
}
