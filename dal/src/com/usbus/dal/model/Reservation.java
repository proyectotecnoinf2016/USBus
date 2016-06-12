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
public class Reservation extends BaseEntity{
    private Long id;
    private User passenger;
    private Date dueDate;
    @Reference
    private Journey journey;
    private Integer seat;
    private Boolean active;

    public Reservation(){
    }

    public Reservation(long tenantId, Long id, User passenger, Date dueDate, Journey journey, Integer seat, Boolean active) {
        super(tenantId);
        this.id = id;
        this.passenger = passenger;
        this.dueDate = dueDate;
        this.journey = journey;
        this.seat = seat;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
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
