package br.ifpb.simba.ourdata.db.postgresql.temporal;

import br.ifpb.simba.ourdata.db.postgresql.TemporalBdPostgreSQL;
import br.ifpb.simba.ourdata.shared.entities.KeyTime;
import java.util.List;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 06:51:32
 */
public class TemporalBdPostgreSQLImpl extends TemporalBdPostgreSQL {

    public TemporalBdPostgreSQLImpl() {
    }

    public TemporalBdPostgreSQLImpl(String propertiesPath) {
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
    public boolean insert(KeyTime keyTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KeyTime> listAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
