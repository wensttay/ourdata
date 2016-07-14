/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

/**
 *
 * @author kieckegard
 */
public class ResourceItemSearch implements Comparable<ResourceItemSearch>
{
   private Resource resource;
   private double ranking;
   
   public ResourceItemSearch(Resource resource, double ranking){
       this.resource = resource;
       this.ranking = ranking;
   }
   
   public Resource getResource(){
       return resource;
   }
   
   public double getRanking(){
       return ranking;
   }

    @Override
    public int compareTo(ResourceItemSearch o)
    {
        if(this.ranking < o.getRanking())
            return 1;
        else if(this.ranking > o.getRanking())
            return -1;
        return 0;
    }
}
