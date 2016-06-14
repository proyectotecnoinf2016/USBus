package com.usbus.dal.model;

import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lufasoch on 26/05/2016.
 */
@XmlRootElement
@Entity(value = "busStops",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iBusStopKey", unique=true)),
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "name") }, options = @IndexOptions(name="iBusStopName", unique=true))})
public class BusStop extends BaseEntity {
//    @Property(value = "busStopId")
    private Long id;
//    @Property(value = "busStopName")
    private String name;
    private Boolean active;
    private Double stopTime;

    public BusStop(){
    }

    public BusStop(long tenantId, Long id, String name, Boolean active, Double stopTime) {
        super(tenantId);
        this.id = id;
        this.name = name;
        this.active = active;
        this.stopTime = stopTime;
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
