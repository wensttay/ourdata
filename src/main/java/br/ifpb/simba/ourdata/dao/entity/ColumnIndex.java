/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.dao.entity;

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author kieckegard
 */
public class ColumnIndex {
    
    private Integer columnNumber;
    private final Set<String> values;
    
    public ColumnIndex(Integer columnNumber) {
        this.values = new TreeSet<>();
        this.columnNumber = columnNumber;
    }
    
    public ColumnIndex() {
        this.values = new TreeSet<>();
    }
    
    public Integer getColumnNumber() {
        return this.columnNumber;
    }
    
    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }
    
    public void addValue(String value) {
        this.values.add(value);
    }
    
    public void remValue(String value) {
        this.values.remove(value);
    }
    
    public void addAll(Set<String> values) {
        this.values.addAll(values);
    }
    
    public Set<String> getValues() {
        return Collections.unmodifiableSet(this.values);
    }
}
