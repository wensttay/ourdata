package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.reader.TextColor;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that know how CRUD a KeyPlace type into a JDBC
 *
 * @author Wensttay, kieckegard
 */
public class KeyPlaceBdDao extends GenericGeometricBdDao<KeyPlace, Integer> {

    /**
     * This constructor create a KeyPlaceBdDao using the default properties_path
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection.
     */
    public KeyPlaceBdDao() {
    }

    /**
     * This constructor create a KeyPlaceBdDao using the properties_path passed
     * to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public KeyPlaceBdDao(String properties_path) {
        super.setProperties_path(properties_path);
    }

    /**
     * Method to insert a KeyPlace type into a JDBC
     *
     * @param obj KeyPlace that need be save into a JDBC
     *
     * @return A boolean that means: true = inserted with sucess, false = not
     * insert with sucess or inserssion is not possible.
     */
    
    public boolean conectAndInsert(KeyPlace obj){
        
        boolean result;
        
        try {
            conectar();
            result = insert(obj);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            result = false;
        }
        
        try {
            desconectar();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        }
        
        return result;
    }
    
    @Override
    public boolean insert(KeyPlace obj) {
        PreparedStatement ps = null;
        boolean result;
        
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO resource_place(COLUM_NUMBER, COLUM_VALUE,");
            sql.append("REPEAT_NUMBER, ROWS_NUMBER, METADATA_CREATED, WAY, minX, minY, maxX, maxY, ID_PLACE, ID_RESOURCE)");
            sql.append("VALUES(?, ?, ?, ?, ?, ST_GeomFromText(?), ?, ?, ?, ?, ?, ?)");
            ps = getConnection().prepareStatement(sql.toString());

            int i = 1;
            ps.setInt(i++, obj.getColumNumber());
            ps.setString(i++, obj.getColumValue());
            ps.setInt(i++, obj.getRepeatNumber());
            ps.setInt(i++, obj.getRowsNumber());
            ps.setTimestamp(i++, obj.getMetadataCreated());
            ps.setObject(i++, obj.getPlace().getWay().toString());
            ps.setDouble(i++, obj.getPlace().getMinX());
            ps.setDouble(i++, obj.getPlace().getMinY());
            ps.setDouble(i++, obj.getPlace().getMaxX());
            ps.setDouble(i++, obj.getPlace().getMaxY());

            ps.setInt(i++, obj.getPlace().getId());
            ps.setString(i++, obj.getIdResource());

            result = (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
//            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            System.out.println(TextColor.ANSI_RED.getCode() + "Já existe no banco");
            result = false;
        }
        try {
            ps.close();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        }

        return result;
    }

    /**
     * This method return a java.util.List; with all the KeyPlace inserted in
     * JDBC
     *
     * @return A list of all KeyPlace in JDBC
     */
    @Override
    public List<KeyPlace> getAll() {
        List<KeyPlace> places = new ArrayList<>();

        try {
            conectar();
            String sql = "SELECT *, ST_AsText(way) as geo FROM resource_place";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            System.out.print("Executando Query ["+sql+"]...");
            ResultSet rs = ps.executeQuery();
            System.out.println(" Done!");   
            System.out.print("Percorrendo Cursor e preenchendo a List de Resource_places...");
            while (rs.next()) {
                KeyPlace p = preencherObjeto(rs);
                if (p != null) {
                    places.add(p);
                }
            }
            System.out.println(" Done!");

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        } finally {
            desconectar();
        }
        return places;
    }
    
    public List<KeyPlace> getBetween(int start, int end) {
        List<KeyPlace> places = new ArrayList<>();

        try {
            conectar();
            String sql = "SELECT *, ST_AsText(way) as geo FROM resource_place LIMIT ? OFFSET ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, end);
            ps.setInt(2, start);
            System.out.print("Executando Query ["+sql+"]...");
            ResultSet rs = ps.executeQuery();
            System.out.println(" Done!");   
            System.out.print("Percorrendo Cursor e preenchendo a List de Resource_places...");
            while (rs.next()) {
                KeyPlace p = preencherObjeto(rs);
                if (p != null) {
                    places.add(p);
                }
            }
            System.out.println(" Done!");

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
        } finally {
            desconectar();
        }
        return places;
    }

    public List<KeyPlace> getKeyPlacesFromResourceId(String id) {
        String sql = "SELECT * FROM Resource_Place WHERE id_resource = ?";
        List<KeyPlace> keyPlaces = new ArrayList<>();

        try {
            PreparedStatement pstm = getConnection().prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                KeyPlace kp = preencherObjeto(rs);
                keyPlaces.add(kp);
            }
            return keyPlaces;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(KeyPlaceBdDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return keyPlaces;
    }

    /**
     * This method fill a KeyPlace with a ResultSet param
     *
     * @param rs ResultSet with result of Search KeyPlace
     *
     * @return A new KeyPlace with ResulSet's Values
     */
    private KeyPlace preencherObjeto(ResultSet rs) {
        try {
            KeyPlace kw = new KeyPlace();
            kw.setColumNumber(rs.getInt("COLUM_NUMBER"));
            kw.setColumValue(rs.getString("COLUM_VALUE"));
            kw.setRepeatNumber(rs.getInt("REPEAT_NUMBER"));
            kw.setRowsNumber(rs.getInt("ROWS_NUMBER"));
            kw.setMetadataCreated(rs.getTimestamp("METADATA_CREATED"));
            kw.setIdResource(rs.getString("ID_RESOURCE"));

            Place place = new Place();
            place.setWay(new WKTReader().read(rs.getString("geo")));
            place.setId(rs.getInt("ID_PLACE"));
            place.setMaxX(rs.getDouble("maxx"));
            place.setMaxY(rs.getDouble("maxy"));
            place.setMinX(rs.getDouble("minx"));
            place.setMinY(rs.getDouble("miny"));
            kw.setPlace(place);

            return kw;
        } catch (SQLException | ParseException ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            return null;
        }
    }

    /**
     * This method insert all KeyWords passed in a java.util.List; param
     *
     * @param listKeyWords List with KeyWords to insert
     *
     * @return
     */
    public boolean insertAll(List<KeyPlace> listKeyWords) {
        long size = listKeyWords.size();
        long count = 0;
        float porcent = 0;
        float oldPorcent = 0;
        
        try {
            conectar();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            return false;
        }
        
        for (KeyPlace keyWord : listKeyWords) {
            if(insert(keyWord)){
                System.out.println("Inserido Com Sucesso");
            }
            
            count++;
            porcent = (count * 100) / size;
            
            if (porcent > oldPorcent + 10) {
                oldPorcent = porcent;
                System.out.println(porcent + " %");
            }
        }
        
        if (!listKeyWords.isEmpty()) {
            System.out.println("100.0 %");
        }
        
        try {
            desconectar();
        } catch (Exception ex) {
            System.out.println(TextColor.ANSI_RED.getCode() + ex.getMessage());
            return false;
        }
        
        return true;
    }

}
