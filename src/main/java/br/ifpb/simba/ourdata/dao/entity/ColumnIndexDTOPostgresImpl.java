/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.dao.GenericBdDao;
import br.ifpb.simba.ourdata.dao.PostgresConnectionProvider;
import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pedro Arthur
 */
public class ColumnIndexDTOPostgresImpl implements ColumnIndexDao {
    
    private PreparedStatement pstm;
    private Connection conn;

    @Override
    public void insert(ColumnIndexDTO columnIndexDTO) {
        
        String sql = "INSERT INTO resource_column_index (column_value,column_number,rows_count,resource_id,dataset_id)"
                + " VALUES(?,?,?,?,?) RETURNING id";
        
        try {
            
            if(this.conn == null || this.conn.isClosed())
                this.conn = PostgresConnectionProvider.getInstance().getConnection();
            
            if(this.pstm == null || this.pstm.isClosed())
                this.pstm = conn.prepareStatement(sql);
            
            Integer count = 1;
            
            pstm.setString(count++, columnIndexDTO.getValue());
            pstm.setInt(count++, columnIndexDTO.getColumnNumber());
            pstm.setInt(count++, columnIndexDTO.getRowsCount());
            pstm.setString(count++, columnIndexDTO.getResourceId());
            pstm.setString(count++, columnIndexDTO.getDatasetId());
                    
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next())
                columnIndexDTO.setId(rs.getLong("id"));
                    
        } catch(SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    
}
