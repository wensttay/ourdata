package br.ifpb.simba.ourdata.db.facade;

import br.ifpb.simba.ourdata.shared.entities.KeyPlace;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:24:10
 */
public interface GeographicBdFacade {
    
    public boolean remove(String resourceId);
    
    public boolean exists(String resourceId);
    
    public boolean insert(KeyPlace keyPlace);
    
    public List<KeyPlace> listAll();
    
    public List<KeyPlace> getByResourceId(String resourceId);
    
}
