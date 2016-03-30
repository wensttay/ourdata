/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanActivity;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CkanActivityBdDao extends GenericBdDao<CkanActivity, String>{

    @Override
    public boolean insert(CkanActivity obj) {
        try {
            conectar();
            String sql = "INSERT INTO ACTIVITY values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setTimestamp(2, obj.getApprovedTimestamp());
            ps.setString(3, obj.getAuthor());
            ps.setString(4, obj.getMessage());
            ps.setString(5, obj.getState().toString());
            ps.setTimestamp(6, obj.getTimestamp());
            
            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public CkanActivity get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanActivity> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
