/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.entity.Period;
import br.ifpb.simba.ourdata.entity.PeriodTime;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import br.ifpb.simba.ourdata.entity.ResourceTimeSearch;
import br.ifpb.simba.ourdata.entity.utils.PlaceUtils;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class QueryResourceItemSearchBo {
    
    private final QueryPlaceBo queryPlaceBo;
    private final QueryResourceBo queryResourceBo;
    private Date start;
    
    public QueryResourceItemSearchBo() {
        queryPlaceBo = new QueryPlaceBo();
        queryResourceBo = new QueryResourceBo();
    }

    public List<ResourceItemSearch> getResourceItemSearchSortedByRank( String placeName, String placeType ){

        Place resultPlace = queryPlaceBo.getPlacesByName(placeName, placeType);
        return getResourceItemSearchSortedByRank(resultPlace);  
    }
    
    public List<ResourceItemSearch> getResourceItemSearchSortedByRank(Envelope envelope) {
        
        System.out.println("Criando objeto de pesquisa...");
        start = new Date(System.currentTimeMillis());
        GeometryFactory fac = new GeometryFactory();
        Geometry geometry = fac.toGeometry(envelope);
        Place place = new Place();
        place.setWay(geometry);
        place.setMaxX(envelope.getMaxX());
        place.setMinX(envelope.getMinX());
        place.setMaxY(envelope.getMaxY());
        place.setMinY(envelope.getMinY());
        
        System.out.println("Duração em ms: " + (System.currentTimeMillis() - start.getTime()));
        return getResourceItemSearchSortedByRank(place);    
    }
    
    private List<ResourceItemSearch> getResourceItemSearchSortedByRank(Place place) {
        
        List<Resource> resources = new ArrayList<>();
        List<ResourceItemSearch> itensSearch = new ArrayList<>();

        if ( place != null ) {
            
            start = new Date(System.currentTimeMillis());
//            System.out.println("Obtendo todos os resources que intersectão com o lugar passado por parâmetro...");
            //Todos os recursos cuja geometria intersectou com o lugar passado por parâmetro são adicionados nessa lista.
            resources.addAll(queryResourceBo.listResourcesIntersectedBy(place));
//            System.out.println("Duração em ms: " + (System.currentTimeMillis() - start.getTime()));
            
            start = new Date(System.currentTimeMillis());
            System.out.println("Obtendo calculo do RankingPercent (repeatPercent + overlapPercent)...");
            for ( Resource resource : resources ) {
                double repeatPercent = resource.getRepeatPercent(0.2f);
                double overlapPercent = PlaceUtils.getOverlap(resource.getPlace(), place, 0.8f) * 0.8f;
                double rankingPercent = repeatPercent + overlapPercent;
                ResourceItemSearch itemSearch = new ResourceItemSearch(resource, rankingPercent * 100);
                itensSearch.add(itemSearch);
            }
            System.out.println("Duração em ms: " + (System.currentTimeMillis() - start.getTime()));
        }
        
        start = new Date(System.currentTimeMillis());
        System.out.println("Ordenando SearchResources encontrados...");
        Collections.sort(itensSearch);
        System.out.println("Duração em ms: " + (System.currentTimeMillis() - start.getTime()));
        
        return itensSearch;
    }
    
    public List<ResourceTimeSearch> getResourceItemSearchByTime(Date start, Date end) {
        
        PeriodTime periodStart = new PeriodTime(start);
        PeriodTime periodEnd = new PeriodTime(end);
        Period period = new Period(periodStart, periodEnd);
        List<ResourceTimeSearch> rtses = queryResourceBo.listResourcesIntersectedBy(period);
        
        return rtses;
    }
    
    
}
