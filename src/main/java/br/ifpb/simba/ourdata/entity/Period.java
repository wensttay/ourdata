package br.ifpb.simba.ourdata.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @version 1.0
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/01/2017 - 12:01:31
 */
public class Period {

    private PeriodTime startDate;
    private PeriodTime endDate;
    private List<Integer> rows;

    public Period() {
    }

    public Period(PeriodTime startDate, PeriodTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        rows = new ArrayList<>();
    }

    public boolean intersect(Period htPeriod) {

        if (htPeriod == null) {
            return false;
        }

        if (this.getStartDate() == null || this.getEndDate() == null
                || htPeriod.getStartDate() == null || htPeriod.getEndDate() == null) {
            return false;
        }

        return this.getStartDate().getDate().after(htPeriod.getStartDate().getDate())
                && this.getStartDate().getDate().before(htPeriod.getEndDate().getDate())
                || this.getEndDate().getDate().after(htPeriod.getStartDate().getDate())
                && this.getEndDate().getDate().before(htPeriod.getEndDate().getDate());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.startDate);
        hash = 67 * hash + Objects.hashCode(this.endDate);
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
        final Period other = (Period) obj;
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        return true;
    }

    public PeriodTime getStartDate() {
        return startDate;
    }

    public void setStartDate(PeriodTime startDate) {
        this.startDate = startDate;
    }

    public PeriodTime getEndDate() {
        return endDate;
    }

    public void setEndDate(PeriodTime endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getRows() {
        return rows;
    }

    public void setRows(List<Integer> rows) {
        this.rows = rows;
    }

    public void addRow(int row) {
        this.rows.add(row);
    }

    public void addAllRows(List<Integer> allRows) {
        for (Integer currentRow : allRows) {
            if (!rows.contains(currentRow)) {
                rows.add(currentRow);
            }
        }
    }
}
