/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.dao.GenericBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTWriter;
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
 *
 * @author kieckegard
 */
public class ResourceBdDao extends GenericBdDao
{
    private PreparedStatement pstm;
    
    public List<Resource> getResourcesIntersectedBy(){
        List<Resource> resources = new ArrayList<>();
        
        String sql1 = "SELECT r.id, r.description, r.format, r.url, r.id_dataset, rp.repeat_number, rp.rows_number, rp.colum_value, rp.colum_name,\n" +
            "rp.metadata_Created, rp.minX, rp.minY, rp.maxX, rp.maxY\n" +
            "FROM Resource r JOIN Resource_Place rp ON r.id = rp.id_resource, (SELECT way FROM resource_place Where colum_value = 'Cajazeiras') c\n" +
            "WHERE ST_Intersects(rp.way, c.way);";
        
        String sql = "SELECT r.id, r.description, r.format, r.url, r.id_dataset, rp.repeat_number, rp.rows_number, rp.colum_number, rp.colum_name,\n" +
            "rp.metadataCreated, rp.id id_place, rp.nome, rp.sigla, rp.tipo, rp.minX, rp.minY, rp.maxX, rp.maxY\n" +
            "FROM Resource r JOIN Resource_Place rp ON r.id = rp.id_resource\n" +
            "WHERE ST_Intersects(rp.way, ?);";
        try
        {
            conectar();
            WKTWriter writer = new WKTWriter();
            PreparedStatement pstm = getConnection().prepareCall(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //pstm.setString(1,writer.write(g));
            
            ResultSet rs = pstm.executeQuery();
                
            Resource r;
            
            while(rs.next()){
                r = formaResource(rs);
                resources.add(r);
            }
            return resources;
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex){
            Logger.getLogger(KeyPlaceBdDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resources;
    }
    
    private Resource formaResource(ResultSet rs) throws SQLException{
        String id_resource = rs.getString("id");
        String description = rs.getString("description");
        String format = rs.getString("format");
        String url = rs.getString("url");
        String idDataset = rs.getString("id_Dataset");
        List<KeyPlace> keyplaces = new ArrayList<>();
        Place place = new Place();
        KeyPlace keyPlace = new KeyPlace();
        while(id_resource.equals(rs.getString("id"))){
            place.setMaxX(rs.getDouble("maxx"));
            place.setMaxY(rs.getDouble("maxy"));
            place.setMinX(rs.getDouble("minx"));
            place.setMinY(rs.getDouble("miny"));
            
            keyPlace.setIdResource(id_resource);
            keyPlace.setPlace(place);
            keyPlace.setColumValue(rs.getString("colum_value"));
            keyPlace.setRowsNumber(rs.getInt("rows_Number"));
            keyPlace.setMetadataCreated(rs.getTimestamp("metaData_created"));
            keyPlace.setRepeatNumber(rs.getInt("repeat_number"));
            keyplaces.add(keyPlace);
            rs.next();
        }
        rs.previous();
        Resource r = new Resource(id_resource,description,format,url,idDataset);
        for(KeyPlace kp : keyplaces)
            r.addKeyPlace(kp);
        return r;
    }

    public List<Resource> getAll() {
        try
        {
            conectar();
            List<Resource> resources = new ArrayList<>();
            String sql = "SELECT r.id, r.description, r.format, r.url, r.id_dataset, rp.repeat_number, rp.rows_number, rp.colum_number, rp.colum_name,\n" +
                    "rp.metadataCreated, rp.id id_place, rp.nome, rp.sigla, rp.tipo, rp.minX, rp.minY, rp.maxX, rp.maxY\n" +
                    "FROM Resource r JOIN Resource_Place rp ON r.id = rp.id_resource\n";
            try
            {
                PreparedStatement pstm = getConnection().prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                
                Resource r;
                
                while(rs.next()){
                    
                    r = formaResource(rs);
                    resources.add(r);
                    
                }
                return resources;
            }
            catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(KeyPlaceBdDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            return resources;
            
        }
        catch (URISyntaxException | IOException | SQLException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
        
    
}
