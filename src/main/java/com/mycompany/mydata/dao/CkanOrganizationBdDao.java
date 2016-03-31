/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanGroup;
import eu.trentorise.opendata.jackan.model.CkanOrganization;
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
public class CkanOrganizationBdDao extends GenericBdDao<CkanOrganization, String>{

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
    public boolean insert(CkanOrganization obj) {
        try {
            conectar();
            String sql = "INSERT INTO ORGANIZATION values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(12, obj.getState().toString());
            ps.setString(13, obj.getTitle());
            ps.setString(14, obj.getType());
            ps.setBoolean(15, obj.isOrganization());
            
               
            List<CkanGroup> auxListGroup = obj.getGroups();
            List<CkanUser> auxListUser = obj.getUsers();
            
            for(CkanGroup cg : auxListGroup){
                getCkanGroupBdBao().insert(cg);
                insertOrganizationGroup(obj.getId(), cg.getId());
            }
            
            for(CkanUser cuser : auxListUser){
                getCkanUserBdDao().insert(cuser);
                insertOrganizationUser(obj.getId(), cuser.getId());
            }
                    
            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    
    public boolean insertOrganizationExtra(CkanPair extra, String id_organization) throws URISyntaxException, IOException, SQLException, ClassNotFoundException{
        conectar();
        String sql = "INSERT INTO ORGANIZATION_EXTRA VALUES(?,?,?)";
        PreparedStatement pstm = getConnection().prepareCall(sql);
        pstm.setString(1,extra.getKey());
        pstm.setString(2,extra.getValue());
        pstm.setString(3,id_organization);
        
        return (pstm.executeUpdate() != 0);
    }
    
    private boolean insertOrganizationGroup(String organizationId, String organizationGroupId) {
        
        try {
            conectar();
            String sql = "INSERT INTO ORGANIZATION_GRUPO values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, organizationId);
            ps.setString(2, organizationGroupId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean insertOrganizationUser(String organizationId, String userId){
        try {
            conectar();
            String sql = "INSERT INTO ORGANIZATION_USER values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, organizationId);
            ps.setString(2, userId);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public CkanOrganization get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanOrganization> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
