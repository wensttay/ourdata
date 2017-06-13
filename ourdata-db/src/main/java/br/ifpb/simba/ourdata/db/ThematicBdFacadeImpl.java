package br.ifpb.simba.ourdata.db;

import br.ifpb.simba.ourdata.db.facade.ThematicBdFacade;
import br.ifpb.simba.ourdata.db.postgresql.thematic.ThematicBdPostgreSQLImpl;
import br.ifpb.simba.ourdata.db.shared.ThematicBd;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:57:25
 */
public class ThematicBdFacadeImpl implements ThematicBdFacade {
    
    private ThematicBd thematicBd;
    
    public ThematicBdFacadeImpl() {
        thematicBd = new ThematicBdPostgreSQLImpl();
    }

    public ThematicBdFacadeImpl(ThematicBd thematicBd) {
        this.thematicBd = thematicBd;
    }

}
