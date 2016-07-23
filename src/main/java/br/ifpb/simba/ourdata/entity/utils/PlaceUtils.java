/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;

/**
 *
 * @author Wensttay
 */
public class PlaceUtils{

    private double controlVariable = 0.5;

    /**
     * Method to get the area of intesect two places
     *
     * @param place the first place
     * @param otherPlace the secound place
     *
     * @return Area of intesect two places pass on params
     */
    public static double getIntersectArea( Place place, Place otherPlace ){
        double altura = Math.max(0, Math.min(place.getMaxY(), otherPlace.getMaxY()) - Math.max(place.getMinY(), otherPlace.getMinY()));
        double largura = Math.max(0, Math.min(place.getMaxX(), otherPlace.getMaxX()) - Math.max(place.getMinX(), otherPlace.getMinX()));
        return altura * largura;
    }

    public static float getRepeatPercent( Resource resource, float constante ){
        int sum_repeat = 0;
        int rows = 0;
        if ( !resource.getKeyplaces().isEmpty() ){
            rows = resource.getKeyplaces().get(0).getRowsNumber();
        } else{
            return 0;
        }
        for ( KeyPlace kp : resource.getKeyplaces() ){
            sum_repeat += kp.getRepeatNumber();
        }
        return (sum_repeat / rows) * constante;
    }

    public static double getOverlap( Place place, Place otherPlace, float controlVariable ){
        double intersectArea = getIntersectArea(place, otherPlace);
        double placeArea = getArea(place);
        double otherPlaceArea = getArea(otherPlace);

        double divisor1 = intersectArea;
        double divisor2 = controlVariable * (placeArea - intersectArea + 0.01);
        double divisor3 = (1f - controlVariable) * (otherPlaceArea - intersectArea + 0.01);
        double result = intersectArea / (divisor1 + divisor2 + divisor3);

//        if (result == 1f)
//        {
//            System.out.println("Area(A^B): " + intersectArea);
//            System.out.println("Area(A - B): " + (placeArea - otherPlaceArea));
//            System.out.println("const * Area(A - B): " + (controlVariable * (placeArea - otherPlaceArea)));
//            System.out.println("Area(B - A): " + (otherPlaceArea - placeArea));
//            System.out.println("(1 - const) * Area(A - B): " + ((1 - controlVariable) * (placeArea - otherPlaceArea)));
//
//            System.out.println("Place: " + place.getNome() + "(" + place.getSigla() + ")");
//            System.out.println("Place: " + otherPlace.getNome() + "(" + otherPlace.getSigla() + ")");
//            System.out.println("divisor 1: " + divisor1);
//            System.out.println("divisor 2: " + divisor2);
//            System.out.println("divisor 3: " + divisor3);
//        }
        return result;
    }

    private static double getArea( Place place ){
        return (place.getMaxX() - place.getMinX()) * (place.getMaxY() - place.getMinY());
    }
}
