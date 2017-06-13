package br.ifpb.simba.ourdata.db.postgresql;

import br.ifpb.simba.ourdata.db.postgresql.connection.GenericBdDao;
import br.ifpb.simba.ourdata.db.shared.TemporalBd;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 13/06/2017, 05:24:30
 */
public abstract class TemporalBdPostgreSQL extends GenericBdDao 
        implements TemporalBd {
}
