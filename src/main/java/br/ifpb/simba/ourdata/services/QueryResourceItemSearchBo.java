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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class QueryResourceItemSearchBo{
    private QueryPlaceBo queryPlaceBo;
    private QueryResourceBo queryResourceBo;

    public QueryResourceItemSearchBo(){
        queryPlaceBo = new QueryPlaceBo();
        queryResourceBo = new QueryResourceBo();
    }

    public List<ResourceItemSearch> getResourceItemSearchSortedByRank( String placeName, String placeType ){

        Place resultPlace = queryPlaceBo.getPlacesByName(placeName, placeType);

        List<Resource> resources = new ArrayList<>();
        List<ResourceItemSearch> itensSearch = new ArrayList<>();

        if ( resultPlace != null ){

            //Todos os recursos cuja geometria intersectou com o lugar passado por parâmetro são adicionados nessa lista.
            resources.addAll(queryResourceBo.listResourcesIntersectedBy(resultPlace));

            for ( Resource resource : resources ){
                double repeatPercent = resource.getRepeatPercent(0.2f);
                double overlapPercent = PlaceUtils.getOverlap(resource.getPlace(), resultPlace, 0.8f) * 0.8f;
                double rankingPercent = repeatPercent + overlapPercent;
//                System.out.println("Overlap percent: "+overlapPercent+"\n");

                ResourceItemSearch itemSearch = new ResourceItemSearch(resource, rankingPercent);
                itensSearch.add(itemSearch);
            }
        }

        Collections.sort(itensSearch);
        return itensSearch;
    }
}
