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
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author wensttay
 */
public class DataSetExtraBdDao extends GenericRelationBdDao<List<CkanPair>, String> 
implements DaoUpdatable <List<CkanPair>,String>{

    @Override
    public boolean insert(List<CkanPair> obj, String id) {
        
        try {
            conectar();
            String sql;
            PreparedStatement ps;

            for (CkanPair ckanPair : obj) {
                sql = "INSERT INTO DATASET_EXTRA values (?, ?, ?)";
                ps = getConnection().prepareStatement(sql);
                ps.setString(1, ckanPair.getKey());
                ps.setString(2, ckanPair.getValue().toString());
                ps.setString(3, id);
                ps.executeUpdate();
            }
            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            desconectar();
        }
        return false;

    }  
}
