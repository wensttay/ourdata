package br.ifpb.simba.ourdata.db;

import br.ifpb.simba.ourdata.db.facade.TemporalBdFacade;
import br.ifpb.simba.ourdata.db.postgresql.temporal.TemporalBdPostgreSQLImpl;
import br.ifpb.simba.ourdata.db.shared.TemporalBd;
import br.ifpb.simba.ourdata.shared.entities.KeyTime;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:56:53
 */
public class TemporalBdFacadeImpl implements TemporalBdFacade {

    private TemporalBd temporalBd;

    public TemporalBdFacadeImpl() {
        temporalBd = new TemporalBdPostgreSQLImpl();
    }

    public TemporalBdFacadeImpl(TemporalBd temporalBd) {
        this.temporalBd = temporalBd;
    }
    
    @Override
    public boolean remove(String resourceId) {
        return temporalBd.remove(resourceId);
    }

    @Override
    public boolean exists(String resourceId) {
        return temporalBd.exists(resourceId);
    }

    @Override
    public boolean insert(KeyTime keyTime) {
        return temporalBd.insert(keyTime);
    }

    @Override
    public List<KeyTime> listAll() {
        return temporalBd.listAll();
    }

}
