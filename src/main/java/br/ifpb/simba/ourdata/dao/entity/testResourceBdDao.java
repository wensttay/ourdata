/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.KeyPlace;
import br.ifpb.simba.ourdata.entity.Place;
import br.ifpb.simba.ourdata.entity.Resource;

/**
 *
 * @author kieckegard
 */
public class testResourceBdDao
{
    public static void main(String[] args)
    {
        ResourceBdDao dao = new ResourceBdDao();
        for(Resource r : dao.getResourcesIntersectedBy()){
            System.out.println("[BBOX] Min X: "+r.getMinX()+" Max X:"+r.getMaxX()+" Min Y: "+r.getMinY()+" Max Y:"+r.getMaxY()+"\n\n");
            for(KeyPlace kp : r.getKeyPlaces()){
                Place p = kp.getPlace();
                System.out.println("Min X: "+p.getMinX()+" Max X:"+p.getMaxX()+" Min Y: "+p.getMinY()+" Max Y:"+p.getMaxY());
            }
            System.out.println("====================================================");
        }
    }
}
