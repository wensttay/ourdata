package br.ifpb.simba.ourdata.services;

import br.ifpb.simba.ourdata.dao.entity.ResourceBdDao;
import br.ifpb.simba.ourdata.entity.Period;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import br.ifpb.simba.ourdata.entity.ResourceTimeSearch;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class QueryResourceBo {

    private ResourceBdDao resourceDao;

    public QueryResourceBo() {
        resourceDao = new ResourceBdDao();
    }

    public List<Resource> listResourcesIntersectedBy(Place place) {
        return getResourceDao().getResourcesIntersectedBy(place);
    }

    public List<Resource> listResourcesIntersectedByEvaluation(Place place) {
        return getResourceDao().getResourcesIntersectedByEvaluation(place);
    }

    public List<ResourceTimeSearch> listResourcesIntersectedBy(Period period) {
        return getResourceDao().getResourcesIntersectedBy(period);
    }

    /**
     * @return the resourceDao
     */
    public ResourceBdDao getResourceDao() {
        return resourceDao;
    }

    /**
     * @param resourceDao the resourceDao to set
     */
    public void setResourceDao(ResourceBdDao resourceDao) {
        this.resourceDao = resourceDao;
    }
    
}
