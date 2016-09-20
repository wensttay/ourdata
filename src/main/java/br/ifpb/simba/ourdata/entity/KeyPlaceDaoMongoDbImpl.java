/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

import com.github.filosganga.geogson.gson.GeometryAdapterFactory;
import com.github.filosganga.geogson.jts.JtsAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vividsolutions.jts.geom.Geometry;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author kieckegard
 */
public class KeyPlaceDaoMongoDbImpl implements KeyPlaceDao {
    
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> collection;
    
    public KeyPlaceDaoMongoDbImpl() {
        this.mongoDatabase = MongoDbConnection.getMongoClient().getDatabase("ourdata");
        this.collection = this.mongoDatabase.getCollection("resource_place");
    }

    @Override
    public void insert(KeyPlace kp) {
        collection.insertOne(kp.toDocument());
    }
    
    public List<KeyPlace> getIntersected(Geometry geometry) {
        
        List<KeyPlace> resultList = new LinkedList<>();
        
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new GeometryAdapterFactory())
                .registerTypeAdapterFactory(new JtsAdapterFactory())
                .create();
        
        String geometryJson = gson.toJson(geometry, Geometry.class);
        Document bsonGeometry = Document.parse(geometryJson);
        
        
        Document geoIntersects = new Document("$geoIntersects", new Document("$geometry", bsonGeometry));
        System.out.println(geoIntersects);
        
        Long start = System.currentTimeMillis();
        FindIterable<Document> result = collection.find(new Document("place.way", geoIntersects));
        
        MongoCursor<Document> cursor = result.iterator();
        
        
        while(cursor.hasNext()) {
            resultList.add(new KeyPlace().fromDocument(cursor.next()));
        }
        Long end = System.currentTimeMillis();
        System.out.println("timeout: "+(end-start));
        
        return resultList;
    }

    
}