package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.dao.entity.PlaceBdDao;
import br.ifpb.simba.ourdata.entity.Place;

/**
 *
 * @author Pedro Arthur
 */
public class QueryPlaceBo {

    private PlaceBdDao placeDao;

    public QueryPlaceBo() {
        placeDao = new PlaceBdDao();
    }

    public Place getPlacesByName(String name, String type) {
        return getPlaceDao().burcarPorTitulos(name, type);
    }

    /**
     * @return the placeDao
     */
    public PlaceBdDao getPlaceDao() {
        return placeDao;
    }

    /**
     * @param placeDao the placeDao to set
     */
    public void setPlaceDao(PlaceBdDao placeDao) {
        this.placeDao = placeDao;
    }
    
}
