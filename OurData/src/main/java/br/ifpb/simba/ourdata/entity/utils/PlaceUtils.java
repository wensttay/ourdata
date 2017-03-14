package br.ifpb.simba.ourdata.entity.utils;

import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class PlaceUtils {

    private double controlVariable = 0.5;

    /**
     * Method to get the area of intesect two places
     *
     * @param place the first place
     * @param otherPlace the secound place
     *
     * @return Area of intesect two places pass on params
     */
    public static double getIntersectArea(Place place, Place otherPlace) {
        double altura = Math.max(0, Math.min(place.getMaxY(), otherPlace.getMaxY()) - Math.max(place.getMinY(), otherPlace.getMinY()));
        double largura = Math.max(0, Math.min(place.getMaxX(), otherPlace.getMaxX()) - Math.max(place.getMinX(), otherPlace.getMinX()));
        return altura * largura;
    }

    public static float getRepeatPercent(Resource resource, float constante) {
        int sum_repeat = 0;
        int rows = 0;
        if (!resource.getKeyplaces().isEmpty()) {
            rows = resource.getKeyplaces().get(0).getRowsNumber();
        } else {
            return 0;
        }
        for (KeyPlace kp : resource.getKeyplaces()) {
            sum_repeat += kp.getRepeatNumber();
        }
        return (sum_repeat / rows) * constante;
    }

    public static double getOverlap(Place place, Place otherPlace, float controlVariable) {
        double intersectArea = getIntersectArea(place, otherPlace);
        double placeArea = getArea(place);
        double otherPlaceArea = getArea(otherPlace);

        double divisor1 = intersectArea;
        double divisor2 = controlVariable * (placeArea - intersectArea);
        double divisor3 = (1f - controlVariable) * (otherPlaceArea - intersectArea);
        double result = intersectArea / (divisor1 + divisor2 + divisor3);
        return result;
    }

    private static double getArea(Place place) {
        return (place.getMaxX() - place.getMinX()) * (place.getMaxY() - place.getMinY());
    }
}
