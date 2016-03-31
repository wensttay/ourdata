/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanPair;
import eu.trentorise.opendata.jackan.model.CkanUser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CkanGroupBdBao extends GenericBdDao<CkanGroup, String> {

    CkanGroupBdBao ckanGroupBdBao;
    CkanUserBdDao ckanUserBdDao;
    
    public CkanGroupBdBao getCkanGroupBdBao() {
        if(ckanGroupBdBao == null){
            ckanGroupBdBao = new CkanGroupBdBao();
        }
        return this.ckanGroupBdBao;
    }
    
    public CkanUserBdDao getCkanUserBdDao(){
        if(ckanUserBdDao == null){
            ckanUserBdDao = new CkanUserBdDao();
        }
        return this.ckanUserBdDao;
    }
    
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
            
            List<CkanGroup> auxListGroup = obj.getGroups();
            List<CkanUser> auxListUser = obj.getUsers();
            
            if(auxListGroup != null){
                for(CkanGroup cg : auxListGroup){
                    getCkanGroupBdBao().insert(cg);
                    insertGrupoGrupos(obj.getId(), cg.getId());
                }
            }
            
            if(auxListUser != null){
                for(CkanUser cuser : auxListUser){
                    getCkanUserBdDao().insert(cuser);
                    insertGrupoUser(obj.getId(), cuser.getId());
                }
            }
           
            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            //ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    
    public boolean insertGrupoExtra(CkanPair extra, String id_grupo) throws URISyntaxException, IOException, SQLException, ClassNotFoundException{
        conectar();
        String sql = "INSERT INTO GRUPO_EXTRA VALUES(?,?,?)";
        PreparedStatement pstm = getConnection().prepareCall(sql);
        pstm.setString(1,extra.getKey());
        pstm.setString(2,extra.getValue());
        pstm.setString(3,id_grupo);
        
        return (pstm.executeUpdate() != 0);
    }
    
    private boolean insertGrupoGrupos(String groupParentId, String groupSonId) {
        
        try {
            conectar();
            String sql = "INSERT INTO GRUPO_GRUPOS values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, groupParentId);
            ps.setString(2, groupSonId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean insertGrupoUser(String groupId, String userId){
        try {
            conectar();
            String sql = "INSERT INTO GRUPO_USER values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, groupId);
            ps.setString(2, userId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    @Override
    public CkanGroup get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanGroup> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
