/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Wensttay
 */
public class CkanDatasetRelationshipObjectBdDao extends GenericObjectBdDao<CkanDatasetRelationship, String> {

    @Override
    public boolean insert(CkanDatasetRelationship obj) {
        try {
            conectar();
            String sql = "INSERT INTO DATASET_RELATIONSHIP_AS_OBJECT values (?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getComment());
            ps.setString(3, obj.getObject());
            ps.setString(4, obj.getSubject());
            ps.setString(5, obj.getType());

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

            String sql = "SELECT * FROM DATASET_RELATIONSHIP_AS_OBJECT WHERE ID = ?";

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
    public boolean update(CkanDatasetRelationship obj) {
        
        try {
            conectar();
            String sql = "UPDATE DATASET_RELATIONSHIP_AS_OBJECT SET COMMENT = ?, OBJECT = ?,"
                    + " SUBJECT = ?, TYPE = ?"
                    + " WHERE ID = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            
            ps.setString(1, obj.getComment());
            ps.setString(2, obj.getObject());
            ps.setString(3, obj.getSubject());
            ps.setString(4, obj.getType());
            ps.setString(5, obj.getId());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }

    @Override
    public void insertOrUpdate(CkanDatasetRelationship obj) {
        if (exist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
    }

    @Override
    public void insertOrUpdateAtributes(CkanDatasetRelationship obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
