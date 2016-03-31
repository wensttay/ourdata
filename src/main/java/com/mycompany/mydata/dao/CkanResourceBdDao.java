/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.jackan.model.CkanTrackingSummary;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
            String sql = "INSERT INTO RESOURCE values (?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?)";
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
            ps.setString(13, obj.getPackageId());
            ps.setInt(14, obj.getPosition());
            ps.setString(15, obj.getResourceGroupId());
            ps.setString(16, obj.getResourceType());
            ps.setString(17, obj.getRevisionTimestamp());
            ps.setString(18, obj.getSize());
            ps.setString(19, obj.getState().toString());
            ps.setString(20, obj.getUrlType());
            ps.setTimestamp(21, obj.getWebstoreLastUpdated());
            ps.setString(22, obj.getWebstoreUrl());
            
            if(obj.getOthers() != null)
                insertResourceOthers(obj.getOthers(), obj.getId());
            
            if(obj.getTrackingSummary() != null)
                insertDataSetTrackingSummary(obj.getTrackingSummary(), obj.getId());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    
   private boolean insertResourceOthers(Map<String, Object> others, String resourceId) {

        try {
            conectar();
            String sql;
            PreparedStatement ps;
            
            for (Map.Entry<String, Object> entry : others.entrySet()) {
                sql = "INSERT INTO RESOURCE_OTHER values (?, ?, ?)";
                ps = getConnection().prepareStatement(sql);
                ps.setString(1, entry.getKey());
                ps.setString(2, entry.getValue().toString());
                ps.setString(3, resourceId);
                ps.executeUpdate();
            }
            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
   
   private boolean insertDataSetTrackingSummary(CkanTrackingSummary trackingSummary, String resourceId) {
        
        try {
            conectar();
            String sql = "INSERT INTO DATASET_TRACKING_SUMMARY values (?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            
            ps.setInt(1, trackingSummary.getRecent());
            ps.setInt(2, trackingSummary.getTotal());
            ps.setString(3, resourceId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
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
