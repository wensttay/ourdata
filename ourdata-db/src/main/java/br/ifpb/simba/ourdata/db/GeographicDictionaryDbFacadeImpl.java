package br.ifpb.simba.ourdata.db;

import br.ifpb.simba.ourdata.db.facade.GeographicDictionaryBdFacade;
import br.ifpb.simba.ourdata.db.shared.GeographicDictionaryBd;
import br.ifpb.simba.ourdata.shared.entities.Place;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:57:46
 */
public class GeographicDictionaryDbFacadeImpl implements GeographicDictionaryBdFacade {
    
    private GeographicDictionaryBd geographicDictionaryBd;

    public GeographicDictionaryDbFacadeImpl(GeographicDictionaryBd geographicDictionaryBd) {
        this.geographicDictionaryBd = geographicDictionaryBd;
    }
    
    @Override
    public List<Place> listAll() {
        return geographicDictionaryBd.listAll();
    }

    @Override
    public List<Place> getByTitle(String title) {
        return geographicDictionaryBd.getByTitle(title);
    }

    @Override
    public Place getByTitleAndType(String title, String type) {
        return geographicDictionaryBd.getByTitleAndType(title, type);
    }

}
