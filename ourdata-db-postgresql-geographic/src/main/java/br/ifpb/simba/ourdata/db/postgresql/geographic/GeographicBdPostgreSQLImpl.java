package br.ifpb.simba.ourdata.db.postgresql.geographic;

import br.ifpb.simba.ourdata.db.postgresql.GeographicBdPostgreSQL;
import br.ifpb.simba.ourdata.shared.entities.KeyPlace;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 06:45:50
 */
public class GeographicBdPostgreSQLImpl extends GeographicBdPostgreSQL {

    public GeographicBdPostgreSQLImpl() {
    }

    public GeographicBdPostgreSQLImpl(String propertiesPath) {
        setPropertiesPath(propertiesPath);
    }
    
    @Override
    public boolean remove(String resourceId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exists(String resourceId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(KeyPlace keyPlace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KeyPlace> listAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KeyPlace> getByResourceId(String resourceId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
