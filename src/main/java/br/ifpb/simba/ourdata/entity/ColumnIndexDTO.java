/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

import java.util.Objects;

/**
 *
 * @author Pedro Arthur
 */
public class ColumnIndexDTO {
    
    private Long id;
    private String value;
    private Integer rowsCount;
    private Integer columnNumber;
    private String resourceId;
    private String datasetId;

    public ColumnIndexDTO(Long id, String value, Integer rowsCount, Integer columnNumber, String resourceId, String datasetId) {
        this.id = id;
        this.value = value;
        this.rowsCount = rowsCount;
        this.columnNumber = columnNumber;
        this.resourceId = resourceId;
        this.datasetId = datasetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(Integer rowsCount) {
        this.rowsCount = rowsCount;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        
        if(!(obj instanceof ColumnIndexDTO)) return false;
        ColumnIndexDTO column = (ColumnIndexDTO) obj;
        
        return this.value.equals(column.value);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public String toString() {
        return "ColumnIndexDTO{" + "id=" + id + ", value=" + value + ", rowsCount=" + rowsCount + ", columnNumber=" + columnNumber + ", resourceId=" + resourceId + ", datasetId=" + datasetId + '}';
    }
    
    
    
}
