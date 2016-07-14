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
            return getArea(otherPlace);
        } else if (place.getMinX() >= otherPlace.getMinX()
                && place.getMinY() >= otherPlace.getMinY()
                && place.getMaxX() <= otherPlace.getMaxX()
                && place.getMaxY() <= otherPlace.getMaxY()) {
            return getArea(place);
        } else {
            double altura = Math.max(0, Math.min(place.getMaxY(), otherPlace.getMaxY()) - Math.max(place.getMinY(), otherPlace.getMinY()));
            double largura = Math.max(0, Math.min(place.getMaxX(), otherPlace.getMaxX()) - Math.max(place.getMinX(), otherPlace.getMinX()));
            return altura * largura;
        }
    }
    
    public static double getOverlap(Place place, Place otherPlace, float controlVariable){
        double intersc = getIntersectArea(place, otherPlace);
        return intersc / 
                (intersc + 
                    (controlVariable * (getArea(place) - getArea(otherPlace))) +
                    ((1 - controlVariable) * (getArea(otherPlace) - getArea(place)))
                );
    }
    
    public static double getOverlap2(Place place, Place otherPlace){
        double si = calculateIntersectionArea(place, otherPlace);
        double su = getArea(place) + getArea(otherPlace) - si;
        return si/su;
    }
    
    private static double calculateIntersectionArea(Place place, Place otherPlace){
        double dx = Math.min(place.getMaxX(), otherPlace.getMaxX()) - Math.min(place.getMinX(),otherPlace.getMinX());
        double dy = Math.min(place.getMaxY(), otherPlace.getMaxY()) - Math.min(place.getMinY(),otherPlace.getMinY());
        if((dx >= 0) && (dy >= 0))
                return dx*dy;
        return 0;
    }
    
    private static double getArea(Place place){
        double maxX = place.getMaxX();
        double minX = place.getMinX();
        double maxY = place.getMaxY();
        double minY = place.getMinY();
        double area = (maxX - minX) * (maxY - minY);
        return area;
    }
}
