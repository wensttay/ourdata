package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.utils.PlaceUtils;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class TestOverLap {

    public static void main(String[] args) throws ParseException {
        Geometry geometry = new WKTReader().read("POLYGON((1 1, 3 1, 3 3, 1 3, 1 1))");
//        Geometry geometry2 = new WKTReader().read("POLYGON((2 1, 4 1, 4 3, 2 3, 2 1))");
        Geometry geometry2 = new WKTReader().read("POLYGON((1 1, 3 1, 3 3, 1 3, 1 1))");
        Place p1 = new Place(geometry);
        p1.setMaxX(3);
        p1.setMaxY(3);
        p1.setMinX(1);
        p1.setMinY(1);

        Place p2 = new Place(geometry2);
//        p2.setMaxX(4);
//        p2.setMaxY(3);
//        p2.setMinX(2);
//        p2.setMinY(1);
        p2.setMaxX(3);
        p2.setMaxY(3);
        p2.setMinX(1);
        p2.setMinY(1);
        double overlapPercent = PlaceUtils.getOverlap(p1, p2, 0.5f);
        System.out.println(overlapPercent);
    }

}
