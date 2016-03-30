/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanOrganization;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CkanOrganizationBdDao extends GenericBdDao<CkanOrganization, String>{

    @Override
    public boolean insert(CkanOrganization obj) {
        try {
            conectar();
            String sql = "INSERT INTO ORGANIZATION values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getApprovalStatus());
            ps.setTimestamp(3, obj.getCreated());
            ps.setString(4, obj.getDescription());
            ps.setString(5, obj.getDisplayName());
            ps.setString(6, obj.getImageDisplayUrl());
            ps.setString(7, obj.getImageUrl());
            ps.setString(8, obj.getName());
            ps.setInt(9, obj.getNumFollowers());
            ps.setInt(10, obj.getPackageCount());
            ps.setString(11, obj.getRevisionId());
            ps.setString(12, obj.getState().toString());
            ps.setString(13, obj.getTitle());
            ps.setString(14, obj.getType());
            ps.setBoolean(15, obj.isOrganization());
                     
            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public CkanOrganization get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanOrganization> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
