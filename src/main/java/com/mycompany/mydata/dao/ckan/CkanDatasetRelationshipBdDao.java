/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao.ckan;

import com.mycompany.mydata.dao.GenericBdDao;
import com.mycompany.mydata.dao.GenericObjectBdDao;
import eu.trentorise.opendata.jackan.model.CkanDatasetRelationship;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.postgresql.util.PSQLException;

/**
 *
 * @author wensttay
 */
public class CkanDatasetRelationshipBdDao extends GenericObjectBdDao<CkanDatasetRelationship, String>{

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
            }else{
            	ex.printStackTrace();
            }
        }  catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
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
            }else{
            	ex.printStackTrace();
            }
        }  catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;
    }
    
}
