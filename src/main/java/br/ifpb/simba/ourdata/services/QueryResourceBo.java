/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.dao.entity.ResourceBdDao;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import com.vividsolutions.jts.geom.Geometry;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class QueryResourceBo{
    private ResourceBdDao resourceDao;

    public QueryResourceBo(){
        resourceDao = new ResourceBdDao();
    }

    public List<Resource> listResourcesIntersectedBy( Place place ){
        
        return resourceDao.getResourcesIntersectedBy(place);
    }
}
