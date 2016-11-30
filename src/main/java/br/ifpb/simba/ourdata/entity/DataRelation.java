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
public class DataRelation {
    
    private Long id;
    private String firstColumnValue;
    private Integer firstColumnNumber;
    private String firstResourceId;
    
    private String secondColumnValue;
    private Integer secondColumnNumber;
    private String secondResourceId;

    public DataRelation(String firstColumnValue, Integer firstColumnNumber, String firstResourceId, String secondColumnValue, Integer secondColumnNumber, String secondResourceId) {
        this.firstColumnValue = firstColumnValue;
        this.firstColumnNumber = firstColumnNumber;
        this.firstResourceId = firstResourceId;
        this.secondColumnValue = secondColumnValue;
        this.secondColumnNumber = secondColumnNumber;
        this.secondResourceId = secondResourceId;
    }
    
    public DataRelation() {
        
    }

    public String getFirstColumnValue() {
        return firstColumnValue;
    }

    public void setFirstColumnValue(String firstColumnValue) {
        this.firstColumnValue = firstColumnValue;
    }

    public Integer getFirstColumnNumber() {
        return firstColumnNumber;
    }

    public void setFirstColumnNumber(Integer firstColumnNumber) {
        this.firstColumnNumber = firstColumnNumber;
    }

    public String getFirstResourceId() {
        return firstResourceId;
    }

    public void setFirstResourceId(String firstResourceId) {
        this.firstResourceId = firstResourceId;
    }

    public String getSecondColumnValue() {
        return secondColumnValue;
    }

    public void setSecondColumnValue(String secondColumnValue) {
        this.secondColumnValue = secondColumnValue;
    }

    public Integer getSecondColumnNumber() {
        return secondColumnNumber;
    }

    public void setSecondColumnNumber(Integer secondColumnNumber) {
        this.secondColumnNumber = secondColumnNumber;
    }

    public String getSecondResourceId() {
        return secondResourceId;
    }

    public void setSecondResourceId(String secondResourceId) {
        this.secondResourceId = secondResourceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    @Override
    public String toString() {
        return "DataRelation{" + "firstColumnValue=" + firstColumnValue + ", firstColumnNumber=" + firstColumnNumber + ", firstResourceId=" + firstResourceId + ", secondColumnValue=" + secondColumnValue + ", secondColumnNumber=" + secondColumnNumber + ", secondResourceId=" + secondResourceId + '}';
    }
    
    
}
