/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericBdDao;
import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.ResourceOthersBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.ResourceTrackingSummaryBdDao;
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
import org.postgresql.util.PSQLException;

/**
 *
 * @author wensttay
 */
public class CkanResourceBdDao extends GenericObjectBdDao<CkanResource, String> {

    ResourceOthersBdDao resourceOthersBdDao;
    ResourceTrackingSummaryBdDao resourceTrackingSummaryBdDao;
    
    @Override
    public boolean insert(CkanResource obj) {
        try {
            conectar();
            String sql = "INSERT INTO RESOURCE values (?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?)";
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
            ps.setString(17, obj.getRevisionId());
            ps.setString(18, obj.getRevisionTimestamp());
            ps.setString(19, obj.getSize());
            ps.setString(20, String.valueOf(obj.getState()));
            ps.setString(21, obj.getUrl());
            ps.setString(22, obj.getUrlType());
            ps.setTimestamp(23, obj.getWebstoreLastUpdated());
            ps.setString(24, obj.getWebstoreUrl());

            if (obj.getOthers() != null) {
                getResourceOthersBdDao().insert(obj.getOthers(), obj.getId());
            }

            if (obj.getTrackingSummary() != null) {
                getResourceTrackingSummaryBdDao().insert(obj.getTrackingSummary(), obj.getId());
            }
            
            return (ps.executeUpdate() != 0);
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe uma Resource com o ID: " + obj.getId());
            } else {
                ex.printStackTrace();
            }
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    public ResourceOthersBdDao getResourceOthersBdDao() {
        if (resourceOthersBdDao == null) {
            resourceOthersBdDao = new ResourceOthersBdDao();
        }
        return resourceOthersBdDao;
    }

    public ResourceTrackingSummaryBdDao getResourceTrackingSummaryBdDao() {
        if(resourceTrackingSummaryBdDao == null)
            resourceTrackingSummaryBdDao = new ResourceTrackingSummaryBdDao();
        return resourceTrackingSummaryBdDao;
    }
    
}
