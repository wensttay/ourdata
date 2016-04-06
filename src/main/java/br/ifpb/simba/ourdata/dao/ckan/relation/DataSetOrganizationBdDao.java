/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.ckan.relation;

import br.ifpb.simba.ourdata.dao.GenericRelationBdDao;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author wensttay
 */
public class DataSetOrganizationBdDao extends GenericRelationBdDao<String, String>{

    @Override
    public boolean insert(String obj, String id) {
        
        try {
            conectar();
            String sql;
            PreparedStatement ps;

            sql = "INSERT INTO DATASET_ORGANIZATION values (?, ?)";
            ps = getConnection().prepareStatement(sql);

            ps.setString(1, obj);
            ps.setString(2, id);

            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;

    }
    
}
