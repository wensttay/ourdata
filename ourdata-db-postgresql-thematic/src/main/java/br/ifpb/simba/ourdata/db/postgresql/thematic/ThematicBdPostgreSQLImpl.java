package br.ifpb.simba.ourdata.db.postgresql.thematic;

import br.ifpb.simba.ourdata.db.postgresql.ThematicBdPostgreSQL;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 06:53:09
 */
public class ThematicBdPostgreSQLImpl extends ThematicBdPostgreSQL {

    public ThematicBdPostgreSQLImpl() {
    }

    public ThematicBdPostgreSQLImpl(String PropertiesPath) {
        setPropertiesPath(PropertiesPath);
    }
    
}
