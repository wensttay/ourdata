/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetResourcesBdDao;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wensttay, Pedro Arthur
 */
public class CkanDataSetBdDao extends GenericObjectBdDao<CkanDataset, String> {
    
    DataSetResourcesBdDao dataSetResourcesBdDao;
    /*
    CkanTagBdDao ckanTagBdDao;
    CkanDatasetRelationshipObjectBdDao ckanDatasetRelationshipObjectBdDao;
    CkanDatasetRelationshipSubjectBdDao ckanDatasetRelationshipSubjectBdDao;
    CkanOrganizationBdDao ckanOrganizationBdDao;
    CkanGroupBdBao ckanGroupBdBao;
    
    DataSetResourcesBdDao dataSetResourcesBdDao;
    DataSetOthersBdDao dataSetOthersBdDao;
    DataSetExtraBdDao dataSetExtraBdDao;
    DataSetTagBdDao dataSetTagBdDao;
    
    DataSetRelationshipAsObjectBdDao dataSetRelationshipAsObjectBdDao;
    DataSetRelationshipAsSubjectBdDao dataSetRelationshipAsSubjectBdDao;
    DataSetOrganizationBdDao dataSetOrganizationBdDao;
    DataSetGroupBdDao dataSetGroupBdDao;
    DataSetTrackingSummaryBdDao dataSetTrackingSummaryBdDao;

    List<CkanTag> auxListTag;
    
    List<CkanDatasetRelationship> auxListDatasetRelationshipsAsObject;
    List<CkanDatasetRelationship> auxListDatasetRelationshipsAsSubject;
    List<CkanGroup> auxListGroup;
    List<CkanPair> auxListExtra;
    Map<String, Object> auxMapOthers;
    */
    
    
    List<CkanResource> auxListResource;
    CkanResourceBdDao ckanResourceBdDao;
    
    Timestamp timestampModified;

