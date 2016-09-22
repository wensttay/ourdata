/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.KeyPlaceDao;
import br.ifpb.simba.ourdata.entity.Place;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.vividsolutions.jts.geom.Geometry;
import java.util.List;
import static com.stratio.cassandra.lucene.builder.Builder.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.util.Date;
import java.sql.Timestamp;
import java.util.LinkedList;

/**
 *
 * @author kieckegard
 */
public class KeyPlaceDaoCassandraDbImpl implements KeyPlaceDao {

    private Cluster cluster;
    private Session session;

    public KeyPlaceDaoCassandraDbImpl() {
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("ourdata");
    }

    @Override
    public void insert(KeyPlace kp) {
        String sql = "INSERT INTO resource_place (id,colum_number,colum_value,repeat_number,rows_number,metadata_created,way,"
                + "id_place,id_resource,minx,maxx,miny,maxy) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement pstm = session.prepare(sql);

        BoundStatement bind = pstm.bind(kp.getId(), kp.getColumNumber(),
                kp.getColumValue(), kp.getRepeatNumber(), kp.getRowsNumber(),
                kp.getMetadataCreated(), kp.getPlace().getWay().toString(),
                kp.getPlace().getId(), kp.getIdResource(), kp.getPlace().getMinX(),
                kp.getPlace().getMaxX(), kp.getPlace().getMinY(), kp.getPlace().getMaxY());

        session.execute(bind);
    }

    public List<KeyPlace> getIntersectedBy(Geometry geometry) {

        String wkt = geometry.toString();

        List<KeyPlace> keyPlaces = new LinkedList<>();

        long start = System.currentTimeMillis();
        ResultSet rs = session.execute("SELECT * FROM resource_place WHERE expr(resource_place_idx, ?)",
                search().filter(
                        geoShape("way", wkt(wkt)).operation("intersects")
                ).build());
        long end = System.currentTimeMillis();
        System.out.println("!!!!!!! CASSANDRA + LUCENE !!!!!!!");
        System.out.println("Executou a Busca e criou o objeto ResultSet. Tempo: "+(end-start)+"ms.");
        
        start = System.currentTimeMillis();
        for (Row r : rs.all()) {

            try {
                keyPlaces.add(formaKeyPlace(r));
            }
            catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        end = System.currentTimeMillis();
        
        System.out.println("Montou todos os resource_places e os adicionou em uma lista. Tempo: "+(end-start)+"ms.");
        System.out.println("Quantidade de Resource_Places encontrados: "+keyPlaces.size());
        System.out.println("!!!!!!! FIM CASSANRA + LUCENE !!!!!!!");

        

        return keyPlaces;
    }

    public KeyPlace formaKeyPlace(Row r) throws ParseException {

        WKTReader reader = new WKTReader();

        int id = r.getInt("id");
        int columNumber = r.getInt("colum_number");
        String columValue = r.getString("colum_value");
        int repeat_number = r.getInt("repeat_number");
        int rows_number = r.getInt("rows_number");
        Date metadataCreated = r.getTimestamp("metadata_created");
        Timestamp metadata_created = new java.sql.Timestamp(metadataCreated.getTime());
        Geometry geo = reader.read(r.getString("way"));
        int id_place = r.getInt("id_place");
        String id_resource = r.getString("id_resource");
        double minx = r.getDouble("minx");
        double maxx = r.getDouble("maxx");
        double miny = r.getDouble("miny");
        double maxy = r.getDouble("maxy");

        KeyPlace keyPlace = new KeyPlace();
        keyPlace.setId(id);
        keyPlace.setColumNumber(columNumber);
        keyPlace.setColumValue(columValue);
        keyPlace.setRepeatNumber(repeat_number);
        keyPlace.setRowsNumber(rows_number);
        keyPlace.setMetadataCreated(metadata_created);

        Place place = new Place();
        place.setId(id_place);
        place.setWay(geo);
        place.setMinX(minx);
        place.setMaxX(maxx);
        place.setMinY(miny);
        place.setMaxY(maxy);

        keyPlace.setPlace(place);
        
        return keyPlace;
    }

}
