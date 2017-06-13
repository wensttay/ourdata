package br.ifpb.simba.ourdata.db;

import br.ifpb.simba.ourdata.db.facade.GeographicBdFacade;
import br.ifpb.simba.ourdata.db.postgresql.geographic.GeographicBdPostgreSQLImpl;
import br.ifpb.simba.ourdata.db.shared.GeographicBd;
import br.ifpb.simba.ourdata.shared.entities.KeyPlace;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:57:13
 */
public class GeographicBdFacadeImpl implements GeographicBdFacade {
    
    private GeographicBd geographicBd;

    public GeographicBdFacadeImpl() {
        geographicBd = new GeographicBdPostgreSQLImpl();
    }

    public GeographicBdFacadeImpl(GeographicBd geographicBd) {
        this.geographicBd = geographicBd;
    }

    @Override
    public boolean remove(String resourceId) {
        return geographicBd.remove(resourceId);
    }

    @Override
    public boolean exists(String resourceId) {
        return geographicBd.exists(resourceId);
    }

    @Override
    public boolean insert(KeyPlace keyPlace) {
        return geographicBd.insert(keyPlace);
    }

    @Override
    public List<KeyPlace> listAll() {
        return geographicBd.listAll();
    }

    @Override
    public List<KeyPlace> getByResourceId(String resourceId) {
        return geographicBd.getByResourceId(resourceId);
    }

}
