/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan.relation;

import br.ifpb.simba.ourdata.dao.GenericRelationBdDao;
import eu.trentorise.opendata.jackan.model.CkanPair;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.ifpb.simba.ourdata.dao.DaoRelationUpdatable;

/**
 *
 * @author wensttay
 */
public class ResourceOthersBdDao extends GenericRelationBdDao<CkanPair, String>
        implements DaoRelationUpdatable<CkanPair, String> {

    @Override
    public boolean insert(CkanPair obj, String id) {

        try {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "INSERT INTO RESOURCE_OTHER values (?, ?, ?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getKey());
            ps.setString(2, obj.getValue());
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
    public boolean update(CkanPair obj, String id) {
        try {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "UPDATE RESOURCE_OTHER SET KEY = ?, VALUE = ? WHERE ID_RESOURCE = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getKey());
            ps.setString(2, obj.getValue());
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
    public void insertOrUpdate(CkanPair obj, String id) {
        if (exist(obj, id)) {
            update(obj, id);
        } else {
            insert(obj, id);
        }
    }

    @Override
    public boolean exist(CkanPair obj, String id) {
        try {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "SELECT * FROM RESOURCE_OTHER WHERE ID_RESOURCE = ?";
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
