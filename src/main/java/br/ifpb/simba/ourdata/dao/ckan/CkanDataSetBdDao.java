/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetExtraBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetGroupBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetOrganizationBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetOthersBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetRelationshipAsObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetRelationshipAsSubjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetResourcesBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetTagBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.DataSetTrackingSummaryBdDao;
import eu.trentorise.opendata.jackan.model.CkanDataset;
import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanResource;
import eu.trentorise.opendata.jackan.model.CkanTag;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.postgresql.util.PSQLException;

/**
 *
 * @author wensttay
 */
public class CkanDataSetBdDao extends GenericObjectBdDao<CkanDataset, String> {

    CkanTagBdDao ckanTagBdDao;
    CkanResourceBdDao ckanResourceBdDao;
    CkanDatasetRelationshipBdDao ckanDatasetRelationshipBdDao;
    CkanOrganizationBdDao ckanOrganizationBdDao;
    CkanGroupBdBao ckanGroupBdBao;

    DataSetOthersBdDao dataSetOthersBdDao;
    DataSetExtraBdDao dataSetExtraBdDao;
    DataSetTagBdDao dataSetTagBdDao;
    DataSetResourcesBdDao dataSetResourcesBdDao;
    DataSetRelationshipAsObjectBdDao dataSetRelationshipAsObjectBdDao;
    DataSetRelationshipAsSubjectBdDao dataSetRelationshipAsSubjectBdDao;
    DataSetOrganizationBdDao dataSetOrganizationBdDao;
    DataSetGroupBdDao dataSetGroupBdDao;
    DataSetTrackingSummaryBdDao dataSetTrackingSummaryBdDao;

    List<CkanTag> auxListTag;
    List<CkanResource> auxListResource;
    List<CkanDatasetRelationship> auxListDatasetRelationshipsAsObject;
    List<CkanDatasetRelationship> auxListDatasetRelationshipsAsSubject;
    List<CkanGroup> auxListGroup;

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

            if (obj.isOpen() != null) {
                ps.setBoolean(25, obj.isOpen());
            } else {
                ps.setBoolean(25, true);
            }

            if (obj.isPriv() != null) {
                ps.setBoolean(26, obj.isPriv());
            } else {
                ps.setBoolean(26, true);
            }

            if (obj.getOthers() != null) {
                getDataSetOthersBdDao().insert(obj.getOthers(), obj.getId());
            }

            if (obj.getExtras() != null) {
                getDataSetExtraBdDao().insert(obj.getExtras(), obj.getId());
            }

            if (obj.getOrganization() != null) {
                getCkanOrganizationBdDao().insert(obj.getOrganization());
                getDataSetOrganizationBdDao().insert(obj.getId(), obj.getOrganization().getId());
            }

            if (obj.getTrackingSummary() != null) {
                getDataSetTrackingSummaryBdDao().insert(obj.getTrackingSummary(), obj.getId());
            }

            auxListTag = obj.getTags();
            auxListResource = obj.getResources();
            auxListDatasetRelationshipsAsObject = obj.getRelationshipsAsObject();
            auxListDatasetRelationshipsAsSubject = obj.getRelationshipsAsSubject();
            auxListGroup = obj.getGroups();

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

            for (CkanTag ckanTag : auxListTag) {
                getCkanTagBdDao().insert(ckanTag);
                getDataSetTagBdDao().insert(obj.getId(), ckanTag.getId());
            }

            for (CkanResource ckanResource : auxListResource) {
                getCkanResourceBdDao().insert(ckanResource);
                getDataSetResourcesBdDao().insert(obj.getId(), ckanResource.getId());
            }

