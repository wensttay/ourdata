/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wensttay
 */
public class KeyPlaceUtils {

    /**
     * Return a list with a 'lite Version' of result seach KeyWords's resource.
     * This list has a same KeyWords of result search, but KeyWords with same
     * place are joined in the same KeyPlace
     *
     * @param keyWords List with repeat KeyPlaces
     *
     * @return Filered list, without repeat KeyPlaces
     */
    public static List<KeyPlace> getLiteVersion(List<KeyPlace> keyWords) {
        List<KeyPlace> liteVersion = new ArrayList<>();

        if (keyWords != null && !keyWords.isEmpty()) {
            for (KeyPlace keyWord : keyWords) {
                boolean exist = false;
                for (KeyPlace liteVersionAux : liteVersion) {
                    if (liteVersionAux.equals(keyWord)) {
                        liteVersionAux.setRepeatNumber(keyWord.getRepeatNumber() + liteVersionAux.getRepeatNumber());
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    liteVersion.add(keyWord);
                }
            }
        }
        return liteVersion;
    }
    
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

}
