/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import br.ifpb.simba.ourdata.entity.KeyPlace;
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
            System.out.println("\n\nid resource: "+r.getId());
            System.out.println("url: "+r.getUrl());
            System.out.println("KeyPlaces: ");
            for(KeyPlace kp : r.getKeyPlaces()){
                System.out.println("local: "+kp.getColumValue());
            }
        }
    }
}
