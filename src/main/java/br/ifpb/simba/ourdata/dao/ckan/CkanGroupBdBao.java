/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.GrupoExtraBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.GrupoGruposBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.GrupoUserBdDao;
import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanPair;
import eu.trentorise.opendata.jackan.model.CkanUser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wensttay, Pedro Arthur
 */
public class CkanGroupBdBao extends GenericObjectBdDao<CkanGroup, String> {

    CkanGroupBdBao ckanGroupBdBao;
    CkanUserBdDao ckanUserBdDao;

    GrupoExtraBdDao grupoExtraBdDao;
    GrupoGruposBdDao grupoGruposBdDao;
    GrupoUserBdDao grupoUserBdDao;

    List<CkanGroup> auxListGroup;
    List<CkanUser> auxListUser;
    List<CkanPair> auxListExtras;

    @Override
    public boolean insert(CkanGroup obj) {
        try {
            conectar();
            String sql = "INSERT INTO GRUPO values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(12, String.valueOf(obj.getState()));
            ps.setString(13, obj.getTitle());
            ps.setString(14, obj.getType());
            ps.setBoolean(15, obj.isOrganization());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanGroup obj) {
        try {
            conectar();
            String sql = "UPDATE GRUPO SET APPROVAL_STATUS = ?, CREATED = ?,"
                    + " DESCRIPTION = ?, DISPLAY_NAME = ?,"
                    + " IMAGE_DISPLAY_URL = ?, IMAGE_URL = ?,"
                    + " NAME = ?, NUM_FOLLOWERS = ?,"
                    + " DATASET_COUNT = ?, REVISION_ID = ?,"
                    + " STATE = ?, TITLE = ?,"
                    + " TYPE = ?, IS_ORGANIZATION = ?"
                    + " WHERE ID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            
            ps.setString(1, obj.getApprovalStatus());
            ps.setTimestamp(2, obj.getCreated());
            ps.setString(3, obj.getDescription());
            ps.setString(4, obj.getDisplayName());
            ps.setString(5, obj.getImageDisplayUrl());
            ps.setString(6, obj.getImageUrl());
            ps.setString(7, obj.getName());
            ps.setInt(8, obj.getNumFollowers());
            ps.setInt(9, obj.getPackageCount());
            ps.setString(10, obj.getRevisionId());
            ps.setString(11, String.valueOf(obj.getState()));
            ps.setString(12, obj.getTitle());
            ps.setString(13, obj.getType());
            ps.setBoolean(14, obj.isOrganization());
            ps.setString(15, obj.getId());

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

            String sql = "SELECT * FROM GRUPO WHERE ID = ?";

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
    public void insertOrUpdate(CkanGroup obj) {
        if (exist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
        insertOrUpdateAtributes(obj);
    }

    @Override
    public void insertOrUpdateAtributes(CkanGroup obj) {
        auxListGroup = obj.getGroups();
        auxListUser = obj.getUsers();

        if (auxListGroup == null) {
            auxListGroup = new ArrayList<>();
        }
        if (auxListUser == null) {
            auxListUser = new ArrayList<>();
        }
        if (auxListExtras == null) {
            auxListExtras = new ArrayList<>();
        }

        for (CkanGroup cg : auxListGroup) {
            getCkanGroupBdBao().insertOrUpdate(cg);
            getGrupoGruposBdDao().insert(cg.getId(), obj.getId());
        }

        for (CkanUser cuser : auxListUser) {
            getCkanUserBdDao().insertOrUpdate(cuser);
            getGrupoUserBdDao().insert(cuser.getId(), obj.getId());
        }

        for (CkanPair cp : auxListExtras) {
            getGrupoExtraBdDao().insertOrUpdate(cp, obj.getId());

        }
    }

    
    public CkanGroupBdBao getCkanGroupBdBao() {
        if (ckanGroupBdBao == null) {
            ckanGroupBdBao = new CkanGroupBdBao();
        }
        return this.ckanGroupBdBao;
    }

    public CkanUserBdDao getCkanUserBdDao() {
        if (ckanUserBdDao == null) {
            ckanUserBdDao = new CkanUserBdDao();
        }
        return this.ckanUserBdDao;
    }

    public GrupoExtraBdDao getGrupoExtraBdDao() {
        if (grupoExtraBdDao == null) {
            grupoExtraBdDao = new GrupoExtraBdDao();
        }
        return grupoExtraBdDao;
    }

    public GrupoGruposBdDao getGrupoGruposBdDao() {
        if (grupoGruposBdDao == null) {
            grupoGruposBdDao = new GrupoGruposBdDao();
        }
        return grupoGruposBdDao;
    }

    public GrupoUserBdDao getGrupoUserBdDao() {
        if (grupoUserBdDao == null) {
            grupoUserBdDao = new GrupoUserBdDao();
        }
        return grupoUserBdDao;
    }

}
