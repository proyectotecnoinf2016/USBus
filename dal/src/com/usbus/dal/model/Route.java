package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lufasoch on 26/05/2016.
 */
@XmlRootElement
@Entity(value = "routes",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iRouteKey", unique=true)),
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "name") }, options = @IndexOptions(name="iRouteName", unique=true))})
public class Route extends BaseEntity {
    private Long id;
    private String name;
    private String origin;
    private BusStop destination;
    private RouteStop[] busStops;
    private Boolean active;
    private Boolean hasCombination;
    private Double pricePerKm;

    public Route(){
    }

    public Route(long tenantId, Long id, String name, String origin, BusStop destination, RouteStop[] busStops, Boolean active, Boolean hasCombination, Double pricePerKm) {
        super(tenantId);
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.destination = destination;
        this.busStops = busStops;
        this.active = active;
        this.hasCombination = hasCombination;
        this.pricePerKm = pricePerKm;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public BusStop getDestination() {
        return destination;
    }

    public void setDestination(BusStop destination) {
        this.destination = destination;
    }

    public RouteStop[] getBusStops() {
        return busStops;
    }

    public void setBusStops(RouteStop[] busStops) {
        this.busStops = busStops;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getHasCombination() {
        return hasCombination;
    }

    public void setHasCombination(Boolean hasCombination) {
        this.hasCombination = hasCombination;
    }

    public Double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(Double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
}
