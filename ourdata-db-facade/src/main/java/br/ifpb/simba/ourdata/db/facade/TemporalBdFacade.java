package br.ifpb.simba.ourdata.db.facade;

import br.ifpb.simba.ourdata.shared.entities.KeyTime;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:24:30
 */
public interface TemporalBdFacade {
    
    public boolean remove(String resourceId);
    
    public boolean exists(String resourceId);
    
    public boolean insert(KeyTime keyTime);

    public List<KeyTime> listAll();
}
