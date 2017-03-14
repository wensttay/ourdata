package br.ifpb.simba.ourdata.test;

import br.ifpb.simba.ourdata.dao.entity.PlaceBdDao;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.utils.PlaceUtils;
import com.vividsolutions.jts.geom.Geometry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class TestIntersectionArea {

    public static void main(String[] args) {
        PlaceBdDao bdDao = new PlaceBdDao();

        List<Place> places = new ArrayList<>();
        places = bdDao.burcarPorTitulos("Barro");
        Place sousa = places.get(0);
        places = bdDao.burcarPorTitulos("Cajazeiras");
        Place paraiba = places.get(0);

        Geometry way = sousa.getWay();
        way = way.intersection(paraiba.getWay());
        double area = way.getArea();

        long t1, t2, t3;
        System.out.println("Area do primeiro lugar: " + sousa.getWay().getArea());
        System.out.println("Area do segundo lugar: " + paraiba.getWay().getArea());

        t1 = System.currentTimeMillis();
        System.out.println("T1: " + t1);
        System.out.println("Meu metodo: " + PlaceUtils.getIntersectArea(sousa, paraiba));
        t2 = System.currentTimeMillis();
        System.out.println("T2: " + t2);
        System.out.println("Geometry method: " + area);
        t3 = System.currentTimeMillis();
        System.out.println("T3: " + t3);

        System.out.println(area - PlaceUtils.getIntersectArea(sousa, paraiba));

//        for (Place place : places) {
//            System.out.println(place.toString());
//        }
        System.out.println("");
        System.out.println("Fim");

//        RDF
//        Sparkl
//        OWL
    }
}
