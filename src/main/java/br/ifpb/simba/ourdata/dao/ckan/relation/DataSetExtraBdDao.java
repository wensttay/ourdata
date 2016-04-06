/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan.relation;

import br.ifpb.simba.ourdata.dao.DaoUpdatable;
import br.ifpb.simba.ourdata.dao.GenericRelationBdDao;
import eu.trentorise.opendata.jackan.model.CkanPair;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author wensttay, Pedro Arthur
 */
public class DataSetExtraBdDao extends GenericRelationBdDao<CkanPair, String>
        implements DaoUpdatable<CkanPair, String>
{

    @Override
    public boolean insert(CkanPair obj, String id)
    {
        try
        {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "INSERT INTO DATASET_EXTRA values (?, ?, ?)";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getKey());
            ps.setString(2, obj.getValue().toString());
            ps.setString(3, id);
            ps.executeUpdate();
            return true;

        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanPair obj, String id)
    {
        try
        {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "UPDATE DATASET_EXTRA SET KEY = ?, VALUE = ? WHERE ID_DATASET = ?";
            ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getKey());
            ps.setString(2, obj.getValue().toString());
            ps.setString(3, id);
            ps.executeUpdate();
            return true;

        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            desconectar();
        }
        return false;
    }

    @Override
    public void insertOrUpdate(CkanPair obj, String id)
    {
        if (exist(obj, id))
        {
            update(obj, id);
        }
        else
        {
            insert(obj, id);
        }
    }

    @Override
    public boolean exist(CkanPair obj, String id)
    {
        try
        {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "SELECT * FROM DATASET_EXTRA WHERE ID_DATASET=?";
            ps = getConnection().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            desconectar();
        }
        return false;
    }
}