            for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsObject) {
                getCkanDatasetRelationshipBdDao().insert(cdr);
                getDataSetRelationshipAsObjectBdDao().insert(obj.getId(), cdr.getId());
            }

            for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsSubject) {
                getCkanDatasetRelationshipBdDao().insertSubject(cdr);
                getDataSetRelationshipAsSubjectBdDao().insert(obj.getId(), cdr.getId());
            }

            for (CkanGroup cg : auxListGroup) {
                getCkanGroupBdBao().insert(cg);
                getDataSetGroupBdDao().insert(obj.getId(), cg.getId());
            }

            return (ps.executeUpdate() != 0);

        } catch (PSQLException ex) {

            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe um DataSet com o ID: " + obj.getId());
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

    public DataSetResourcesBdDao getDataSetResourcesBdDao() {
        if (dataSetResourcesBdDao == null) {
            dataSetResourcesBdDao = new DataSetResourcesBdDao();
        }
        return dataSetResourcesBdDao;
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
    }

    @Override
    public boolean update(CkanDataset obj) {
        try {
            conectar();

            String sql = "UPDATE DATASET SET AUTHOR = ?, AUTHOR_EMAIL = ?,"
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

            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, obj.getAuthor());
            ps.setString(2, obj.getAuthorEmail());
            ps.setString(3, obj.getCreatorUserId());
            ps.setString(4, obj.getLicenseId());
            ps.setString(5, obj.getLicenseTitle());
            ps.setString(6, obj.getLicenseUrl());
            ps.setString(7, obj.getMaintainer());
            ps.setString(8, obj.getMaintainerEmail());
            ps.setTimestamp(9, obj.getMetadataCreated());
            ps.setTimestamp(10, obj.getMetadataModified());
            ps.setString(11, obj.getName());
            ps.setString(12, obj.getNotes());
            ps.setString(13, obj.getNotesRendered());
            ps.setInt(14, obj.getNumResources());
            ps.setInt(15, obj.getNumTags());
            ps.setString(16, obj.getOwnerOrg());
            ps.setString(17, obj.getRevisionId());
            ps.setTimestamp(18, obj.getRevisionTimestamp());
            ps.setString(19, String.valueOf(obj.getState()));
            ps.setString(20, obj.getTitle());
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

            ps.setString(26, obj.getId());

            if (obj.getOthers() != null) {
//                getDataSetOthersBdDao().insert(obj.getOthers(), obj.getId());
            }

            if (obj.getExtras() != null) {
//                getDataSetExtraBdDao().insert(obj.getExtras(), obj.getId());
            }

            if (obj.getOrganization() != null) {
                getCkanOrganizationBdDao().insertOrUpdate(obj.getOrganization());
//                getDataSetOrganizationBdDao().insert(obj.getId(), obj.getOrganization().getId());
            }

            if (obj.getTrackingSummary() != null) {
//                getDataSetTrackingSummaryBdDao().insert(obj.getTrackingSummary(), obj.getId());
            }

            auxListTag = obj.getTags();
            auxListResource = obj.getResources();
            auxListDatasetRelationshipsAsObject = obj.getRelationshipsAsObject();
            auxListDatasetRelationshipsAsSubject = obj.getRelationshipsAsSubject();
            auxListGroup = obj.getGroups();

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

            for (CkanTag ckanTag : auxListTag) {
                getCkanTagBdDao().insertOrUpdate(ckanTag);
//                getDataSetTagBdDao().insert(obj.getId(), ckanTag.getId());
            }

            for (CkanResource ckanResource : auxListResource) {
                getCkanResourceBdDao().insertOrUpdate(ckanResource);
//                getDataSetResourcesBdDao().insert(obj.getId(), ckanResource.getId());
            }

            for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsObject) {
                getCkanDatasetRelationshipBdDao().insertOrUpdate(cdr);
//                getDataSetRelationshipAsObjectBdDao().insert(obj.getId(), cdr.getId());
            }

            for (CkanDatasetRelationship cdr : auxListDatasetRelationshipsAsSubject) {
                getCkanDatasetRelationshipBdDao().insertOrUpdateSubject(cdr);
//                getDataSetRelationshipAsSubjectBdDao().insert(obj.getId(), cdr.getId());
            }

            for (CkanGroup cg : auxListGroup) {
                getCkanGroupBdBao().insertOrUpdate(cg);
//                getDataSetGroupBdDao().insert(obj.getId(), cg.getId());
            }

            return (ps.executeUpdate() != 0);
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe um DataSet com o ID: " + obj.getId());
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

    @Override
    public boolean isExist(String id) {
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

    @Override
    public void insertOrUpdate(CkanDataset obj) {
//        Falta comparar as datas de utimo update
        if (isExist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
    }
}
