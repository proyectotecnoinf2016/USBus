package com.usbus.dal.model;


import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.DayOfWeek;
import java.util.Date;

/**
 * Created by Lufasoch on 26/05/2016.
 */
@XmlRootElement
@Entity(value = "services",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id"),  @Field(value = "name")}, options = @IndexOptions(name="iServiceKey", unique=true))})
public class Service extends BaseEntity {
    private Long id;
    private String name;
    private DayOfWeek day;
    private Date time;
    private Route route;
    private Integer numberOfBuses;
    private Boolean active;

    public Service(){
    }

    public Service(long tenantId, Long id, String name, DayOfWeek day, Date time, Route route, Integer numberOfBuses, Boolean active) {
        super(tenantId);
        this.id = id;
        this.name = name;
        this.day = day;
        this.time = time;
        this.route = route;
        this.numberOfBuses = numberOfBuses;
        this.active = active;
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

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
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
