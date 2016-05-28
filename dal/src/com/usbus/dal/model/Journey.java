package com.usbus.dal.model;

import com.usbus.commons.enums.JourneyStatus;
import com.usbus.dal.BaseEntity;

import java.util.Date;

/**
 * Created by Lufasoch on 26/05/2016.
 */
public class Journey extends BaseEntity{
    private Long id;
    private Service service;
    private Date date;
    private Bus bus;
    private String thirdPartyBus;
    private HumanResource driver;
    private Integer busNumber;
    private Integer seats;
    private Integer standingPassengers;
    private String trunkWeight;
    private JourneyStatus status;

    public Journey(){
    }

    public Journey(long tenantId, Long id, Service service, Date date, Bus bus, String thirdPartyBus, HumanResource driver, Integer busNumber, Integer seats, Integer standingPassengers, String trunkWeight, JourneyStatus status) {
        super(tenantId);
        this.id = id;
        this.service = service;
        this.date = date;
        this.bus = bus;
        this.thirdPartyBus = thirdPartyBus;
        this.driver = driver;
        this.busNumber = busNumber;
        this.seats = seats;
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

    public Integer getStandingPassengers() {
        return standingPassengers;
    }

    public void setStandingPassengers(Integer standingPassengers) {
        this.standingPassengers = standingPassengers;
    }

    public String getTrunkWeight() {
        return trunkWeight;
    }

    public void setTrunkWeight(String trunkWeight) {
        this.trunkWeight = trunkWeight;
    }

    public JourneyStatus getStatus() {
        return status;
    }

    public void setStatus(JourneyStatus status) {
        this.status = status;
    }
}
