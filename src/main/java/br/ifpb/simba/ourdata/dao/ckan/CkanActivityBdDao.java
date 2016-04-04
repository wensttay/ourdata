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
import org.postgresql.util.PSQLException;

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
                getActivityDataSetBdDao().insert(obj.getId(), ckanDataset.getId());
            }

            return (ps.executeUpdate() != 0);
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe uma Activity com o ID: " + obj.getId());
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

    @Override
    public boolean update(CkanActivity obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isExist(String id) {
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
        //        Falta comparar as datas de utimo update
        if (isExist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }

    }
}
