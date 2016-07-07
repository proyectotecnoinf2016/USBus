package com.usbus.dal.model;

import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "reservations",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iReservationKey", unique=true))})
public class Reservation extends BaseEntity {
    private Long id;
//    @Reference
//    private User passenger;
    private String clientId;
    private Date dueDate;
//    @Reference
//    private Journey journey;
    private Long journeyId;
    private Integer seat;
    private Boolean active;

    public Reservation() {
    }

    public Reservation(long tenantId, Long id, String clientId, Date dueDate, Long journeyId, Integer seat, Boolean active) {
        super(tenantId);
        this.id = id;
        this.clientId = clientId;
        this.dueDate = dueDate;
        this.journeyId = journeyId;
        this.seat = seat;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}