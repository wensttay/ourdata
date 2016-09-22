/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.dao.entity.KeyPlaceDaoCassandraDbImpl;
import br.ifpb.simba.ourdata.dao.entity.KeyPlaceDaoMongoDbImpl;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import br.ifpb.simba.ourdata.entity.utils.GeometryUtils;
import br.ifpb.simba.ourdata.entity.utils.PlaceUtils;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
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
    
    private void testMongoCassandra(Geometry geo) {
        KeyPlaceDaoCassandraDbImpl cassandra = new KeyPlaceDaoCassandraDbImpl();
        cassandra.getIntersectedBy(geo);
        
        KeyPlaceDaoMongoDbImpl mongo = new KeyPlaceDaoMongoDbImpl();
        mongo.getIntersected(geo);
    }
    
    public List<ResourceItemSearch> getResourceItemSearchSortedByRank(Double maxx, Double maxy, Double minx, Double miny) throws ParseException {
        
        System.out.println("Criando objeto de pesquisa...");
        start = new Date(System.currentTimeMillis());
        Geometry geometry = GeometryUtils.fromEnvelope(maxx, maxy, minx, miny);
        
        //Testing the same request on MongoDb and Cassandra
        testMongoCassandra(geometry);       
        
        System.out.println("Created Envelope : "+geometry.toString());
        Place place = new Place();
        place.setWay(geometry);
        place.setMaxX(maxx);
        place.setMinX(minx);
        place.setMaxY(maxy);
        place.setMinY(miny);
        
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
                //System.out.println("Overlap percent: "+overlapPercent+"\n");
                ResourceItemSearch itemSearch = new ResourceItemSearch(resource, rankingPercent);
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
    
    
}
