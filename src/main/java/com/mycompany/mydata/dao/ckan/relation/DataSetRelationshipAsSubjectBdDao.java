/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao.ckan.relation;

import com.mycompany.mydata.dao.GenericRelationBdDao;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author wensttay
 */
public class DataSetRelationshipAsSubjectBdDao extends GenericRelationBdDao<String, String>{

    @Override
    public boolean insert(String obj, String id) {
        
        try {
            conectar();
            String sql = "INSERT INTO DATASET_DATASET_RELATIONSHIP_AS_SUBJECT values (?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setString(1, obj);
            ps.setString(2, id);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;

    }
    
}
