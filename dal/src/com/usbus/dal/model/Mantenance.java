package com.usbus.dal.model;

import com.usbus.dal.BaseEntity;

import java.util.Date;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class Mantenance extends BaseEntity {
    private Long id;
    private Bus bus;
    private String jobDescription;
    private Double cost;
    private Date startDate;
    private Date endDate;
    private String[] notes;

    public Mantenance(){
    }

    public Mantenance(long tenantId, Long id, Bus bus, String jobDescription, Double cost, Date startDate, Date endDate, String[] notes) {
        super(tenantId);
        this.id = id;
        this.bus = bus;
        this.jobDescription = jobDescription;
        this.cost = cost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String[] getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }
}
