/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.UserActivityBdDao;
import eu.trentorise.opendata.jackan.model.CkanActivity;
import eu.trentorise.opendata.jackan.model.CkanUser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class CkanUserBdDao extends GenericObjectBdDao<CkanUser, String> {

    CkanActivityBdDao activityBdDao;

    UserActivityBdDao userActivityBdDao;

    List<CkanActivity> auxListActivity;

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
            ps.setString(15, String.valueOf(obj.getState()));
            ps.setBoolean(16, obj.isActivityStreamsEmailNotifications());
            ps.setBoolean(17, obj.isSysadmin());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanUser obj) {
        try {
            conectar();
            String sql = "UPDATE USERS SET ABOUT = ?, CAPACITY = ?,"
                    + " CREATED = ?, DISPLAY_NAME = ?,"
                    + " EMAIL = ?, EMAIL_HASH = ?,"
                    + " FULL_NAME = ?, NAME = ?,"
                    + " NUM_FOLLOWERS = ?, NUMBER_ADMINISTERED_PACKAGES = ?,"
                    + " NUMBER_OF_EDITS = ?, OPEN_ID = ?,"
                    + " PASSWORD = ?, STATE = ?,"
                    + " IS_ACTIVITY_STREAMS_EMAIL_NOTIFICATIONS = ?, IS_SYS_ADMIN = ?"
                    + " WHERE ID = ?";
            
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getAbout());
            ps.setString(2, obj.getCapacity());
            ps.setTimestamp(3, obj.getCreated());
            ps.setString(4, obj.getDisplayName());
            ps.setString(5, obj.getEmail());
            ps.setString(6, obj.getEmailHash());
            ps.setString(7, obj.getFullname());
            ps.setString(8, obj.getName());
            ps.setInt(9, obj.getNumFollowers());
            ps.setInt(10, obj.getNumberAdministeredPackages());
            ps.setInt(11, obj.getNumberOfEdits());
            ps.setString(12, obj.getOpenid());
            ps.setString(13, obj.getPassword());
            ps.setString(14, String.valueOf(obj.getState()));
            ps.setBoolean(15, obj.isActivityStreamsEmailNotifications());
            ps.setBoolean(16, obj.isSysadmin());
            ps.setString(17, obj.getId());

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

            String sql = "SELECT * FROM USERS WHERE ID = ?";

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
    public void insertOrUpdate(CkanUser obj) {
        if (exist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
        insertOrUpdateAtributes(obj);
    }

    @Override
    public void insertOrUpdateAtributes(CkanUser obj) {

        auxListActivity = obj.getActivity();

        if (auxListActivity == null) {
            auxListActivity = new ArrayList<>();
        }

        for (CkanActivity ckanActivity : auxListActivity) {
            getActivityBdDao().insertOrUpdate(ckanActivity);
            getUserActivityBdDao().insert(obj.getId(), ckanActivity.getId());
        }
    }
    
    public CkanActivityBdDao getActivityBdDao() {
        if (activityBdDao == null) {
            activityBdDao = new CkanActivityBdDao();
        }
        return activityBdDao;
    }

    public UserActivityBdDao getUserActivityBdDao() {
        if (userActivityBdDao == null) {
            userActivityBdDao = new UserActivityBdDao();
        }
        return userActivityBdDao;
    }

}
