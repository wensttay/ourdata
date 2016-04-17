/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import eu.trentorise.opendata.jackan.model.CkanTag;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Wensttay
 */
public class CkanTagBdDao extends GenericObjectBdDao<CkanTag, String> {

    @Override
    public boolean insert(CkanTag obj) {
        try {
            conectar();
            String sql = "INSERT INTO TAG values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getDisplayName());
            ps.setString(3, obj.getName());
            ps.setTimestamp(4, obj.getRevisionTimestamp());
            ps.setString(5, String.valueOf(obj.getState()));
            ps.setString(6, obj.getVocabularyId());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public boolean update(CkanTag obj) {
        try {
            conectar();
            String sql = "UPDATE TAG SET DISPLAY_NAME = ?, NAME = ?,"
                    + " REVISION_TIMESTAMP = ?, STATE = ?,"
                    + " VOCABULARY_ID = ? WHERE ID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getDisplayName());
            ps.setString(2, obj.getName());
            ps.setTimestamp(3, obj.getRevisionTimestamp());
            ps.setString(4, String.valueOf(obj.getState()));
            ps.setString(5, obj.getVocabularyId());
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

            String sql = "SELECT * FROM TAG WHERE ID = ?";

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
    public void insertOrUpdate(CkanTag obj) {
        if (exist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
    }

    @Override
    public void insertOrUpdateAtributes(CkanTag obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
