/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanPair;
import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.jackan.model.CkanTag;
import eu.trentorise.opendata.jackan.model.CkanTrackingSummary;
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

    CkanTagBdDao ckanTagBdDao;
    CkanResourceBdDao ckanResourceBdDao;
    CkanDatasetRelationshipBdDao ckanDatasetRelationshipBdDao;
    CkanOrganizationBdDao ckanOrganizationBdDao;
    CkanGroupBdBao ckanGroupBdBao;

    public CkanOrganizationBdDao getCkanOrganizationBdDao() {
        if(ckanOrganizationBdDao == null){
            ckanOrganizationBdDao = new CkanOrganizationBdDao();
        }
        return this.ckanOrganizationBdDao;
    }

    public CkanGroupBdBao getCkanGroupBdBao() {
        if(ckanGroupBdBao == null){
            ckanGroupBdBao = new CkanGroupBdBao();
        }
        return this.ckanGroupBdBao;
    }

    public CkanDatasetRelationshipBdDao getCkanDatasetRelationshipBdDao() {
        if (ckanDatasetRelationshipBdDao == null) {
            ckanDatasetRelationshipBdDao = new CkanDatasetRelationshipBdDao();
        }
        return this.ckanDatasetRelationshipBdDao;
    }

    public CkanResourceBdDao getCkanResourceBdDao() {
        if (ckanResourceBdDao == null) {
            ckanResourceBdDao = new CkanResourceBdDao();
        }
        return this.ckanResourceBdDao;
    }

    public CkanTagBdDao getCkanTagBdDao() {
        if (ckanTagBdDao == null) {
            ckanTagBdDao = new CkanTagBdDao();
        }
        return this.ckanTagBdDao;
    }

    @Override
    public boolean insert(CkanDataset obj) {
        try {
            conectar();
            
            String sql = "INSERT INTO DATASET values (?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ? )";
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
            ps.setString(20, String.valueOf(obj.getState()));
            ps.setString(21, obj.getTitle());
            ps.setString(22, obj.getType());
            ps.setString(23, obj.getUrl());
            ps.setString(24, obj.getVersion());
            ps.setBoolean(25, obj.isOpen());
            ps.setBoolean(26, obj.isPriv());
            
            
            if(obj.getOthers() != null)
                insertDataSetOthers(obj.getOthers(), obj.getId());
            
            if(obj.getExtras() != null)
                insertDataSetExtra(obj.getExtras(), obj.getId());    
            
            if(obj.getOrganization() != null){
                getCkanOrganizationBdDao().insert(obj.getOrganization());
                insertDataSetOrganization(obj.getId(), obj.getOrganization().getId());
            }
            
            if(obj.getTrackingSummary() != null)
                insertDataSetTrackingSummary(obj.getTrackingSummary(), obj.getId());
            
            List<CkanTag> auxListTag = obj.getTags();
            List<CkanResource> auxListResource = obj.getResources();
            List<CkanDatasetRelationship> auxListDatasetRelationshipsAsObject = obj.getRelationshipsAsObject();
            List<CkanDatasetRelationship> auxListDatasetRelationshipsAsSubject = obj.getRelationshipsAsSubject();
            List<CkanGroup> auxListGroup = obj.getGroups();
            
            for(CkanGroup cg : auxListGroup){
                getCkanGroupBdBao().insert(cg);
                insertDataSetGroup(obj.getId(), cg.getId());
            }
            
            for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsObject) {
                getCkanDatasetRelationshipBdDao().insert(cdr);
                insertDataSetDataSetRelationshipAsObject(obj.getId(), cdr.getId());
            }

            for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsSubject) {
                getCkanDatasetRelationshipBdDao().insertSubject(cdr);
                insertDataSetDataSetRelationshipAsSubject(obj.getId(), cdr.getId());
            }

            for (CkanTag ckanTag : auxListTag) {
                getCkanTagBdDao().insert(ckanTag);
                insertDataSetTag(obj.getId(), ckanTag.getId());
            }

            for (CkanResource ckanResource : auxListResource) {
                getCkanResourceBdDao().insert(ckanResource);
                insertDataSetTag(obj.getId(), ckanResource.getId());
                insertDataSetResources(obj.getId(), ckanResource.getId());
            }
            

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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

    private boolean insertDataSetOthers(Map<String, Object> others, String dataSetId) {

        try {
            conectar();
            String sql;
            PreparedStatement ps;
            
            for (Map.Entry<String, Object> entry : others.entrySet()) {
                sql = "INSERT INTO DATASET_OTHER values (?, ?, ?)";
                ps = getConnection().prepareStatement(sql);
                ps.setString(1, entry.getKey());
                ps.setString(2, entry.getValue().toString());
                ps.setString(3, dataSetId);
                ps.executeUpdate();
            }
            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }
    
    private boolean insertDataSetExtra(List<CkanPair> extra, String dataSetId) {
        
        try {
            conectar();
            String sql;
            PreparedStatement ps;

            for (CkanPair ckanPair : extra) {
                sql = "INSERT INTO DATASET_EXTRA values (?, ?, ?)";
                ps = getConnection().prepareStatement(sql);
                ps.setString(1, ckanPair.getKey());
                ps.setString(2, ckanPair.getValue().toString());
                ps.setString(3, dataSetId);
                ps.executeUpdate();
            }
            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private boolean insertDataSetTag(String dataSetId, String tagId) {

        try {
            conectar();
            String sql = "INSERT INTO DATASET_TAG values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, dataSetId);
            ps.setString(2, tagId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private boolean insertDataSetResources(String dataSetId, String resourcesId) {

        try {
            conectar();
            String sql = "INSERT INTO DATASET_RESOURCE values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, dataSetId);
            ps.setString(2, resourcesId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private boolean insertDataSetDataSetRelationshipAsObject(String dataSetId, String dataSetRelationshipAsObjectId) {

        try {
            conectar();
            String sql = "INSERT INTO DATASET_DATASET_RELATIONSHIP_AS_OBJECT values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, dataSetId);
            ps.setString(2, dataSetRelationshipAsObjectId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private boolean insertDataSetDataSetRelationshipAsSubject(String dataSetId, String dataSetRelationshipAsObjectId) {

        try {
            conectar();
            String sql = "INSERT INTO DATASET_DATASET_RELATIONSHIP_AS_SUBJECT values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, dataSetId);
            ps.setString(2, dataSetRelationshipAsObjectId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private boolean insertDataSetOrganization(String dataSetId, String dataSetOrganizationId) {

        try {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "INSERT INTO DATASET_ORGANIZATION values (?, ?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, dataSetId);
            ps.setString(2, dataSetOrganizationId);
            
            ps.executeUpdate();

            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private boolean insertDataSetGroup(String dataSetId, String dataSetGroupId) {
        
        try {
            conectar();
            String sql = "INSERT INTO DATASET_GRUPO values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, dataSetId);
            ps.setString(2, dataSetGroupId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean insertDataSetTrackingSummary(CkanTrackingSummary trackingSummary, String dataSetId) {
        
        try {
            conectar();
            String sql = "INSERT INTO DATASET_TRACKING_SUMMARY values (?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            
            ps.setInt(1, trackingSummary.getRecent());
            ps.setInt(2, trackingSummary.getTotal());
            ps.setString(3, dataSetId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
