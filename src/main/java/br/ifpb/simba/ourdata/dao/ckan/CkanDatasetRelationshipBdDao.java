/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan;

import br.ifpb.simba.ourdata.dao.GenericObjectBdDao;
import eu.trentorise.opendata.jackan.model.CkanActivity;
import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.postgresql.util.PSQLException;

/**
 *
 * @author wensttay
 */
public class CkanDatasetRelationshipBdDao extends GenericObjectBdDao<CkanDatasetRelationship, String> {

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
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe uma DataSetRelationshipObject com o ID: " + obj.getId());
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

    public boolean insertSubject(CkanDatasetRelationship obj) {
        try {
            conectar();
            String sql = "INSERT INTO DATASET_RELATIONSHIP_AS_SUBJECT values (?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getComment());
            ps.setString(3, obj.getObject());
            ps.setString(4, obj.getSubject());
            ps.setString(5, obj.getType());

            return (ps.executeUpdate() != 0);
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe uma DataSetRelationshipSubject com o ID: " + obj.getId());
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

    @Override
    public boolean isExist(String id) {
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

    public boolean isExistSubject(String id) {
        try {
            conectar();

            String sql = "SELECT * FROM DATASET_RELATIONSHIP_AS_SUBJECT WHERE ID = ?";

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean updateSubject(CkanDatasetRelationship obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertOrUpdate(CkanDatasetRelationship obj) {
//        Falta comparar as datas de utimo update
        if (isExist(obj.getId())) {
            update(obj);
        } else {
            insert(obj);
        }
    }

    public void insertOrUpdateSubject(CkanDatasetRelationship obj) {
//        Falta comparar as datas de utimo update
        if (isExistSubject(obj.getId())) {
            updateSubject(obj);
        } else {
            insertSubject(obj);
        }
    }
}
