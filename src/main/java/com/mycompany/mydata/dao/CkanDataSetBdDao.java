/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanDataset;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wensttay
 */
public class CkanDataSetBdDao extends GenericBdDao<CkanDataset, String> {
    
    @Override
    public boolean insert(CkanDataset obj) {
        try {
            conectar();
            String sql = "INSERT INTO DATASET values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, )";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, obj.getId());
            ps.setString(2, obj.getAuthor());
            ps.setString(3, obj.getAuthorEmail());
            ps.setString(4, obj.getCreatorUserId());
            ps.setString(5, obj.getLicenseId());
            ps.setString(6, obj.getLicenseTitle());
            ps.setString(7, obj.getLicenseUrl());
            ps.setString(8, obj.getMaintainer());
            ps.setString(9, obj.getMaintainerEmail());
            ps.setTimestamp(10, obj.getMetadataCreated());
            ps.setTimestamp(11, obj.getMetadataModified());
            ps.setString(12, obj.getName());
            ps.setString(13, obj.getNotes());
            ps.setString(14, obj.getNotesRendered());
            ps.setInt(15, obj.getNumResources());
            ps.setInt(16, obj.getNumTags());
            ps.setString(17, obj.getOwnerOrg());
            ps.setString(18, obj.getRevisionId());
            ps.setTimestamp(19, obj.getRevisionTimestamp());
            ps.setString(20, obj.getState().toString());
            ps.setString(21, obj.getTitle());
            ps.setString(22, obj.getType());
            ps.setString(23, obj.getUrl());
            ps.setString(24, obj.getVersion());
            ps.setBoolean(25, obj.isOpen());
            ps.setBoolean(26, obj.isPriv());
            insertOthers(obj.getOthers(), obj.getId());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public CkanDataset get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanDataset> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean insertOthers(Map<String, Object> others, String dataSetId) {

        try {
            conectar();
            String sql;
            PreparedStatement ps;

            for (Map.Entry<String, Object> entry : others.entrySet()) {
                sql = "INSERT INTO DATASET_TAGS values (?, ?, ?)";
                ps = getConnection().prepareStatement(sql);
                ps.setString(1, entry.getKey());
                ps.setString(2, entry.getValue().toString());
                ps.setString(3, dataSetId);
                ps.executeUpdate();
            }
            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            return false;
        }

    }
}
