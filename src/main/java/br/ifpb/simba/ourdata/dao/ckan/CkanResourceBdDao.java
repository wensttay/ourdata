/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.ResourceOthersBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.ResourceTrackingSummaryBdDao;
import eu.trentorise.opendata.jackan.model.CkanPair;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wensttay, Pedro Arthur
 */
public class CkanResourceBdDao extends GenericObjectBdDao<CkanResource, String> {

    ResourceOthersBdDao resourceOthersBdDao;
    ResourceTrackingSummaryBdDao resourceTrackingSummaryBdDao;
    
    Map<String,Object> auxMapOthers;
    
    @Override
    public boolean insert(CkanResource obj) {
        try {
            conectar();
            /*String sql = "INSERT INTO RESOURCE values (?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?)";
            ps.setString(1, obj.getId()); //uses
            ps.setString(2, obj.getCacheLastUpdated());
            ps.setString(3, obj.getCacheUrl());
            ps.setTimestamp(4, obj.getCreated());
            ps.setString(5, obj.getDescription()); //uses
            ps.setString(6, obj.getFormat()); //uses
            ps.setString(7, obj.getHash());
            ps.setString(8, obj.getLastModified());
            ps.setString(9, obj.getMimetype());
            ps.setString(10, obj.getMimetypeInner());
            ps.setString(11, obj.getName());
            ps.setString(12, obj.getOwner());
            ps.setString(13, obj.getPackageId()); //uses
            ps.setInt(14, obj.getPosition());
            ps.setString(15, obj.getResourceGroupId());
            ps.setString(16, obj.getResourceType());
            ps.setString(17, obj.getRevisionId());
            ps.setString(18, obj.getRevisionTimestamp());
            ps.setString(19, obj.getSize());
            ps.setString(20, String.valueOf(obj.getState()));
            ps.setString(21, obj.getUrl()); //uses
            ps.setString(22, obj.getUrlType());
            ps.setTimestamp(23, obj.getWebstoreLastUpdated());
            ps.setString(24, obj.getWebstoreUrl());*/
            
            String sql = "INSERT INTO resource(id,description,format,id_dataset,url) VALUES(?,?,?,?,?)";
           
            PreparedStatement ps = getConnection().prepareStatement(sql);
            int i=1;
            
            ps.setString(i++, obj.getId());
            ps.setString(i++, obj.getDescription());
            ps.setString(i++, obj.getFormat());
            ps.setString(i++, obj.getPackageId());
            ps.setString(i++, obj.getUrl());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanResource obj) {
        try {
            conectar();
            /*String sql = "UPDATE RESOURCE SET CACHE_LAST_UPDATED = ?, CACHE_URL = ?,"
                    + " CREATED = ?, DESCRIPTION = ?,"
                    + " FORMAT = ?, HASH = ?,"
                    + " LAST_MODIFIED = ?, MIMETYPE = ?,"
                    + " MIMETYPE_INNER = ?, NAME = ?,"
                    + " OWNER = ?, ID_DATASET = ?,"
                    + " POSITION = ?, RESOURCE_GROUP_ID = ?,"
                    + " RESOURCE_TYPE = ?, REVISION_ID = ?,"
                    + " REVISION_TIMESTAMP = ?, SIZE = ?,"
                    + " STATE = ?, URL = ?,"
                    + " URL_TYPE = ?, WEBSTORE_LAST_UPDATED = ?,"
                    + " WEBSTORE_URL = ?"
                    + " WHERE ID = ?";
            
            ps.setString(1, obj.getCacheLastUpdated());
            ps.setString(2, obj.getCacheUrl());
            ps.setTimestamp(3, obj.getCreated());
            ps.setString(4, obj.getDescription()); //uses
            ps.setString(5, obj.getFormat()); //uses
            ps.setString(6, obj.getHash());
            ps.setString(7, obj.getLastModified());
            ps.setString(8, obj.getMimetype());
            ps.setString(9, obj.getMimetypeInner());
            ps.setString(10, obj.getName());
            ps.setString(11, obj.getOwner());
            ps.setString(12, obj.getPackageId()); //uses
            ps.setInt(13, obj.getPosition());
            ps.setString(14, obj.getResourceGroupId());
            ps.setString(15, obj.getResourceType());
            ps.setString(16, obj.getRevisionId());
            ps.setString(17, obj.getRevisionTimestamp());
            ps.setString(18, obj.getSize());
            ps.setString(19, String.valueOf(obj.getState()));
            ps.setString(20, obj.getUrl()); //uses
            ps.setString(21, obj.getUrlType());
            ps.setTimestamp(22, obj.getWebstoreLastUpdated());
            ps.setString(23, obj.getWebstoreUrl());
            ps.setString(24, obj.getId()); //uses*/
            
            String sql = "UPDATE RESOURCE SET DESCRIPTION = ?,"
                    + "FORMAT = ?, ID_DATASET = ?,"
                    + "URL = ? WHERE ID = ?";
            
            PreparedStatement ps = getConnection().prepareStatement(sql);
            int i=1;
            
            ps.setString(i++, obj.getDescription());
            ps.setString(i++, obj.getFormat());
            ps.setString(i++, obj.getPackageId());
            ps.setString(i++, obj.getUrl());
            ps.setString(i++, obj.getId());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean exist(String id) {
        try {
            conectar();

            String sql = "SELECT * FROM RESOURCE WHERE ID = ?";

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, id);

            return (ps.executeQuery().next());

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public void insertOrUpdate(CkanResource obj) {
        if (exist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
        //insertOrUpdateAtributes(obj);
    }

    @Override
    public void insertOrUpdateAtributes(CkanResource obj) {
        
        auxMapOthers = obj.getOthers();
        
        if(auxMapOthers == null){
            auxMapOthers = (Map<String, Object>) Collections.EMPTY_MAP;
        }
        
        for (Map.Entry<String, Object> entry : auxMapOthers.entrySet()) {
            CkanPair auxCkanPair = new CkanPair(entry.getKey(), String.valueOf(entry.getValue()));
            getResourceOthersBdDao().insertOrUpdate(auxCkanPair, obj.getId());
        }

        if (obj.getTrackingSummary() != null) {
            getResourceTrackingSummaryBdDao().insertOrUpdate(obj.getTrackingSummary(), obj.getId());
        }
    }

    public ResourceOthersBdDao getResourceOthersBdDao() {
        if (resourceOthersBdDao == null) {
            resourceOthersBdDao = new ResourceOthersBdDao();
        }
        return resourceOthersBdDao;
    }

    public ResourceTrackingSummaryBdDao getResourceTrackingSummaryBdDao() {
        if (resourceTrackingSummaryBdDao == null) {
            resourceTrackingSummaryBdDao = new ResourceTrackingSummaryBdDao();
        }
        return resourceTrackingSummaryBdDao;
    }
    
    public List<CkanResource> list(){
        try{
            super.conectar();
            String sql = "SELECT * FROM resource";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<CkanResource> resources = new ArrayList<>();
            while(rs.next()){
                CkanResource resource = new CkanResource();
                resource.setId(rs.getString("id"));
                resource.setDescription(rs.getString("description"));
                resource.setFormat(rs.getString("format"));
                resource.setPackageId(rs.getString("id_dataset"));
                resource.setUrl(rs.getString("url"));
                resources.add(resource);
            }
            return resources;
            
        }catch(SQLException | URISyntaxException | IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }finally{
            desconectar();
        }
        return null;
    }

}
