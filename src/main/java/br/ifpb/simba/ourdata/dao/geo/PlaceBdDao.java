/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.geo;

import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.geo.Place;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.postgis.PGgeometry;

/**
 *
 * @author wensttay
 */
public class PlaceBdDao extends GenericGeometricBdDao<Place, Integer> {

    public PlaceBdDao(String properties_path) {
        super(properties_path);
    }

    @Override
    public boolean insert(Place obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Place> getAll() {
        List<Place> places = new ArrayList<>();
        
        try {
            conectar();
            String sql = "SELECT * FROM gazetteer";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Place p = preencherObjeto(rs);
                if (p != null) {
                    places.add(p);
                }
            }
            
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }finally{
            desconectar();
        }
        return places;
    }

    @Override
    public Place preencherObjeto(ResultSet rs) {
        
        try {
            Place p = new Place();
            p.setNome(rs.getString("nome"));
            p.setSigla(rs.getString("sigla"));
            p.setTipo(rs.getString("tipo"));
            p.setWay((PGgeometry) rs.getObject("the_geom"));
            return p;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

}
