/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan.relation;

import br.ifpb.simba.ourdata.dao.GenericRelationBdDao;
import eu.trentorise.opendata.jackan.model.CkanTrackingSummary;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.ifpb.simba.ourdata.dao.DaoRelationUpdatable;

/**
 *
 * @author Wensttay
 */
public class ResourceTrackingSummaryBdDao extends GenericRelationBdDao<CkanTrackingSummary, String>
        implements DaoRelationUpdatable<CkanTrackingSummary, String> {

    @Override
    public boolean insert(CkanTrackingSummary obj, String id) {

        try {
            conectar();
            String sql = "INSERT INTO RESOURCE_TRACKING_SUMMARY values (?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setInt(1, obj.getRecent());
            ps.setInt(2, obj.getTotal());
            ps.setString(3, id);

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanTrackingSummary obj, String id) {
        try {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "UPDATE RESOURCE_TRACKING_SUMMARY SET RECENTE = ?, TOTAL = ? WHERE ID_DATASET = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setInt(1, obj.getRecent());
            ps.setInt(2, obj.getTotal());
            ps.setString(3, id);
            return (ps.executeUpdate() != 0);

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public void insertOrUpdate(CkanTrackingSummary obj, String id) {
        if (exist(obj, id)) {
            update(obj, id);
        } else {
            insert(obj, id);
        }
    }

    @Override
    public boolean exist(CkanTrackingSummary obj, String id) {
        try {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "SELECT * FROM DATASET_TRACKING_SUMMARY WHERE ID_DATASET = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeQuery().next();

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }
}
