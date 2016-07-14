/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.services;


import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import br.ifpb.simba.ourdata.entity.ResourceItemSearch;
import br.ifpb.simba.ourdata.entity.utils.PlaceUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class QueryResourceItemSearchBo
{
    private QueryPlaceBo queryPlaceBo;
    private QueryResourceBo queryResourceBo;
    
    public QueryResourceItemSearchBo(){
        queryPlaceBo = new QueryPlaceBo();
        queryResourceBo = new QueryResourceBo();
    }
    
    public List<ResourceItemSearch> getResourceItemSearchSortedByRank(String placeName, String placeType){
        
        List<Place> resultPlaces = queryPlaceBo.getPlacesByName(placeName);
        List<Resource> resources = new ArrayList<>();
        List<ResourceItemSearch> itensSearch = new ArrayList<>();
        
        if(!resultPlaces.isEmpty()){
            Place place = resultPlaces.get(0);
            
            //Todos os recursos cuja geometria intersectou com o lugar passado por parâmetro são adicionados nessa lista.
            resources.addAll(queryResourceBo.listResourcesIntersectedBy(place));
            
            for(Resource r : resources){
                System.out.println(r.getPlace().getMaxX()+" | "+r.getPlace().getMaxY());
            }
            
            for(Resource resource : resources){
                float repeatPercent = resource.getRepeatPercent(0.2f);
                double overlapPercent;
                double rankingPercent;
                
                overlapPercent = PlaceUtils.getOverlap2(place, resource.getPlace());
                
                overlapPercent*=0.8f;
                
                rankingPercent = repeatPercent + overlapPercent;
                
                if(rankingPercent > 1) rankingPercent = 1;
                
                ResourceItemSearch itemSearch = new ResourceItemSearch(resource,rankingPercent);
                
                itensSearch.add(itemSearch);
            }
        }
        Collections.sort(itensSearch);
        return itensSearch;
    }
}
