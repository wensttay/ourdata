/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

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
    private Geometry way;
    
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
        keyplaces.add(kp);
    }
    
    public List<KeyPlace> getKeyPlaces(){
        return Collections.unmodifiableList(keyplaces);
    }
    
    public String getId(){
        return id;
    }
    
    public String getDescricao(){
        return descricao;
    }
    
    public String getFormato(){
        return formato;
    }
    
    public String getUrl(){
        return url;
    }
    
    public String getIdDataset(){
        return idDataset;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public void setFormato(String formato)
    {
        this.formato = formato;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setIdDataset(String idDataset)
    {
        this.idDataset = idDataset;
    }
    
    public Geometry getWay(){
        return way;
    }
    
    public void setWay(Geometry g){
        this.way = g;
    }
    
    
}
    
   
