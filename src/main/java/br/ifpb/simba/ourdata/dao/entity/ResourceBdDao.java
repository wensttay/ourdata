/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.dao.GenericBdDao;
import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Period;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;
import br.ifpb.simba.ourdata.entity.ResourceTimeSearch;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kieckegard
 */
public class ResourceBdDao extends GenericBdDao {

    private PreparedStatement pstm;

    public List<ResourceTimeSearch> getResourcesIntersectedBy(Period periodToSearch) {
        List<ResourceTimeSearch> resources = new ArrayList<>();
        StringBuilder sql
                = new StringBuilder("SELECT rt_search.*, r.description as Resource_Description, r.name as Resource_Name, r.url Resource_Url, dt.title as dt_title, dt.name as dt_name, "
                        + "(intervalInterTimes(Start_Date, End_Date, ?, ?) * 0.8) + (CAST((rt_search.Repeat_Number * 100) AS float) / CAST(rt_search.Resource_Rows_Number AS float)) * 0.2 as intervalTimes FROM ");
        sql.append("(SELECT min(start_date) Start_Date, max(end_date) End_Date, sum(repeat_number) Repeat_Number, max(rows_number) Resource_Rows_Number, id_resource Resource_Id ");
        sql.append("FROM RESOURCE_TIME rt ");
        sql.append("WHERE (? >= start_date AND ? <= end_date) OR (? >= start_date AND ? <= end_date) ");
        sql.append("GROUP BY (rt.id_resource)) AS rt_search JOIN RESOURCE r ON rt_search.Resource_Id = r.id JOIN DATASET dt ON r.id_dataset = dt.id ORDER BY intervalTimes DESC; ");

        try {
            conectar();
            PreparedStatement pstm = getConnection().prepareCall(sql.toString());
            int i = 1;

            Timestamp start = new Timestamp(periodToSearch.getStartDate().getDate().getTime());
            Timestamp end = new Timestamp(periodToSearch.getEndDate().getDate().getTime());

            pstm.setTimestamp(i++, start);
            pstm.setTimestamp(i++, end);

            pstm.setTimestamp(i++, start);
            pstm.setTimestamp(i++, start);

            pstm.setTimestamp(i++, end);
            pstm.setTimestamp(i++, end);

            System.out.println(pstm.toString());
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                ResourceTimeSearch rts = preencherRTS(rs);
                resources.add(rts);
            }
           
        } catch (SQLException | URISyntaxException | IOException | ClassNotFoundException ex) {
            Logger.getLogger(ResourceBdDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resources;
    }

    private ResourceTimeSearch preencherRTS(ResultSet rs) throws SQLException {
        ResourceTimeSearch resourceTimeSearch = new ResourceTimeSearch();
        String desc = rs.getString("Resource_Name");
        
        if (desc == null || desc.equals("")) {
            desc = rs.getString("Resource_Description");
        }
        if (desc == null || desc.equals("")) {
            desc = rs.getString("Resource_Description");
        }
        if (desc == null || desc.equals("")) {
            desc = rs.getString("dt_title");
        }
        if (desc == null || desc.equals("")) {
            desc = rs.getString("dt_name");
        }
        
        resourceTimeSearch.setDescription(desc);
        resourceTimeSearch.setStartDate(rs.getTimestamp("Start_Date"));
        resourceTimeSearch.setEndDate(rs.getTimestamp("End_Date"));
        resourceTimeSearch.setRepeatNumber(rs.getLong("Repeat_Number"));
        resourceTimeSearch.setResourceRowsNumber(rs.getLong("Resource_Rows_Number"));
        resourceTimeSearch.setResourceId(rs.getString("Resource_Id"));
        resourceTimeSearch.setResourceUrl(rs.getString("Resource_Url"));
        resourceTimeSearch.setIntervelTimes(rs.getFloat("intervalTimes"));

        return resourceTimeSearch;
    }

    public List<Resource> getResourcesIntersectedBy(Place placeToSearch) {

        Date start;

        List<Resource> resources = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT r.id, r.description, r.name, r.format, r.url, r.id_dataset, ");
        sql.append("rp.repeat_number, rp.rows_number, rp.colum_value, ");
        sql.append("rp.metadata_Created, rp.minX, rp.minY, rp.maxX, rp.maxY, ST_AsEWKT(rp.way) way, ");
        sql.append("d.title dataset_title ");
        sql.append("FROM Resource r JOIN Resource_Place rp ON r.id = rp.id_resource ");
        sql.append("JOIN dataset d ON r.id_dataset = d.id ");
        sql.append("WHERE ST_Intersects(rp.way, ?) ORDER BY id ");

        try {
            conectar();
            WKTWriter writer = new WKTWriter();

            PreparedStatement pstm = getConnection().prepareCall(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, writer.write(placeToSearch.getWay()));
            System.out.println(pstm.toString());

            System.out.println("Executando consulta...");
            start = new Date(System.currentTimeMillis());

            ResultSet rs = pstm.executeQuery();
            System.out.println("Duração em ms: " + (System.currentTimeMillis() - start.getTime()));
            Resource r;

            System.out.println("Preenchendo Objetos com  consulta...");
            start = new Date(System.currentTimeMillis());

            while (rs.next()) {
                r = formaResource(rs);
                resources.add(r);
            }

            System.out.println("Duração em ms: " + (System.currentTimeMillis() - start.getTime()));

            return resources;
        } catch (URISyntaxException | IOException | SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(KeyPlaceBdDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resources;
    }

    private Resource formaResource(ResultSet rs) throws SQLException, ParseException {
        String id_resource = rs.getString("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        if (description.equals("")) {
            description = rs.getString("dataset_title");
        }
        String format = rs.getString("format");
        String url = rs.getString("url");
        String idDataset = rs.getString("id_Dataset");
        List<KeyPlace> keyplaces = new ArrayList<>();

        double maxx, maxy, minx, miny;

        maxx = rs.getDouble("maxx");
        maxy = rs.getDouble("maxy");
        minx = rs.getDouble("minx");
        miny = rs.getDouble("miny");

        do {
            if (rs.getDouble("maxx") > maxx) {
                maxx = rs.getDouble("maxx");
            }
            if (rs.getDouble("maxy") > maxy) {
                maxy = rs.getDouble("maxy");
            }
            if (rs.getDouble("minx") < minx) {
                minx = rs.getDouble("minx");
            }
            if (rs.getDouble("miny") < miny) {
                miny = rs.getDouble("miny");
            }

            Place place = new Place();
            KeyPlace keyPlace = new KeyPlace();
            WKTReader reader = new WKTReader();

            place.setMaxX(rs.getDouble("maxx"));
            place.setMaxY(rs.getDouble("maxy"));
            place.setMinX(rs.getDouble("minx"));
            place.setMinY(rs.getDouble("miny"));
            place.setWay(reader.read(rs.getString("way")));

            keyPlace.setIdResource(id_resource);
            keyPlace.setPlace(place);
            keyPlace.setColumValue(rs.getString("colum_value"));
            keyPlace.setRowsNumber(rs.getInt("rows_Number"));
            keyPlace.setMetadataCreated(rs.getTimestamp("metaData_created"));
            keyPlace.setRepeatNumber(rs.getInt("repeat_number"));
            keyplaces.add(keyPlace);

        } while (rs.next() && id_resource.equals(rs.getString("id")));

        rs.previous();
        Resource r = new Resource(id_resource, name, description, format, url, idDataset);
        Place p = new Place();
        p.setMaxX(maxx);
        p.setMaxY(maxy);
        p.setMinX(minx);
        p.setMinY(miny);
//        p.setWay(new WKTReader().read("POLYGON((" +minx +" "+ miny +", " + maxx +" " + maxy + "))"));
        r.setPlace(p);

        for (KeyPlace kp : keyplaces) {
            r.addKeyPlace(kp);
        }

        return r;
    }

}
