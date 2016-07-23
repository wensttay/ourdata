/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.dao.entity.PlaceBdDao;
import br.ifpb.simba.ourdata.entity.Place;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class QueryPlaceBo{
    private PlaceBdDao placeDao;

    public QueryPlaceBo(){
        placeDao = new PlaceBdDao();
    }

    public Place getPlacesByName( String name, String type ){
        return placeDao.burcarPorTitulos(name, type);
    }
}
