/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.dao.PostgresConnectionProvider;
import br.ifpb.simba.ourdata.entity.ColumnIndexDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    
    @Override
    public List<ResourceColumnIndex> getResourcesColumnIndex() {
        
        String sql = "SELECT DISTINCT resource_id, rows_count FROM resource_column_index LIMIT 4";
        
        List<ResourceColumnIndex> resources = new LinkedList<>();
        
        try{
        
            if(this.conn == null || this.conn.isClosed())
                this.conn = PostgresConnectionProvider.getInstance().getConnection();
            
            this.pstm = conn.prepareStatement(sql);
            
            ResultSet rs = pstm.executeQuery();
            
            System.out.println("Executed resourceid,rowscount Query...");
            
            while(rs.next()) {
                resources.add(createResourceColumnIndex(rs));
            }
        
        } catch(SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return resources; 
    }
    
    private ResourceColumnIndex createResourceColumnIndex(ResultSet rs) throws SQLException {
        
        String resourceId = rs.getString("resource_id");
        Integer rowsCount = rs.getInt("rows_count");
        
        ResourceColumnIndex resource = new ResourceColumnIndex(resourceId, rowsCount);
        
        resource.addAll(getColumnsByResource(resourceId));
        
        return resource; 
    }
    
    private List<ColumnIndex> getColumnsByResource(String resourceId) {
        
        String sql = "SELECT DISTINCT column_number FROM resource_column_index WHERE resource_id = ?";
        
        List<ColumnIndex> columns = new LinkedList<>();
        
         try{
        
            if(this.conn == null || this.conn.isClosed())
                this.conn = PostgresConnectionProvider.getInstance().getConnection();
            
            this.pstm = conn.prepareStatement(sql);
            
            Integer count = 1;
            
            pstm.setString(count++, resourceId);
            
            ResultSet rs = pstm.executeQuery();
            
            System.out.println("Executed column_number query...");
            
            while(rs.next()) {
                columns.add(createColumnIndex(rs, resourceId));
            }
        
        } catch(SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }  
         
        return columns;
    }
    
    private ColumnIndex createColumnIndex(ResultSet rs, String resourceId) throws SQLException {
        
        Integer columnNumber = rs.getInt("column_number");
        
        ColumnIndex column = new ColumnIndex(columnNumber);
        
        column.addAll(getValues(columnNumber, resourceId));
        
        return column;
    } 
    
    private Set<String> getValues(Integer columnNumber, String resourceId) {
        
        String sql = "SELECT column_value FROM resource_column_index WHERE column_number = ? AND resource_id = ?";
        
        Set<String> values = new TreeSet<>();
        
        try{
        
            if(this.conn == null || this.conn.isClosed())
                this.conn = PostgresConnectionProvider.getInstance().getConnection();
            
            this.pstm = conn.prepareStatement(sql);
            
            Integer count = 1;
            
            pstm.setInt(count++, columnNumber);
            pstm.setString(count++, resourceId);
            
            ResultSet rs = pstm.executeQuery();
            
            System.out.println("Executed values query...");
            
            while(rs.next()) {
                values.add(rs.getString("column_value"));
            }
        
        } catch(SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return values;
    }
    
    
    
     private ColumnIndexDTO createColumnIndexDTO(ResultSet rs) throws SQLException {
        
        Long id = rs.getLong("id");
        String columnValue = rs.getString("column_value");
        Integer columnNumber = rs.getInt("column_number");
        Integer rowsCount = rs.getInt("rows_count");
        String resourceId = rs.getString("resource_id");
        String datasetId = rs.getString("dataset_id");
        
        ColumnIndexDTO columnIndexDTO = new ColumnIndexDTO(id, columnValue, rowsCount, columnNumber, resourceId, datasetId);
        
        return columnIndexDTO;
        
    }
    
    @Override
    public List<ColumnIndexDTO> list(Long start, Long quantity) {
        
        List<ColumnIndexDTO> listColumnIndexDTO = new LinkedList<>();
        String sql = "SELECT * FROM resource_column_index ORDER BY resource_id, column_number OFFSET ? LIMIT ?";
        
        try {
            
            if(this.conn == null || this.conn.isClosed())
                this.conn = PostgresConnectionProvider.getInstance().getConnection();
            
            this.pstm = conn.prepareStatement(sql);
            
            Integer count = 1;
            
            pstm.setLong(count++, start);
            pstm.setLong(count++, quantity);
            
            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()) {
                
                listColumnIndexDTO.add(createColumnIndexDTO(rs));
            }
            
            return listColumnIndexDTO;
            
            
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            
        }
        return null;
    }
    
   
    
    
    
    
}
