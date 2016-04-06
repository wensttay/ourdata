/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.ActivityDataSetBdDao;
import br.ifpb.simba.ourdata.dao.ckan.relation.ActivityGroupBdDao;
import eu.trentorise.opendata.jackan.model.CkanActivity;
import eu.trentorise.opendata.jackan.model.CkanDataset;
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
public class CkanActivityBdDao extends GenericObjectBdDao<CkanActivity, String> {

    ActivityDataSetBdDao activityDataSetBdDao;
    ActivityGroupBdDao activityGroupBdDao;

    List<String> auxListGroup;
    List<CkanDataset> auxListDataSet;

    @Override
    public boolean insert(CkanActivity obj) {
        try {
            conectar();
            String sql = "INSERT INTO ACTIVITY values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setTimestamp(2, obj.getApprovedTimestamp());
            ps.setString(3, obj.getAuthor());
            ps.setString(4, obj.getMessage());
            ps.setString(5, String.valueOf(obj.getState()));
            ps.setTimestamp(6, obj.getTimestamp());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanActivity obj) {
        try {
            conectar();
            String sql = "UPDATE ACTIVITY SET APPROVED_TIMESTAMP = ?, AUTHOR = ?,"
                    + " MESSAGE = ?, STATE = ?,"
                    + " TIMESTAMP = ? WHERE ID = ?";

            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setTimestamp(1, obj.getApprovedTimestamp());
            ps.setString(2, obj.getAuthor());
            ps.setString(3, obj.getMessage());
            ps.setString(4, String.valueOf(obj.getState()));
            ps.setTimestamp(5, obj.getTimestamp());
            ps.setString(6, obj.getId());

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

            String sql = "SELECT * FROM ACTIVITY WHERE ID = ?";

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
    public void insertOrUpdate(CkanActivity obj) {
        if (exist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
        insertOrUpdateAtributes(obj);
    }

    @Override
    public void insertOrUpdateAtributes(CkanActivity obj) {

        auxListGroup = obj.getGroups();
        auxListDataSet = obj.getPackages();

        if (auxListGroup == null) {
            auxListGroup = new ArrayList<>();
        }
        if (auxListDataSet == null) {
            auxListDataSet = new ArrayList<>();
        }

        for (String string : auxListGroup) {
            getActivityGroupBdDao().insert(obj.getId(), string);
        }

        for (CkanDataset ckanDataset : auxListDataSet) {
//            TALVEZ AQUI PRECISE INSERIR NOVAMENTE DATASETs
            getActivityDataSetBdDao().insert(obj.getId(), ckanDataset.getId());
        }
    }

    public ActivityDataSetBdDao getActivityDataSetBdDao() {
        if (activityDataSetBdDao == null) {
            activityDataSetBdDao = new ActivityDataSetBdDao();
        }
        return activityDataSetBdDao;
    }

    public ActivityGroupBdDao getActivityGroupBdDao() {
        if (activityGroupBdDao == null) {
            activityGroupBdDao = new ActivityGroupBdDao();
        }
        return activityGroupBdDao;
    }

}
