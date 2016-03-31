/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao;

import eu.trentorise.opendata.jackan.model.CkanActivity;
import eu.trentorise.opendata.jackan.model.CkanUser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CkanUserBdDao extends GenericBdDao<CkanUser, String>{
    
    CkanActivityBdDao activityBdDao;

    public CkanActivityBdDao getActivityBdDao() {
        if( activityBdDao == null)
            activityBdDao = new CkanActivityBdDao();
        return activityBdDao;
    }
    
    @Override
    public boolean insert(CkanUser obj) {
        try {
            conectar();
            String sql = "INSERT INTO USERS values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getAbout());
            ps.setString(3, obj.getCapacity());
            ps.setTimestamp(4, obj.getCreated());
            ps.setString(5, obj.getDisplayName());
            ps.setString(6, obj.getEmail());
            ps.setString(7, obj.getEmailHash());
            ps.setString(8, obj.getFullname());
            ps.setString(9, obj.getName());
            ps.setInt(10, obj.getNumFollowers());
            ps.setInt(11, obj.getNumberAdministeredPackages());
            ps.setInt(12, obj.getNumberOfEdits());
            ps.setString(13, obj.getOpenid());
            ps.setString(14, obj.getPassword());
            ps.setString(15, obj.getState().toString());
            ps.setBoolean(16, obj.isActivityStreamsEmailNotifications());
            ps.setBoolean(17, obj.isSysadmin());
            
            List<CkanActivity> auxListActivity =  obj.getActivity();
            if(auxListActivity == null)
                auxListActivity = new ArrayList<>();
            
            for (CkanActivity ckanActivity : auxListActivity) {
                getActivityBdDao().insert(ckanActivity);
                insertUserActivity(obj.getId(), ckanActivity.getId());
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
    public CkanUser get(String obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CkanUser> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean insertUserActivity(String idUser, String idActivity) {
        
        try {
            conectar();
            String sql = "INSERT INTO ACTIVITY_DATASET values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, idUser);
            ps.setString(2, idActivity);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

    }
    
}
