/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.geo;

import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.geo.KeyWord;
import br.ifpb.simba.ourdata.geo.Place;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
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
 * @author kieckegard
 */
public class KeyWordBdDao extends GenericGeometricBdDao<KeyWord, Integer> {

    public KeyWordBdDao(String properties_path) {
        super(properties_path);
    }

    @Override
    public boolean insert(KeyWord obj) {
        try {
            conectar();
            String sql = "INSERT INTO resource_place(COLUM_NUMBER, COLUM_VALUE, REPEAT_NUMBER,"
                    + "ROWS_NUMBER, METADATA_CREATED, WAY, ID_PLACE, ID_RESOURCE) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            
            int i = 1;
            ps.setInt(i++, obj.getColumNumber());
            ps.setString(i++, obj.getColumValue());
            ps.setInt(i++, obj.getRepeatNumber());
            ps.setInt(i++, obj.getRowsNumber());
            ps.setTimestamp(i++, obj.getMetadataCreated());
            
            PGgeometry pGgeometry = new PGgeometry(obj.getPlace().getWay().toString());
            ps.setObject(i++, pGgeometry);
            
            ps.setInt(i++, obj.getPlace().getId());
            ps.setString(i++, obj.getIdResource());

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }

    @Override
    public List<KeyWord> getAll() {
        List<KeyWord> places = new ArrayList<>();

        try {
            conectar();
            String sql = "SELECT *, ST_AsBinary(way) as geo FROM resource_place";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                KeyWord p = preencherObjeto(rs);
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
    public KeyWord preencherObjeto(ResultSet rs) {
        try {
            Place place = new Place();
            KeyWord kw = new KeyWord();
            kw.setColumNumber(rs.getInt("COLUM_NUMBER"));
            kw.setColumValue(rs.getString("COLUM_VALUE"));
            kw.setRepeatNumber(rs.getInt("REPEAT_NUMBER"));
            kw.setRowsNumber(rs.getInt("ROWS_NUMBER"));
            kw.setMetadataCreated(rs.getTimestamp("METADATA_CREATED"));
            
            WKBReader wkbReader = new WKBReader();
            Geometry geo = wkbReader.read(rs.getBytes("geo"));
            place.setWay(geo);
            
            place.setWay((Geometry) rs.getObject("WAY"));
            place.setId(rs.getInt("ID_PLACE"));
            kw.setIdResource(rs.getString("ID_RESOURCE"));
            kw.setPlace(place);

            return kw;
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean insertAll(List<KeyWord> listKeyWords) {
        try {
            conectar();
            for (KeyWord keyWord : listKeyWords) {
                insert(keyWord);
            }
            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            desconectar();
        }
    }
    
//    public boolean insertAll(List<KeyWord> liteVersion) {
//        try {
//            if (liteVersion.isEmpty()) {
//                return false;
//            }
//            conectar();
//            StringBuilder sql = new StringBuilder("INSERT INTO resource_place(COLUM_NAME, COLUM_VALUE, REPEAT_NUMBER, ROWS_NUMBER, WAY, METADATA_CREATED, ID_RESOURCE) VALUES");
//            for (int i = 0; i < liteVersion.size(); i++) {
//                if (i == liteVersion.size() - 1) {
//                    sql.append("(?, ?, ?, ?, ?, ?, ?)");
//                }else{
//                    sql.append("(?, ?, ?, ?, ?, ?, ?), ");
//                }
//            }
//            PreparedStatement ps = getConnection().prepareStatement(sql.toString());
//            int i = 1;
//            for (KeyWord obj : liteVersion) {
//                ps.setString(i++, obj.getColumName());
//                ps.setString(i++, obj.getColumValue());
//                ps.setInt(i++, obj.getRepeatNumber());
//                ps.setInt(i++, obj.getRowsNumber());
//                ps.setObject(i++, obj.getPlace().getWay());
//                ps.setTimestamp(i++, obj.getMetadataCreated());
//                ps.setString(i++, obj.getIdResource());
//            }
////            System.out.println(ps.toString());
//            return (ps.executeUpdate() != 0);
//        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
//            ex.printStackTrace();
//            return false;
//        } finally {
//            desconectar();
//        }
//
//    }
}
