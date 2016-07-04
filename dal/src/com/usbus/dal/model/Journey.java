package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.enums.JourneyStatus;
import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Lufasoch on 26/05/2016.
 */
@XmlRootElement
@Entity(value = "journeys",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iJourneyKey", unique=true))})
public class
Journey extends BaseEntity{
    private Long id;
    @Reference
    private Service service;
    private Date date;
    @Reference
    private Bus bus;
    private String thirdPartyBus;
    @Reference
    private HumanResource driver;
    private HumanResource assistant;
    private Integer busNumber;
    private Integer seats;
    private Seat seatsState[];
    private Integer standingPassengers;
    private Integer trunkWeight;
    private JourneyStatus status;

    public Journey(){
    }

    public Journey(long tenantId, Long id, Service service, Date date, Bus bus, String thirdPartyBus, HumanResource driver, HumanResource assistant, Integer busNumber, Integer seats, Seat[] seatsState, Integer standingPassengers, Integer trunkWeight, JourneyStatus status) {
        super(tenantId);
        this.id = id;
        this.service = service;
        this.date = date;
        this.bus = bus;
        this.thirdPartyBus = thirdPartyBus;
        this.driver = driver;
        this.assistant = assistant;
        this.busNumber = busNumber;
        this.seats = seats;
        this.seatsState = seatsState;
        this.standingPassengers = standingPassengers;
        this.trunkWeight = trunkWeight;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getThirdPartyBus() {
        return thirdPartyBus;
    }

    public void setThirdPartyBus(String thirdPartyBus) {
        this.thirdPartyBus = thirdPartyBus;
    }

    public HumanResource getDriver() {
        return driver;
    }

    public void setDriver(HumanResource driver) {
        this.driver = driver;
    }

    public HumanResource getAssistant() {
        return assistant;
    }

    public void setAssistant(HumanResource assistant) {
        this.assistant = assistant;
    }

    public Integer getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(Integer busNumber) {
        this.busNumber = busNumber;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Seat[] getSeatsState() {
        return seatsState;
    }

    public void setSeatsState(Seat[] seatsState) {
        this.seatsState = seatsState;
    }

    public Integer getStandingPassengers() {
        return standingPassengers;
    }

    public void setStandingPassengers(Integer standingPassengers) {
        this.standingPassengers = standingPassengers;
    }

    public Integer getTrunkWeight() {
        return trunkWeight;
    }

    public void setTrunkWeight(Integer trunkWeight) {
        this.trunkWeight = trunkWeight;
    }

    public JourneyStatus getStatus() {
        return status;
    }

    public void setStatus(JourneyStatus status) {
        this.status = status;
    }
}
