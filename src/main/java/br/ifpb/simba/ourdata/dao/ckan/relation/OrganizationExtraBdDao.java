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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wensttay
 */
public class OrganizationExtraBdDao extends GenericRelationBdDao<CkanPair, String> {

    @Override
    public boolean insert(CkanPair obj, String id) {
        try {
            conectar();
            String sql = "INSERT INTO ORGANIZATION_EXTRA VALUES(?,?,?)";
            PreparedStatement pstm = getConnection().prepareCall(sql);
            pstm.setString(1, obj.getKey());
            pstm.setString(2, obj.getValue());
            pstm.setString(3, id);
            
            return (pstm.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