    @Override
    public boolean insert(CkanDataset obj) {
        try {
            conectar();

            /* 
            String sql = "INSERT INTO DATASET values (?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ? )";

            ps.setString(1, obj.getId()); //uses
            ps.setString(2, obj.getAuthor()); //uses
            ps.setString(3, obj.getAuthorEmail());
            ps.setString(4, obj.getCreatorUserId());
            ps.setString(5, obj.getLicenseId());
            ps.setString(6, obj.getLicenseTitle());
            ps.setString(7, obj.getLicenseUrl());
            ps.setString(8, obj.getMaintainer());
            ps.setString(9, obj.getMaintainerEmail());
            ps.setTimestamp(10, obj.getMetadataCreated());
            ps.setTimestamp(11, obj.getMetadataModified());
            ps.setString(12, obj.getName()); //uses
            ps.setString(13, obj.getNotes()); //uses
            ps.setString(14, obj.getNotesRendered());
            ps.setInt(15, obj.getNumResources());
            ps.setInt(16, obj.getNumTags());
            ps.setString(17, obj.getOwnerOrg());
            ps.setString(18, obj.getRevisionId());
            ps.setTimestamp(19, obj.getRevisionTimestamp());
            ps.setString(20, String.valueOf(obj.getState()));
            ps.setString(21, obj.getTitle());
            ps.setString(22, obj.getType());
            ps.setString(23, obj.getUrl()); //uses
            ps.setString(24, obj.getVersion());

            if (obj.isOpen() != null) {
                ps.setBoolean(25, obj.isOpen());
            } else {
                ps.setBoolean(25, true);
            }

            if (obj.isPriv() != null) {
                ps.setBoolean(26, obj.isPriv());
            } else {
                ps.setBoolean(26, true);
            } */
            
            String sql = "INSERT INTO dataset(id,author,notes,title,name,metadata_created,metadata_modified) VALUES(?,?,?,?,?,?,?)";
            
            PreparedStatement ps = getConnection().prepareStatement(sql);
            int i=1;
            
            ps.setString(i++, obj.getId());
            ps.setString(i++, obj.getAuthor());
            ps.setString(i++, obj.getNotes());
            ps.setString(i++, obj.getTitle());
            ps.setString(i++, obj.getName());
            ps.setTimestamp(i++, obj.getMetadataCreated());
            ps.setTimestamp(i++, obj.getMetadataModified());
            
            

            return ps.executeUpdate() != 0;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanDataset obj) {
        try {
            conectar();

            /*String sql = "UPDATE DATASET SET AUTHOR = ?, AUTHOR_EMAIL = ?,"
                    + " CREATOR_USER_ID = ?, LICENSED_ID = ?,"
                    + " LICENSED_TITLE = ?, LICENSED_URL = ?,"
                    + " MAINTAINER = ?, MAINTAINER_EMAIL = ?,"
                    + " METADATA_CREATED = ?, METADATA_MODIFIED = ?,"
                    + " NAME = ?, NOTES = ?, NOTES_RENDERED = ?,"
                    + " NUM_RESOURCES = ?, NUM_TAGS = ?, OWNER_ORG = ?,"
                    + " REVISION_ID = ?,  REVISION_TIMESTAMP = ?,"
                    + " STATE = ?, TITLE = ?, TYPE = ?, URL = ?,"
                    + " VERSION = ?, IS_OPEN = ?, IS_PRIV = ?"
                    + " WHERE ID = ?";

            ps.setString(1, obj.getAuthor()); //uses
            ps.setString(2, obj.getAuthorEmail());
            ps.setString(3, obj.getCreatorUserId());
            ps.setString(4, obj.getLicenseId());
            ps.setString(5, obj.getLicenseTitle());
            ps.setString(6, obj.getLicenseUrl());
            ps.setString(7, obj.getMaintainer());
            ps.setString(8, obj.getMaintainerEmail());
            ps.setTimestamp(9, obj.getMetadataCreated());
            ps.setTimestamp(10, obj.getMetadataModified());
            ps.setString(11, obj.getName()); //uses
            ps.setString(12, obj.getNotes()); //uses
            ps.setString(13, obj.getNotesRendered());
            ps.setInt(14, obj.getNumResources());
            ps.setInt(15, obj.getNumTags());
            ps.setString(16, obj.getOwnerOrg());
            ps.setString(17, obj.getRevisionId());
            ps.setTimestamp(18, obj.getRevisionTimestamp());
            ps.setString(19, String.valueOf(obj.getState()));
            ps.setString(20, obj.getTitle()); //uses
            ps.setString(21, obj.getType());
            ps.setString(22, obj.getUrl());
            ps.setString(23, obj.getVersion());

            if (obj.isOpen() != null) {
                ps.setBoolean(24, obj.isOpen());
            } else {
                ps.setBoolean(24, true);
            }

            if (obj.isPriv() != null) {
                ps.setBoolean(25, obj.isPriv());
            } else {
                ps.setBoolean(25, true);
            }
            
            ps.setString(26, obj.getId()); //uses */
            
            String sql = "UPDATE dataset SET author = ?, notes = ?, title = ?, name = ?,"
                    + "metadata_created = ?, metadata_modified = ?"
                    + "WHERE id = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            int i=1;
            
            ps.setString(i++,obj.getAuthor());
            ps.setString(i++,obj.getNotes());
            ps.setString(i++,obj.getTitle());
            ps.setString(i++,obj.getName());
            ps.setString(i++,obj.getId());
            ps.setTimestamp(i++, obj.getMetadataCreated());
            ps.setTimestamp(i++, obj.getMetadataModified());
            
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

            String sql = "SELECT * FROM DATASET WHERE ID = ?";

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

    private Timestamp getTimestampModified(String id) {
        try {
            conectar();

            String sql = "SELECT METADATA_MODIFIED FROM DATASET WHERE ID = ?";

            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getTimestamp("METADATA_MODIFIED");

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return null;
    }

    @Override
    public void insertOrUpdate(CkanDataset obj) {
        
        if (exist(obj.getId())) {
            timestampModified = getTimestampModified(obj.getId());
            if (timestampModified != null && obj.getMetadataModified().after(timestampModified)) {
                System.out.println("Houve modificação no dataset "+obj.getId());
                 
                insertOrUpdateAtributes(obj);
            }
        } else {
            insert(obj);
            insertOrUpdateAtributes(obj);
        }

    }

    @Override
    public void insertOrUpdateAtributes(CkanDataset obj) {
        
        auxListResource = obj.getResources();
        if (auxListResource == null) {
            auxListResource = new ArrayList<>();
        }
        
        for (CkanResource ckanResource : auxListResource) {
            getCkanResourceBdDao().insertOrUpdate(ckanResource);
            getDataSetResourcesBdDao().insert(obj.getId(), ckanResource.getId());
        }
        
        
        /*if (obj.getOrganization() != null) {
            getCkanOrganizationBdDao().insertOrUpdate(obj.getOrganization());
            getDataSetOrganizationBdDao().insert(obj.getId(), obj.getOrganization().getId());
        }

        if (obj.getTrackingSummary() != null) {
            getDataSetTrackingSummaryBdDao().insertOrUpdate(obj.getTrackingSummary(), obj.getId());;
        }

        auxListTag = obj.getTags();
        auxListResource = obj.getResources();
        auxListDatasetRelationshipsAsObject = obj.getRelationshipsAsObject();
        auxListDatasetRelationshipsAsSubject = obj.getRelationshipsAsSubject();
        auxListGroup = obj.getGroups();
        auxListExtra = obj.getExtras();
        auxMapOthers = obj.getOthers();

        if (auxListTag == null) {
            auxListTag = new ArrayList<>();
        }

        if (auxListResource == null) {
            auxListResource = new ArrayList<>();
        }

        if (auxListDatasetRelationshipsAsObject == null) {
            auxListDatasetRelationshipsAsObject = new ArrayList<>();
        }

        if (auxListDatasetRelationshipsAsSubject == null) {
            auxListDatasetRelationshipsAsSubject = new ArrayList<>();
        }

        if (auxListGroup == null) {
            auxListGroup = new ArrayList<>();
        }
        
        if(auxListExtra == null){
            auxListExtra = new ArrayList<>();
        }
        
        if(auxMapOthers == null){
            auxMapOthers = (Map<String, Object>) Collections.EMPTY_MAP;
        }

        for (CkanTag ckanTag : auxListTag) {
            getCkanTagBdDao().insertOrUpdate(ckanTag);
            getDataSetTagBdDao().insert(obj.getId(), ckanTag.getId());
        }

        for (CkanResource ckanResource : auxListResource) {
            getCkanResourceBdDao().insertOrUpdate(ckanResource);
            getDataSetResourcesBdDao().insert(obj.getId(), ckanResource.getId());
        }

        for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsObject) {
            getCkanDatasetRelationshipObjectBdDao().insertOrUpdate(cdr);
            getDataSetRelationshipAsObjectBdDao().insert(obj.getId(), cdr.getId());
        }

        for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsSubject) {
            getCkanDatasetRelationshipSubjectBdDao().insertOrUpdate(cdr);
            getDataSetRelationshipAsSubjectBdDao().insert(obj.getId(), cdr.getId());
        }

        for (CkanGroup cg : auxListGroup) {
            getCkanGroupBdBao().insertOrUpdate(cg);
            getDataSetGroupBdDao().insert(obj.getId(), cg.getId());
        }
        
        for (CkanPair cp : auxListExtra) {
            getDataSetExtraBdDao().insertOrUpdate(cp, obj.getId());
        }
        
        for (Map.Entry<String, Object> entry : auxMapOthers.entrySet()) {
            CkanPair auxCkanPair = new CkanPair(entry.getKey(), String.valueOf(entry.getValue()));
            getDataSetOthersBdDao().insertOrUpdate(auxCkanPair, obj.getId());
        }*/
        
    }
    
    public CkanResourceBdDao getCkanResourceBdDao() {
        if (ckanResourceBdDao == null) {
            ckanResourceBdDao = new CkanResourceBdDao();
        }
        return this.ckanResourceBdDao;
    }

    public DataSetResourcesBdDao getDataSetResourcesBdDao() {
        if (dataSetResourcesBdDao == null) {
            dataSetResourcesBdDao = new DataSetResourcesBdDao();
        }
        return dataSetResourcesBdDao;
    }

    /*
    
    public DataSetResourcesBdDao getDataSetResourcesBdDao() {
        if (dataSetResourcesBdDao == null) {
            dataSetResourcesBdDao = new DataSetResourcesBdDao();
        }
        return dataSetResourcesBdDao;
    }
    
    public CkanOrganizationBdDao getCkanOrganizationBdDao() {
        if (ckanOrganizationBdDao == null) {
            ckanOrganizationBdDao = new CkanOrganizationBdDao();
        }
        return this.ckanOrganizationBdDao;
    }

    public CkanGroupBdBao getCkanGroupBdBao() {
        if (ckanGroupBdBao == null) {
            ckanGroupBdBao = new CkanGroupBdBao();
        }
        return this.ckanGroupBdBao;
    }

    public CkanDatasetRelationshipObjectBdDao getCkanDatasetRelationshipObjectBdDao() {
        if (ckanDatasetRelationshipObjectBdDao == null) {
            ckanDatasetRelationshipObjectBdDao = new CkanDatasetRelationshipObjectBdDao();
        }
        return this.ckanDatasetRelationshipObjectBdDao;
    }

    public CkanDatasetRelationshipSubjectBdDao getCkanDatasetRelationshipSubjectBdDao() {
        if (ckanDatasetRelationshipSubjectBdDao == null) {
            ckanDatasetRelationshipSubjectBdDao = new CkanDatasetRelationshipSubjectBdDao();
        }
        return this.ckanDatasetRelationshipSubjectBdDao;

    }

    public CkanTagBdDao getCkanTagBdDao() {
        if (ckanTagBdDao == null) {
            ckanTagBdDao = new CkanTagBdDao();
        }
        return this.ckanTagBdDao;
    }

    public DataSetOthersBdDao getDataSetOthersBdDao() {
        if (dataSetOthersBdDao == null) {
            dataSetOthersBdDao = new DataSetOthersBdDao();
        }
        return dataSetOthersBdDao;
    }

    public DataSetExtraBdDao getDataSetExtraBdDao() {
        if (dataSetExtraBdDao == null) {
            dataSetExtraBdDao = new DataSetExtraBdDao();
        }
        return dataSetExtraBdDao;
    }

    public DataSetTagBdDao getDataSetTagBdDao() {
        if (dataSetTagBdDao == null) {
            dataSetTagBdDao = new DataSetTagBdDao();
        }
        return dataSetTagBdDao;
    }
    
    public DataSetRelationshipAsObjectBdDao getDataSetRelationshipAsObjectBdDao() {
        if (dataSetRelationshipAsObjectBdDao == null) {
            dataSetRelationshipAsObjectBdDao = new DataSetRelationshipAsObjectBdDao();
        }
        return dataSetRelationshipAsObjectBdDao;
    }

    public DataSetRelationshipAsSubjectBdDao getDataSetRelationshipAsSubjectBdDao() {
        if (dataSetRelationshipAsSubjectBdDao == null) {
            dataSetRelationshipAsSubjectBdDao = new DataSetRelationshipAsSubjectBdDao();
        }
        return dataSetRelationshipAsSubjectBdDao;
    }

    public DataSetOrganizationBdDao getDataSetOrganizationBdDao() {
        if (dataSetOrganizationBdDao == null) {
            dataSetOrganizationBdDao = new DataSetOrganizationBdDao();
        }
        return dataSetOrganizationBdDao;
    }

    public DataSetGroupBdDao getDataSetGroupBdDao() {
        if (dataSetGroupBdDao == null) {
            dataSetGroupBdDao = new DataSetGroupBdDao();
        }
        return dataSetGroupBdDao;
    }

    public DataSetTrackingSummaryBdDao getDataSetTrackingSummaryBdDao() {
        if (dataSetTrackingSummaryBdDao == null) {
            dataSetTrackingSummaryBdDao = new DataSetTrackingSummaryBdDao();
        }
        return dataSetTrackingSummaryBdDao;
    }*/
}
