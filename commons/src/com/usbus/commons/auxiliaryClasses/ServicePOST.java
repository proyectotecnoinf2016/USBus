package com.usbus.commons.auxiliaryClasses;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

/**
 * Created by jpmartinez on 01/07/16.
 */
public class ServicePOST {
    private Long tenantId;
    private Long id;
    private String name;
    private List<DayOfWeek> day;
    private List<Date> time;
    private Long route;
    private Integer numberOfBuses;
    private Boolean active;

    public ServicePOST(){
    }

    public ServicePOST(long tenantId, Long id, String name, List<DayOfWeek> day, List<Date> time, Long route, Integer numberOfBuses, Boolean active) {
        this.tenantId = tenantId;
        this.id = id;
        this.name = name;
        this.day = day;
        this.time = time;
        this.route = route;
        this.numberOfBuses = numberOfBuses;
        this.active = active;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DayOfWeek> getDay() {
        return day;
    }

    public void setDay(List<DayOfWeek> day) {
        this.day = day;
    }

    public List<Date> getTime() {
        return time;
    }

    public void setTime(List<Date> time) {
        this.time = time;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
        this.route = route;
    }

    public Integer getNumberOfBuses() {
        return numberOfBuses;
    }

    public void setNumberOfBuses(Integer numberOfBuses) {
        this.numberOfBuses = numberOfBuses;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
