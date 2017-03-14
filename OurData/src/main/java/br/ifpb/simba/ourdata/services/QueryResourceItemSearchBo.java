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
 * @author Pedro Arthur
 */
public class QueryResourceItemSearchBo {

    private QueryPlaceBo queryPlaceBo;
    private QueryResourceBo queryResourceBo;
    private Date start;

    public QueryResourceItemSearchBo() {
        queryPlaceBo = new QueryPlaceBo();
        queryResourceBo = new QueryResourceBo();
    }

    public List<ResourceItemSearch> getResourceItemSearchSortedByRank(String placeName, String placeType) {

        Place resultPlace = getQueryPlaceBo().getPlacesByName(placeName, placeType);
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
        List<Resource> resourcesEvaluation = new ArrayList<>();
        List<ResourceItemSearch> itensSearch = new ArrayList<>();

        if (place != null) {
            
            //Todos os recursos cuja geometria intersectou com o lugar passado por parâmetro são adicionados nessa lista.
            resources.addAll(getQueryResourceBo().listResourcesIntersectedBy(place));
            resourcesEvaluation.addAll(getQueryResourceBo().listResourcesIntersectedByEvaluation(place));
            
            int match = 0;
            int noMatch = 0;
            for (Resource rA : resourcesEvaluation) {
                boolean encontrou = false;
                for (Resource r : resources) {
                    if (r.getId().equals(rA.getId())) {
                        match++;
                        encontrou = true;
                        break;
                    }
                }
            
                if(!encontrou){
                    System.out.println( ++noMatch + " - ID: " + rA.getId());
                }
            }
            
            System.out.println("MATCHs: " + match);
            System.out.println("ENCONTRADOS NO RESOURCE_PLACE: " + resources.size());
            System.out.println("ENCONTRADOS NO RESOURCE_PLACE_EVALUATION: " + resourcesEvaluation.size());
            float recall = ((float) match * 100) / (float) resourcesEvaluation.size();
            System.out.println("REACALL: " + recall);
            float precision = ((float) match * 100) / (float) resources.size();
            System.out.println("PRECISION: " + precision);
            int allRight = (resourcesEvaluation.size() - match) - (resources.size() - match); 
            System.out.println("ACCURACY: " + ((float) ((match + allRight) * 100) / (float) resourcesEvaluation.size()));
            System.out.println("MÉDIA HARMÔNICA: " + 2 * ((float) (precision * recall) / (float) (precision + recall)));
            
            for (Resource resource : resources) {
                double repeatPercent = resource.getRepeatPercent(0.2f);
                double overlapPercent = PlaceUtils.getOverlap(resource.getPlace(), place, 0.8f) * 0.8f;
                double rankingPercent = repeatPercent + overlapPercent;
                ResourceItemSearch itemSearch = new ResourceItemSearch(resource, rankingPercent * 100);
                itensSearch.add(itemSearch);
            }
        }
        Collections.sort(itensSearch);
        
        return itensSearch;
    }

    public List<ResourceTimeSearch> getResourceItemSearchByTime(Date start, Date end) {

        PeriodTime periodStart = new PeriodTime(start);
        PeriodTime periodEnd = new PeriodTime(end);
        Period period = new Period(periodStart, periodEnd);
        List<ResourceTimeSearch> rtses = getQueryResourceBo().listResourcesIntersectedBy(period);

        return rtses;
    }

    /**
     * @return the queryPlaceBo
     */
    public QueryPlaceBo getQueryPlaceBo() {
        return queryPlaceBo;
    }

    /**
     * @param queryPlaceBo the queryPlaceBo to set
     */
    public void setQueryPlaceBo(QueryPlaceBo queryPlaceBo) {
        this.queryPlaceBo = queryPlaceBo;
    }

    /**
     * @return the queryResourceBo
     */
    public QueryResourceBo getQueryResourceBo() {
        return queryResourceBo;
    }

    /**
     * @param queryResourceBo the queryResourceBo to set
     */
    public void setQueryResourceBo(QueryResourceBo queryResourceBo) {
        this.queryResourceBo = queryResourceBo;
    }

}
