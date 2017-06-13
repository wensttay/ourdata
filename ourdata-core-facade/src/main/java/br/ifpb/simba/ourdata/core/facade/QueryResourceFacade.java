package br.ifpb.simba.ourdata.core.facade;

import br.ifpb.simba.ourdata.shared.entities.Geometry;
import br.ifpb.simba.ourdata.shared.entities.Period;
import br.ifpb.simba.ourdata.shared.entities.ResourceItemSearch;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 04:44:39
 */
public interface QueryResourceFacade {
    
    public List<ResourceItemSearch> queryByGeometry(Geometry geometry);
    
    public List<ResourceItemSearch> queryByPeriod(Period period);
    
}
