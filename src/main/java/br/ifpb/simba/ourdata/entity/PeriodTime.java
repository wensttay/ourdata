/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author wensttay
 */
public class PeriodTime {

    private Date date;
    private int col;

    public PeriodTime() {
    }

    public PeriodTime(Date date) {
        this.date = date;
    }

    public PeriodTime(Date date, int col) {
        this.date = date;
        this.col = col;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.date);
        hash = 67 * hash + this.col;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PeriodTime other = (PeriodTime) obj;
        if (this.col != other.col) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

}
