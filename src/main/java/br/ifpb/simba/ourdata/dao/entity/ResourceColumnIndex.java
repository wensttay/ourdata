/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class ResourceColumnIndex {
    
    private String resourceId;
    private final List<ColumnIndex> columns;
    private Integer rowsQty;
    
    public ResourceColumnIndex(String resourceId, Integer rowsQty) {
        columns = new ArrayList<>();
        this.resourceId = resourceId;
        this.rowsQty = rowsQty;
    }
    
    public ResourceColumnIndex() {
        columns = new ArrayList<>();
    }
    
    public String getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    
    public Integer getRowsQty() {
        return this.rowsQty;
    }
    
    public void setRowsQty(Integer rowsQty) {
        this.rowsQty = rowsQty;
    }
    
    public void addColumn(ColumnIndex column) {
        this.columns.add(column);
    }
    
    public void removeColumn(ColumnIndex column) {
        this.columns.remove(column);
    }
    
    public void addAll(Collection<ColumnIndex> columns) {
        this.columns.addAll(columns);
    }
    
    public List<ColumnIndex> getColumns() {
        return Collections.unmodifiableList(this.columns);
    }
}
