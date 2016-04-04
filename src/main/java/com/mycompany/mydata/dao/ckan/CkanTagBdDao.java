/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao.ckan;

import com.mycompany.mydata.dao.GenericBdDao;
import com.mycompany.mydata.dao.GenericObjectBdDao;
import eu.trentorise.opendata.jackan.model.CkanTag;
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
        } catch (PSQLException ex) {
            if (ex.getErrorCode() == 0) {
                System.out.println("Error: Já existe uma Tag com o ID: " + obj.getId());
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

}
