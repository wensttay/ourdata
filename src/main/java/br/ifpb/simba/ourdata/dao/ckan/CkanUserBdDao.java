/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericBdDao;
import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.UserActivityBdDao;
import eu.trentorise.opendata.jackan.model.CkanActivity;
import eu.trentorise.opendata.jackan.model.CkanUser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import org.postgresql.util.PSQLException;

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

            auxListActivity = obj.getActivity();
            
            if (auxListActivity == null) {
                auxListActivity = new ArrayList<>();
            }

            for (CkanActivity ckanActivity : auxListActivity) {
                getActivityBdDao().insert(ckanActivity);
                getUserActivityBdDao().insert(obj.getId(), ckanActivity.getId());
            }

            return (ps.executeUpdate() != 0);
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe um User com o ID: " + obj.getId());
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

    public CkanActivityBdDao getActivityBdDao() {
        if (activityBdDao == null) {
            activityBdDao = new CkanActivityBdDao();
        }
        return activityBdDao;
    }

    public UserActivityBdDao getUserActivityBdDao() {
        if(userActivityBdDao == null)
            userActivityBdDao = new UserActivityBdDao();
        return userActivityBdDao;
    }

    @Override
    public boolean update(CkanUser obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isExist(String id) {
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
//        Falta comparar as datas de utimo update
        if (isExist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
    }

}
