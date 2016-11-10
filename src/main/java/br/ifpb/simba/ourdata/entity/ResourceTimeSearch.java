/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.simba.ourdata.entity;

import java.sql.Timestamp;

/**
 *
 * @author wensttay
 */
public class ResourceTimeSearch {
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;
    private Long repeatNumber;
    private Long resourceRowsNumber;
    private String resourceId;
    private String resourceUrl;
    private Float intervelTimes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Long getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(Long repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    public Long getResourceRowsNumber() {
        return resourceRowsNumber;
    }

    public void setResourceRowsNumber(Long resourceRowsNumber) {
        this.resourceRowsNumber = resourceRowsNumber;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Float getIntervelTimes() {
        return intervelTimes;
    }

    public void setIntervelTimes(Float intervelTimes) {
        this.intervelTimes = intervelTimes;
    }

    @Override
    public String toString() {
        return "ResourceTimeSearch{\n\t" + "description=" + description + ", \n\tstartDate=" + startDate + ", \n\tendDate=" + endDate + ", \n\trepeatNumber=" + repeatNumber + ", \n\tresourceRowsNumber=" + resourceRowsNumber + ", \n\tresourceId=" + resourceId + ", \n\tresourceUrl=" + resourceUrl + ", \n\tintervelTimes=" + intervelTimes + "\n}";
    }
    
}
