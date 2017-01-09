package br.ifpb.simba.ourdata.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class KeyTime {

    private int repeatNumber;
    private int rowsNumber;
    private Timestamp metadataCreated;
    private Period period;
    private String idResource;

    public KeyTime() {
    }

    public KeyTime(int repeatNumber, int rowsNumber, Timestamp metadataCreated, Period period, String idResource) {
        this.repeatNumber = repeatNumber;
        this.rowsNumber = rowsNumber;
        this.metadataCreated = metadataCreated;
        this.period = period;
        this.idResource = idResource;
    }

    public int getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(int repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public void setRowsNumber(int rowsNumber) {
        this.rowsNumber = rowsNumber;
    }

    public Timestamp getMetadataCreated() {
        return metadataCreated;
    }

    public void setMetadataCreated(Timestamp metadataCreated) {
        this.metadataCreated = metadataCreated;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getIdResource() {
        return idResource;
    }

    public void setIdResource(String idResource) {
        this.idResource = idResource;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final KeyTime other = (KeyTime) obj;
        if (!Objects.equals(this.idResource, other.idResource)) {
            return false;
        }
        if (!Objects.equals(this.period, other.period)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KeyTime{" + "repeatNumber=" + repeatNumber + ", rowsNumber=" + rowsNumber + ", metadataCreated=" + metadataCreated + ", period=" + period + ", idResource=" + idResource + '}';
    }

}
