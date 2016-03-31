/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wensttay
 */
public class CkanResourceBdDao extends GenericBdDao<CkanResource, String> {

    @Override
    public boolean insert(CkanResource obj) {
        try {
            conectar();
            String sql = "INSERT INTO RESOURCE values (?, ?, ?, ?, ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, obj.getId());
            ps.setString(2, obj.getCacheLastUpdated());
            ps.setString(3, obj.getCacheUrl());
            ps.setTimestamp(4, obj.getCreated());
            ps.setString(5, obj.getDescription());
            ps.setString(6, obj.getFormat());
            ps.setString(7, obj.getHash());
            ps.setString(8, obj.getLastModified());
            ps.setString(9, obj.getMimetype());
            ps.setString(10, obj.getMimetypeInner());
            ps.setString(11, obj.getName());
            ps.setString(12, obj.getOwner());
            ps.setInt(13, obj.getPosition());
            ps.setString(14, obj.getResourceGroupId());
            ps.setString(15, obj.getResourceType());
            ps.setString(16, obj.getRevisionTimestamp());
            ps.setString(17, obj.getSize());
            ps.setString(18, obj.getState().toString());
            ps.setString(19, obj.getUrlType());
            ps.setTimestamp(20, obj.getWebstoreLastUpdated());
            ps.setString(21, obj.getWebstoreUrl());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public CkanResource get(String obj) {
        CkanResource cr = new CkanResource();

        try {
            conectar();
            String sql = "SELECT * FROM RESOURCE WHERE id = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj);
            ResultSet rs = ps.executeQuery();
            cr = preencherCkanResource(rs);

            return cr;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    public List<CkanResource> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private CkanResource preencherCkanResource(ResultSet rs) {
        CkanResource cr = new CkanResource();
        return cr;
    }

}
