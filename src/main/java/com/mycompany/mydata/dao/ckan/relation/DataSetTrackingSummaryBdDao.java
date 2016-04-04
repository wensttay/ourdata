/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mydata.dao.ckan.relation;

import com.mycompany.mydata.dao.GenericRelationBdDao;
import eu.trentorise.opendata.jackan.model.CkanTrackingSummary;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author wensttay
 */
public class DataSetTrackingSummaryBdDao extends GenericRelationBdDao<CkanTrackingSummary, String>{

    @Override
    public boolean insert(CkanTrackingSummary obj, String id) {
                try {
            conectar();
            String sql = "INSERT INTO DATASET_TRACKING_SUMMARY values (?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setInt(1, obj.getRecent());
            ps.setInt(2, obj.getTotal());
            ps.setString(3, id);

            ps.executeUpdate();

            return true;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}
