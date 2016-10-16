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

    private final Cluster cluster;
    private final Session session;
    private PreparedStatement pstm;

    public KeyPlaceDaoCassandraDbImpl() {
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect("ourdata");
    }

    /**
     * Inserts a new KeyPlace into resource_place table in OurData keyspace
     * @param keyPlace 
     */
    @Override
    public void insert(KeyPlace keyPlace) {
        String sql = "INSERT INTO resource_place (id,colum_number,colum_value,repeat_number,rows_number,metadata_created,way,"
                + "id_place,id_resource,minx,maxx,miny,maxy) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                
        //Only this method uses statement, So...
        //Doesn't need to re-prepare the Statement if the same query was already prepared.
        if (this.pstm == null)
            this.pstm = session.prepare(sql);

        BoundStatement bind = pstm.bind(keyPlace.getId(), keyPlace.getColumNumber(),
                keyPlace.getColumValue(), keyPlace.getRepeatNumber(), keyPlace.getRowsNumber(),
                keyPlace.getMetadataCreated(), keyPlace.getPlace().getWay().toString(),
                keyPlace.getPlace().getId(), keyPlace.getIdResource(), keyPlace.getPlace().getMinX(),
                keyPlace.getPlace().getMaxX(), keyPlace.getPlace().getMinY(), keyPlace.getPlace().getMaxY());
        
        session.executeAsync(bind);
    }
    
    /**
     * Get all Resources which its geom intersects with the geom passed by parameter.
     * @param geometry Geometry object
     * @return List of KeyPlaces
     */
    public List<KeyPlace> getIntersectedBy(Geometry geometry) {

        String wkt = geometry.toString();

        List<KeyPlace> keyPlaces = new LinkedList<>();

        Long start = System.currentTimeMillis();
        ResultSet rs = session.execute("SELECT * FROM resource_place WHERE expr(resource_place_idx, ?)",
                search().filter(
                        geoShape("way", wkt(wkt)).operation("intersects")
                ).build());
        Long end = System.currentTimeMillis();
        System.out.println("!!!!!!! CASSANDRA + LUCENE !!!!!!!");
        System.out.println("Executou a Busca e criou o objeto ResultSet. Tempo: "+(end-start)+"ms.");
        
        start = System.currentTimeMillis();
        for (Row row : rs.all()) {

            try {
                keyPlaces.add(formaKeyPlace(row));
            }
            catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        end = System.currentTimeMillis();
        
        System.out.println("Montou todos os resource_places e os adicionou em uma lista. Tempo: "+(end-start)+"ms.");
        System.out.println("Quantidade de Resource_Places encontrados: "+keyPlaces.size());
        System.out.println("!!!!!!! FIM CASSANDRA + LUCENE !!!!!!!");

        return keyPlaces;
    }

    private KeyPlace formaKeyPlace(Row row) throws ParseException {

        WKTReader reader = new WKTReader();

        //Getting Parametters from ResultSet Row
        Integer id = row.getInt("id");
        Integer columNumber = row.getInt("colum_number");
        String columValue = row.getString("colum_value");
        Integer repeat_number = row.getInt("repeat_number");
        Integer rows_number = row.getInt("rows_number");
        Date metadataCreated = row.getTimestamp("metadata_created");
        Timestamp metadata_created = new java.sql.Timestamp(metadataCreated.getTime());
        Geometry geo = reader.read(row.getString("way"));
        Integer id_place = row.getInt("id_place");
        String id_resource = row.getString("id_resource");
        Double minx = row.getDouble("minx");
        Double maxx = row.getDouble("maxx");
        Double miny = row.getDouble("miny");
        Double maxy = row.getDouble("maxy");
        
        //Creating A Place
        Place place = new Place();
        place.setId(id_place);
        place.setWay(geo);
        place.setMinX(minx);
        place.setMaxX(maxx);
        place.setMinY(miny);
        place.setMaxY(maxy);

        //Creating KeyPlace
        KeyPlace keyPlace = new KeyPlace();
        keyPlace.setId(id);
        keyPlace.setColumNumber(columNumber);
        keyPlace.setColumValue(columValue);
        keyPlace.setRepeatNumber(repeat_number);
        keyPlace.setRowsNumber(rows_number);
        keyPlace.setMetadataCreated(metadata_created);
        keyPlace.setPlace(place);
        
        return keyPlace;
    }

}
