/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericBdDao;
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
import org.postgresql.util.PSQLException;

/**
 *
 * @author wensttay
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
                getCkanGroupBdBao().insert(cg);
                getGrupoGruposBdDao().insert(cg.getId(), obj.getId());
            }
            
            for (CkanUser cuser : auxListUser) {
                getCkanUserBdDao().insert(cuser);
                getGrupoUserBdDao().insert(cuser.getId(), obj.getId());
            }
            
            for(CkanPair cp : auxListExtras){
                getGrupoExtraBdDao().insert(cp, obj.getId());
                
            }

            return (ps.executeUpdate() != 0);
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe um Group com o ID: " + obj.getId());
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
