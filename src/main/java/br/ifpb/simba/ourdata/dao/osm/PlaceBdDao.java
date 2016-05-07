/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.osm;

import br.ifpb.simba.ourdata.dao.GenericGeometricBdDao;
import br.ifpb.simba.ourdata.osm.Place;
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

/**
 *
 * @author kieckegard
 */
public class PlaceBdDao extends GenericGeometricBdDao<Place, Integer>
{

    public PlaceBdDao(String properties_path) {
        super(properties_path);
    }

    @Override
    public boolean insert(Place obj)
    {
        try
        {
            conectar();
            String sql = "INSERT INTO places(name,amenity,way) VALUES(?,?,?)";
            PreparedStatement pstm = getConnection().prepareCall(sql);
            pstm.setString(1, obj.getName());
            pstm.setString(2, obj.getAmenity());
            pstm.setObject(3, new PGgeometry(obj.getGeometry()));
            
            return (pstm.executeUpdate() != 0);
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return false;
        }
        finally{
            super.desconectar();
        }
    }

    public Place get(Integer obj)
    {    
        try
        {
            String sql = "SELECT * FROM places WHERE id=?";
            PreparedStatement pstm = getConnection().prepareCall(sql);
            pstm.setInt(1, obj);
            ResultSet rs = pstm.executeQuery();
            String name,amenity;
            PGgeometry geo;
            int id;
            if(rs.next()){
                id = rs.getInt("id");
                name = rs.getString("name");
                amenity = rs.getString("amenity");
                geo = (PGgeometry) rs.getObject("way");
                Place p = new Place(id,name,amenity,geo.getGeometry());
                return p;
            }
            return null;
            
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(PlaceBdDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Place> getAll()
    {
        String sql = "SELECT * FROM places";
        try
        {
            PreparedStatement pstm = getConnection().prepareCall(sql);
            List<Place> places = new ArrayList<>();
            getConnection().setAutoCommit(false);
            pstm.setFetchSize(100);
            ResultSet rs = pstm.executeQuery();
            String name,amenity;
            PGgeometry geo;
            int id;
            while(rs.next()){
                id = rs.getInt("id");
                name = rs.getString("name");
                amenity = rs.getString("amenity");
                geo = (PGgeometry) rs.getObject("way");
                Place p = new Place(id,name,amenity,geo.getGeometry());
                places.add(p);
            }
            return places;
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }   
    }
    
    public List<Place> getAllPlacesFromOSMDb()throws SQLException, URISyntaxException, IOException, ClassNotFoundException{
       
        List<Place> all = new ArrayList<>();
        for(Place a1 : getAllPlacesFromOSMTable("planet_osm_polygon"))
            all.add(a1);
        for(Place a2 : getAllPlacesFromOSMTable("planet_osm_point"))
            all.add(a2);
        for(Place a3 : getAllPlacesFromOSMTable("planet_osm_line"))
            all.add(a3);
        for(Place a4 : getAllPlacesFromOSMTable("planet_osm_roads"))
            all.add(a4);
        return all;
    }
    
    public List<Place> getAllPlacesFromOSMTable(String osm_table)throws SQLException, URISyntaxException, IOException, ClassNotFoundException{
        super.conectar();
         
        String sql = "SELECT name,amenity,way FROM "+osm_table+" WHERE name != '' AND amenity != '';";
        System.out.println("Doing... "+sql);
        PreparedStatement pstm = getConnection().prepareCall(sql);
        getConnection().setAutoCommit(false);
        pstm.setFetchSize(100);
        System.out.println("Call prepared, Calling...");
        ResultSet rs = pstm.executeQuery();
        System.out.println("Done, copying the results.");
        List<Place> resultado = new ArrayList<>();
        String name,amenity;
        while(rs.next()){
            name = rs.getString("name");
            System.out.println("name: "+name);
            amenity = rs.getString("amenity");
            System.out.println("geo: "+rs.getObject("way"));
            PGgeometry pgeo = (PGgeometry)rs.getObject("way");
            Place a = new Place(name,amenity,pgeo.getGeometry());
            System.out.println("name: "+name+" | amenity: "+amenity+" | geo: "+pgeo);
            System.out.println(pgeo.getGeometry().getDimension());
                   
            resultado.add(a);
        }
        rs.close();
        pstm.setFetchSize(0);
        pstm.close();
        return resultado;
    }

    @Override
    public boolean update(Place obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertOrUpdate(Place obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertOrUpdateAtributes(Place obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean exist(Integer id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
