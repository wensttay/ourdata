
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
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
 * Class that know how CRUD a PlaceBdDao type into a JDBC
 *
 * @author Wensttay, kieckegard
 */
public class PlaceBdDao extends GenericGeometricBdDao<Place, String>
{
    /**
     * This constructor create a KeyPlaceBdDao using the default
     * properties_path
     * 'PROPERTIES_PATH_DEFAULT' to JDBC connection
     */
    public PlaceBdDao()
    {
    }

    /**
     * This constructor create a PlaceBdDao using the properties_path
     * passed to JDBC connection
     *
     * @param properties_path The path will be used to JDBC connection
     */
    public PlaceBdDao(String properties_path)
    {
        super.setProperties_path(properties_path);
    }
    
    /**
     * Method to insert a Place type into a JDBC
     *
     * @param obj Place that need be save into a JDBC
     *
     * @return A boolean that means: true = inserted with sucess, false = not
     * insert with sucess or inserssion is not possible.
     */
    @Override
    public boolean insert(Place obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method return a java.util.List; with all the Place inserted
     * in JDBC
     *
     * @return A list of all Place in JDBC
     */
    @Override
    public List<Place> getAll()
    {
        try
        {
            conectar();
            String sql = "SELECT *, ST_AsText(way) as geo FROM place";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Place> places = new ArrayList<>();
            while (rs.next())
            {
                Place p = preencherObjeto(rs);
                if (p != null)
                {
                    places.add(p);
                    System.out.println(p.toString());
                }
            }

        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            desconectar();
        }
        return new ArrayList<>();
    }

    /**
     * Method to search Places with the atribute 'name' or 'sigla' equals to the
     * param value into JDBC
     *
     * @param titulo String type that can be a name or a sigla of some Place
     * @param tipo String that represents the type of place. Could be state, city, microregion, etc.
     *
     * @return A list with all Places with name or sigla equals(titulo)
     */
    public List<Place> burcarPorTitulos(String titulo, String tipo)
    {
        try
        {
            conectar();
            StringBuilder sql = new StringBuilder("SELECT *, ST_AsText(way) as geo FROM place WHERE (nome");
            sql.append(" ILIKE ? OR sigla ILIKE ?) AND (tipo ILIKE ?)");
            PreparedStatement ps = getConnection().prepareStatement(sql.toString());
            int i = 1;
            ps.setString(i++, titulo);
            ps.setString(i++, titulo);
            ps.setString(i++, tipo);
            
            ResultSet rs = ps.executeQuery();
            List<Place> places = new ArrayList<>();
            while (rs.next())
            {
                Place p = preencherObjeto(rs);
                if (p != null)
                {
                    places.add(p);
                }
            }
            
            return places;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            desconectar();
        }

        return new ArrayList<>();
    }
    
    /**
     * Method to search Places with the atribute 'name' or 'sigla' equals to the
     * param value into JDBC
     *
     * @param titulo String type that can be a name or a sigla of some Place
     *
     * @return A list with all Places with name or sigla equals(titulo)
     */
    public List<Place> burcarPorTitulos(String titulo)
    {
        try
        {
            conectar();
            StringBuilder sql = new StringBuilder("SELECT *, ST_AsText(way) as geo FROM place WHERE nome");
            sql.append(" ILIKE ? OR sigla ILIKE ?");
            PreparedStatement ps = getConnection().prepareStatement(sql.toString());
            int i = 1;
            ps.setString(i++, titulo);
            ps.setString(i++, titulo);
            
            ResultSet rs = ps.executeQuery();
            List<Place> places = new ArrayList<>();
            while (rs.next())
            {
                Place p = preencherObjeto(rs);
                if (p != null)
                {
                    places.add(p);
                }
            }
            
            return places;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            desconectar();
        }

        return new ArrayList<>();
    }
    
    /**
     * This method fill a Place with a ResultSet param
     * 
     * @param rs ResultSet with result of Search Place
     * @return A new Place with ResulSet's Values
     */
    private Place preencherObjeto(ResultSet rs)
    {
        try
        {
            Place p = new Place();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setSigla(rs.getString("sigla"));
            p.setTipo(rs.getString("tipo"));
            System.out.println("\n\nGEOMETRIA CARAI : "+rs.getString("geo")+" \n\n");
            p.setWay(new WKTReader().read(rs.getString("geo")));
            p.setMaxX(rs.getDouble("maxx"));
            p.setMaxY(rs.getDouble("maxy"));
            p.setMinX(rs.getDouble("minx"));
            p.setMinY(rs.getDouble("miny"));
            return p;
        } catch (SQLException | ParseException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
