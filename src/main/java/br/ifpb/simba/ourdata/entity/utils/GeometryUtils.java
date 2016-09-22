/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 *
 * @author kieckegard
 */
public class GeometryUtils {
    
    public static Geometry fromEnvelope(Double maxx, Double maxy, Double minx, Double miny) throws ParseException {
        
        StringBuilder wktBuilder = new StringBuilder("POLYGON(");
        wktBuilder.append("(")
                .append(minx).append(" ").append(miny).append(",")
                .append(minx).append(" ").append(maxy).append(",")
                .append(maxx).append(" ").append(maxy).append(",")
                .append(maxx).append(" ").append(miny).append(",")
                .append(minx).append(" ").append(miny).append("))");
        
        
        String wkt = wktBuilder.toString();
        
        System.out.println(wkt);
        
        return new WKTReader().read(wkt);
    }
}
