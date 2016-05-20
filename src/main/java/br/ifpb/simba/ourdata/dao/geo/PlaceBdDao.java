/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.geo;

import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.geo.Place;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.postgis.Polygon;

/**
 *
 * @author wensttay
 */
public class PlaceBdDao extends GenericGeometricBdDao<Place, String> {
    
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
            String sql = "SELECT *, ST_AsBinary(way) as geo FROM place";
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
        } finally {
            desconectar();
        }
        return places;
    }
    
    @Override
    public Place preencherObjeto(ResultSet rs) {

        try {
            Place p = new Place();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setSigla(rs.getString("sigla"));
            p.setTipo(rs.getString("tipo"));
            WKBReader wkbReader = new WKBReader();
            Geometry geo = wkbReader.read(rs.getBytes("geo"));
            p.setWay(geo);
            return p;
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List<Place> burcarPorTitulos(String columValue) {
        List<Place> places = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT *, ST_AsBinary(way) as geo FROM place WHERE nome ILIKE ? OR sigla ILIKE ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, columValue);
            ps.setString(2, columValue);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Place p = preencherObjeto(rs);
                if(p != null){
                    places.add(p);
                }
            }
            return places;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        } finally {
            desconectar();
        }
    }
    
//    public List<CkanResource> buscarPorBbox(Polygon polygon){     
//       List<CkanResource> resources = new ArrayList<>();
//       try{
//           conectar();
//           String sql = "SELECT descricao, url, format FROM resource r JOIN resource_places rp ON r.id = rp.id_resource"
//                   + "WHERE ST_Intersects(rp.way,?)";
//           PreparedStatement pstm = getConnection().prepareStatement(sql);
//           pstm.setObject(1, polygon);
//           ResultSet rs = pstm.executeQuery();
//           
//           CkanResource resource = new CkanResource();
//           while(rs.next()){
//               String descricao = rs.getString("descricao");
//               String url = rs.getString("url");
//               String format = rs.getString("format");
//               resource.setDescription(descricao);
//               resource.setUrl(url);
//               resource.setFormat(format);
//               resources.add(resource);
//           }
//           return resources;
//       }
//       catch(URISyntaxException | IOException | SQLException | ClassNotFoundException ex){
//           ex.printStackTrace();        
//           return resources;
//       }finally{
//           desconectar();
//       }
//    }

}
