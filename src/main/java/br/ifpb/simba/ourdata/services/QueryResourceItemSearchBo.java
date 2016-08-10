/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import br.ifpb.simba.ourdata.entity.utils.PlaceUtils;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class QueryResourceItemSearchBo {
    
    private final QueryPlaceBo queryPlaceBo;
    private final QueryResourceBo queryResourceBo;

    public QueryResourceItemSearchBo() {
        queryPlaceBo = new QueryPlaceBo();
        queryResourceBo = new QueryResourceBo();
    }

    public List<ResourceItemSearch> getResourceItemSearchSortedByRank( String placeName, String placeType ){

        Place resultPlace = queryPlaceBo.getPlacesByName(placeName, placeType);
        return getResourceItemSearchSortedByRank(resultPlace);  
    }
    
    public List<ResourceItemSearch> getResourceItemSearchSortedByRank(Envelope envelope) {
        
        GeometryFactory fac = new GeometryFactory();
        Geometry geometry = fac.toGeometry(envelope);
        Place place = new Place();
        place.setWay(geometry);
        place.setMaxX(envelope.getMaxX());
        place.setMinX(envelope.getMinX());
        place.setMaxY(envelope.getMaxY());
        place.setMinY(envelope.getMinY());
        
        
        return getResourceItemSearchSortedByRank(place);    
    }
    
    private List<ResourceItemSearch> getResourceItemSearchSortedByRank(Place place) {
        
        List<Resource> resources = new ArrayList<>();
        List<ResourceItemSearch> itensSearch = new ArrayList<>();

        if ( place != null ) {

            //Todos os recursos cuja geometria intersectou com o lugar passado por parâmetro são adicionados nessa lista.
            resources.addAll(queryResourceBo.listResourcesIntersectedBy(place));

            for ( Resource resource : resources ) {
                double repeatPercent = resource.getRepeatPercent(0.2f);
                double overlapPercent = PlaceUtils.getOverlap(resource.getPlace(), place, 0.8f) * 0.8f;
                double rankingPercent = repeatPercent + overlapPercent;
                //System.out.println("Overlap percent: "+overlapPercent+"\n");

                ResourceItemSearch itemSearch = new ResourceItemSearch(resource, rankingPercent);
                itensSearch.add(itemSearch);
            }
        }

        Collections.sort(itensSearch);
        return itensSearch;
    }
    
    
}
