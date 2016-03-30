/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CkanDatasetRelationshipBdDao extends GenericBdDao<CkanDatasetRelationship, String>{

    @Override
    public boolean insert(CkanDatasetRelationship obj) {
        try {
            conectar();
            String sql = "INSERT INTO DATASET_RELATIONSHIP_AS_OBJECT values (?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getComment());
            ps.setString(3, obj.getObject());
            ps.setString(4, obj.getSubject());
            ps.setString(5, obj.getType());
            
            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            return false;
        } finally {
            desconectar();
        }
    }
    
    public boolean insertSubject(CkanDatasetRelationship obj) {
        try {
            conectar();
            String sql = "INSERT INTO DATASET_RELATIONSHIP_AS_SUBJECT values (?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getComment());
            ps.setString(3, obj.getObject());
            ps.setString(4, obj.getSubject());
            ps.setString(5, obj.getType());
            
            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public CkanDatasetRelationship get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanDatasetRelationship> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
