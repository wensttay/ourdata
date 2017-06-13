package br.ifpb.simba.ourdata.db.facade;

import br.ifpb.simba.ourdata.shared.entities.Place;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:35:46
 */
public interface GeographicDictionaryBdFacade {
    
    public List<Place> listAll();
    
    public List<Place> getByTitle(String title);
    
    public Place getByTitleAndType(String title, String type);
    
}
