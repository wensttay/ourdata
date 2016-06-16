/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

import br.ifpb.simba.ourdata.entity.utils.PlaceUtils;
import com.vividsolutions.jts.geom.Geometry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class Resource
{
    private String id;
    private String descricao;
    private String formato;
    private String url;
    private List<KeyPlace> keyplaces;
    private String idDataset;
    private Place place;
    
    public Resource(){
        
    }
    
    public Resource(String id, String descricao, String formato, String url, String idDataset){
        this.id = id;
        this.descricao = descricao;
        this.formato = formato;
        this.url = url;
        this.idDataset = idDataset;
        this.keyplaces = new ArrayList<>();
    }
    
    public void addKeyPlace(KeyPlace kp){
        getKeyplaces().add(kp);
    }
    
    public List<KeyPlace> getKeyPlaces(){
        return Collections.unmodifiableList(getKeyplaces());
    }
    
    
    public float getRepeatPercent(float constante){
        int sum_repeat=0;
        int rows=0;
        if(!keyplaces.isEmpty())
            rows = getKeyplaces().get(0).getRowsNumber();
        else
            return 0;
        for(KeyPlace kp : getKeyplaces())
            sum_repeat += kp.getRepeatNumber();
        return (sum_repeat/rows)*constante;
    }

//    @Override
//    public int compareTo(Resource o) {
//        float constante = new Float(0.5);
//        double thisScore = this.getRepeatPercent(constante) + PlaceUtils.getOverlap(this.getPlace(), o.getPlace(), constante);
//        double otherScore = o.getRepeatPercent(constante) + PlaceUtils.getOverlap(o.getPlace(), this.getPlace(), constante);
//        return 
//    }
    
    public double getScoreOfAprox(Place p, float constante){
        return this.getRepeatPercent(constante) + PlaceUtils.getOverlap(this.getPlace(), p, constante);
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the keyplaces
     */
    public List<KeyPlace> getKeyplaces() {
        return keyplaces;
    }

    /**
     * @param keyplaces the keyplaces to set
     */
    public void setKeyplaces(List<KeyPlace> keyplaces) {
        this.keyplaces = keyplaces;
    }

    /**
     * @return the idDataset
     */
    public String getIdDataset() {
        return idDataset;
    }

    /**
     * @param idDataset the idDataset to set
     */
    public void setIdDataset(String idDataset) {
        this.idDataset = idDataset;
    }

    /**
     * @return the place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Place place) {
        this.place = place;
    }
    
}
    
   
