package com.usbus.dal.model;

import com.usbus.dal.BaseEntity;
import com.usbus.dal.dao.JourneyDAO;
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
    private String serviceName;
    private Integer seat;
    private Boolean active;
    private String getsOn;
    private String getsOff;

    public Reservation() {
    }

    public Reservation(long tenantId, Long id, String clientId, Date dueDate, Long journeyId, Integer seat, Boolean active, String getsOn, String getsOff) {
        super(tenantId);
        this.id = id;
        this.clientId = clientId;
        this.dueDate = dueDate;
        this.journeyId = journeyId;
        this.serviceName = new JourneyDAO().getByJourneyId(tenantId,journeyId).getService().getName();
        this.seat = seat;
        this.active = active;
        this.getsOn = getsOn;
        this.getsOff = getsOff;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getGetsOn() {
        return getsOn;
    }

    public void setGetsOn(String getsOn) {
        this.getsOn = getsOn;
    }

    public String getGetsOff() {
        return getsOff;
    }

    public void setGetsOff(String getsOff) {
        this.getsOff = getsOff;
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