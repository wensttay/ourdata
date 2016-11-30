/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.dao.PostgresConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.ifpb.simba.ourdata.entity.DataRelation;

/**
 *
 * @author kieckegard
 */
public class DataRelationPostgresImpl implements DataRelationDao {
    
    private PreparedStatement pstm;
    private Connection conn;  
    
    @Override
    public void insert (DataRelation dataRelation) {
        
        String sql = "INSERT INTO data_relation(first_column_value, first_column_number, "
                + "first_resource_id, second_column_value, second_column_number, second_resource_id) "
                + "VALUES(?,?,?,?,?,?) "
                + "RETURNING id";
        
        try {
            
            if(this.conn == null || this.conn.isClosed())
                this.conn = PostgresConnectionProvider.getInstance().getConnection();
            
            if(this.pstm == null || this.pstm.isClosed())
                this.pstm = conn.prepareStatement(sql);
            
            Integer count = 1;
            
            pstm.setString(count++, dataRelation.getFirstColumnValue());
            pstm.setInt(count++, dataRelation.getFirstColumnNumber());
            pstm.setString(count++, dataRelation.getFirstResourceId());
            
            pstm.setString(count++, dataRelation.getSecondColumnValue());
            pstm.setInt(count++, dataRelation.getSecondColumnNumber());
            pstm.setString(count++, dataRelation.getSecondResourceId());
            
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next())
                dataRelation.setId(rs.getLong("id"));
                    
        } catch(SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
