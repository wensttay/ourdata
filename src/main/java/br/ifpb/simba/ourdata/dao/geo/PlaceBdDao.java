/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.geo;

import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.geo.Place;
import eu.trentorise.opendata.jackan.model.CkanResource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgis.PGgeometry;
import org.postgis.PGboxbase;
import org.postgis.Polygon;

/**
 *
 * @author wensttay
 */
public class PlaceBdDao extends GenericGeometricBdDao<Place, String>
{

    public PlaceBdDao(String properties_path)
    {
        super(properties_path);
    }

    @Override
    public boolean insert(Place obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Place> getAll()
    {
        List<Place> places = new ArrayList<>();

        try
        {
            conectar();
            String sql = "SELECT * FROM gazetteer";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Place p = preencherObjeto(rs);
                if (p != null)
                {
                    places.add(p);
                }
            }

        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            desconectar();
        }
        return places;
    }

    @Override
    public Place preencherObjeto(ResultSet rs)
    {

        try
        {
            Place p = new Place();
            p.setId(rs.getInt("gid"));
            p.setNome(rs.getString("nome"));
            p.setSigla(rs.getString("sigla"));
            p.setTipo(rs.getString("tipo"));
            p.setWay((PGgeometry) rs.getObject("the_geom"));
            return p;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return null;
        }

    }

    public Place burcarPorTitulos(String columValue)
    {
        try
        {
            conectar();
            String sql = "SELECT *, st_area(the_geom) as size FROM gazetteer WHERE nome ILIKE ? OR sigla ILIKE ? ORDER BY size DESC LIMIT 1";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setString(1, columValue);
            ps.setString(2, columValue);
            ResultSet rs = ps.executeQuery();

            Place p = null;
            if (rs.next())
            {
                p = preencherObjeto(rs);
            }

            return p;
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            desconectar();
        }
        return null;
    }

    public List<CkanResource> buscarPorBbox(Polygon polygon)
    {
        List<CkanResource> resources = new ArrayList<>();
        try
        {
            conectar();
            String sql = "SELECT descricao, url, format FROM resource r JOIN resource_places rp ON r.id = rp.id_resource"
                    + "WHERE ST_Intersects(rp.way,?)";
            PreparedStatement pstm = getConnection().prepareStatement(sql);
            pstm.setObject(1, polygon);
            ResultSet rs = pstm.executeQuery();

            CkanResource resource = new CkanResource();
            while (rs.next())
            {
                String descricao = rs.getString("descricao");
                String url = rs.getString("url");
                String format = rs.getString("format");
                resource.setDescription(descricao);
                resource.setUrl(url);
                resource.setFormat(format);
                resources.add(resource);
            }
            return resources;
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return resources;
        }
        finally
        {
            desconectar();
        }
    }

    public List<CkanResource> getResourcesByPlace(String place)
    {
        PlaceBdDao dao = new PlaceBdDao("/banco/banco.gazetteer.properties");
        Place p = dao.burcarPorTitulos(place);
        List<CkanResource> resources = new ArrayList<>();
        if (p == null)
        {
            return resources;
        }
        String sql = "SELECT * FROM (SELECT DISTINCT ON (r.url) r.id_dataset, r.id, r.description, r.format, r.url, compare(?,rp.way) rank "
                + "FROM Resource r JOIN Resource_Place rp ON r.id = rp.id_resource "
                + "ORDER BY r.url, rank DESC) a ORDER BY a.rank DESC LIMIT 20";
        
        String sql2 = "SELECT DISTINCT ON (inter.id_resource) inter.id_resource, inter.description, inter.format, inter.url, getEnvelopeIntersect(inter.way,inter.id_resource) bbox \n" +
                    "FROM (SELECT rp.id,rp.way,rp.id_resource,r.description, r.format, r.url FROM resource_place rp JOIN Resource r ON r.id = rp.id_resource\n" +
                    "WHERE ST_Intersects(rp.way, ?) ORDER BY rp.id_resource) \n" +
                    "inter ORDER BY inter.id_resource";

        try
        {
            conectar();
            PreparedStatement ps = getConnection().prepareStatement(sql2);
            ps.setObject(1, p.getWay());

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                String id = rs.getString("id_resource");
                String desc = rs.getString("description");
                String format = rs.getString("format");
                String url = rs.getString("url");
                //double rank = rs.getDouble("rank");
                //String id_dataset = rs.getString("id_dataset");
                //System.out.println("id_dataset = "+id_dataset);
                System.out.println("id = "+id);
                System.out.println("description = "+desc);
                System.out.println("url = "+url);
                System.out.println("format = "+format);
                //System.out.println("rank = "+rank*100+"%");
                System.out.println("============================");
                CkanResource resource = new CkanResource();
                resource.setId(id);
                resource.setDescription(desc);
                resource.setFormat(format);
                resource.setUrl(url);
                resources.add(resource);
            }
            return resources;
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            desconectar();
        }
        return resources;
    }

    public boolean stContains(Place bigPlace, Place smallPlace)
    {
        try
        {
            conectar();
            String sql = "SELECT nome, ST_Contains((SELECT the_geom FROM gazetteer WHERE gid = ?), the_geom) as contains FROM gazetteer WHERE gid = ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, bigPlace.getId());
            ps.setInt(2, smallPlace.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getBoolean("contains");
            }

        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            desconectar();
        }
        return false;
    }

}
