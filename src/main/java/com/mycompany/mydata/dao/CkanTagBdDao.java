/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanTag;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CkanTagBdDao extends GenericBdDao<CkanTag, String>{

    @Override
    public boolean insert(CkanTag obj) {
        try {
            conectar();
            String sql = "INSERT INTO TAG values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getDisplayName());
            ps.setString(3, obj.getName());
            ps.setTimestamp(4, obj.getRevisionTimestamp());
            ps.setString(5, obj.getState().toString());
            ps.setString(6, obj.getVocabularyId());
            
            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public CkanTag get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanTag> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
