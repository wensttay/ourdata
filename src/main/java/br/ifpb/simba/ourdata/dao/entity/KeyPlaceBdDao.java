
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
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

/**
 * Class that know how CRUD a KeyPlace type into a JDBC
 *
 * @author Wensttay, Pedro Arthur
 */
public class KeyPlaceBdDao extends GenericGeometricBdDao<KeyPlace, Integer>
{
    /**
     * This constructor create a KeyPlaceBdDao using the default
     * properties_path
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection.
     */
    public KeyPlaceBdDao()
    {
    }

    /**
     * This constructor create a KeyPlaceBdDao using the properties_path
     * passed to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public KeyPlaceBdDao(String properties_path)
    {
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
    @Override
    public boolean insert(KeyPlace obj)
    {
        try
        {
            conectar();
            StringBuilder sql = new StringBuilder("INSERT INTO resource_place(COLUM_NUMBER, COLUM_VALUE,");
            sql.append("REPEAT_NUMBER, ROWS_NUMBER, METADATA_CREATED, WAY, minX, minY, maxX, maxY, ID_PLACE, ID_RESOURCE)");
            sql.append("VALUES(?, ?, ?, ?, ?, ST_GeomFromText(?), ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps = getConnection().prepareStatement(sql.toString());
            
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

            return (ps.executeUpdate() != 0);
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return false;
        } finally
        {
            desconectar();
        }
    }

    /**
     * This method return a java.util.List; with all the KeyPlace inserted
     * in JDBC
     *
     * @return A list of all KeyPlace in JDBC
     */
    @Override
    public List<KeyPlace> getAll()
    {
        List<KeyPlace> places = new ArrayList<>();

        try
        {
            conectar();
            String sql = "SELECT *, ST_AsText(way) as geo FROM resource_place";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                KeyPlace p = preencherObjeto(rs);
                if (p != null)
                {
                    places.add(p);
                }
            }

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            desconectar();
        }
        return places;

    }
    
    /**
     * This method fill a KeyPlace with a ResultSet param
     * 
     * @param rs ResultSet with result of Search KeyPlace
     * @return A new KeyPlace with ResulSet's Values
     */
    private KeyPlace preencherObjeto(ResultSet rs)
    {
        try
        {
            KeyPlace kw = new KeyPlace();
            kw.setColumNumber(rs.getInt("COLUM_NUMBER"));
            kw.setColumValue(rs.getString("COLUM_VALUE"));
            kw.setRepeatNumber(rs.getInt("REPEAT_NUMBER"));
            kw.setRowsNumber(rs.getInt("ROWS_NUMBER"));
            kw.setMetadataCreated(rs.getTimestamp("METADATA_CREATED"));
            kw.setIdResource(rs.getString("ID_RESOURCE"));

            Place place = new Place();
            place.setWay(new WKTReader().read(rs.getString("geo")));
            place.setWay((Geometry) rs.getObject("WAY"));
            place.setId(rs.getInt("ID_PLACE"));
            place.setMaxX(rs.getDouble("maxx"));
            place.setMaxY(rs.getDouble("maxy"));
            place.setMinX(rs.getDouble("minx"));
            place.setMinY(rs.getDouble("miny"));
            kw.setPlace(place);

            return kw;
        } catch (SQLException | ParseException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * This method insert all KeyWords passed in a java.util.List; param
     * 
     * @param listKeyWords List with KeyWords to insert
     * @return 
     */
    public boolean insertAll(List<KeyPlace> listKeyWords)
    {
        try
        {
            conectar();
            for (KeyPlace keyWord : listKeyWords)
            {
                insert(keyWord);
            }
            return true;

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return false;
        } finally
        {
            desconectar();
        }
    }
}
