/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import java.util.List;

/**
 *
 * @author Pedro Arthur
 */
public class ColumnIndexDTOCassandraImpl implements ColumnIndexDao {
    
    private final Cluster cluster;
    private final Session session;
    private PreparedStatement pstm;
    
    public ColumnIndexDTOCassandraImpl() {
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("ourdata");
    }

    @Override
    public void insert(ColumnIndexDTO c) {
        
        String sql = "INSERT INTO resource_column_index (id,column_value,column_number,row_count,resource_id,dataset_id)"
                + " VALUES(?,?,?,?,?,?)";
        
        if(this.pstm == null)
            this.pstm = session.prepare(sql);
        
        BoundStatement bind = pstm.bind(c.getId(),c.getValue(),
                c.getColumnNumber(),c.getRowsCount(),
                c.getResourceId(),c.getDatasetId());
        
        session.execute(bind);
    }

    @Override
    public List<ColumnIndexDTO> list(Long start, Long quantity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ResourceColumnIndex> getResourcesColumnIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
