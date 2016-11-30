/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.headerAnality;

import java.util.Objects;

/**
 *
 * @author kieckegard
 */
public class ResourceColumn implements Comparable<ResourceColumn> {
    
    private String resourceId;
    private Integer columnNumber;
    
    public ResourceColumn(String resourceId, Integer columnNumber) {
        this.resourceId = resourceId;
        this.columnNumber = columnNumber;
    }
    
    public String getResourceId() {
        return this.resourceId;
    }
    
    public Integer getColumnNumber() {
        return this.columnNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof ResourceColumn)) return false;
        
        ResourceColumn aux = (ResourceColumn) obj;
        
        if(!this.columnNumber.equals(aux.columnNumber)) return false;
        return this.resourceId.equals(aux.resourceId);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.resourceId);
        hash = 17 * hash + Objects.hashCode(this.columnNumber);
        return hash;
    }

    @Override
    public String toString() {
        return "ResourceColumn{" + "resourceId=" + resourceId + ", columnNumber=" + columnNumber + '}';
    }

    @Override
    public int compareTo(ResourceColumn o) {
        return this.resourceId.compareTo(o.resourceId);
    }
}
