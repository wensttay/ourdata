/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.shared.domain;


/**
 *
 * @author Pedro Arthur
 */
public class ResourceItemSearch {
    
    private Resource resource;
    private Float score;

    public ResourceItemSearch(Resource resource, Float score) {
        this.resource = resource;
        this.score = score;
    }

    public ResourceItemSearch() {
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ResourceItemSearch{" + "resource=" + resource + ", score=" + score + '}';
    }
}
