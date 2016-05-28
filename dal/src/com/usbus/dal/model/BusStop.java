package com.usbus.dal.model;

import com.usbus.dal.BaseEntity;

/**
 * Created by Lufasoch on 26/05/2016.
 */
public class BusStop extends BaseEntity {
    private long id;
    private String name;
    private Boolean active;
    private Double stopTime;

    public BusStop(){
    }

    public BusStop(long tenantId, long id, String name, Boolean active, Double stopTime) {
        super(tenantId);
        this.id = id;
        this.name = name;
        this.active = active;
        this.stopTime = stopTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getStopTime() {
        return stopTime;
    }

    public void setStopTime(Double stopTime) {
        this.stopTime = stopTime;
    }
}
