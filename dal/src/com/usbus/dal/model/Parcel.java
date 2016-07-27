package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.Dimension;
import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "parcels",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iParcelKey", unique=true))})
public class Parcel extends BaseEntity {
    private Long id;
    private Dimension dimensions;
    private Integer weight;
    @Reference
    private Journey journey;
    @Transient
    private Long journeyId;
    @Reference
    private BusStop origin;
    @Transient
    private String originName;
    @Transient
    private Long originId;
    @Reference
    private BusStop destination;
    @Transient
    private Long destinationId;
    @Transient
    private String destinationName;
    private String from;
    private String to;
    private Boolean delivered;
    private Boolean onDestination;
    private Boolean paid;

    public Parcel() {
    }

    public Parcel(long tenantId, Long id, Dimension dimensions, Integer weight, Journey journey, BusStop origin, BusStop destination, String from, String to, Boolean delivered, Boolean onDestination, Boolean paid) {
        super(tenantId);
        this.id = id;
        this.dimensions = dimensions;
        this.weight = weight;
        this.journey = journey;
        this.origin = origin;
        this.destination = destination;
        this.from = from;
        this.to = to;
        this.delivered = delivered;
        this.onDestination = onDestination;
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public BusStop getOrigin() {
        return origin;
    }

    public void setOrigin(BusStop origin) {
        this.origin = origin;
    }

    public BusStop getDestination() {
        return destination;
    }

    public void setDestination(BusStop destination) {
        this.destination = destination;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Boolean getOnDestination() {
        return onDestination;
    }

    public void setOnDestination(Boolean onDestination) {
        this.onDestination = onDestination;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
