package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.enums.JourneyStatus;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kavesa on 27/06/16.
 */
public class JourneyPatch {

    private final List<JourneyPatchField> updatedFields = new ArrayList<JourneyPatchField>();

    public List<JourneyPatchField> updatedFields() {
        return updatedFields;
    }

    public enum JourneyPatchField {
        tenantId,
        id,
        service,
        date,
        bus,
        thirdPartyBus,
        driver,
        busNumber,
        seats,
        seatsState,
        standingPassengers,
        trunkWeight,
        status
    }

    private long tenantId;
    private Long id;
    @Reference
    private Service service;
    private Date date;
    @Reference
    private Bus bus;
    private String thirdPartyBus;
    @Reference
    private HumanResource driver;
    private Integer busNumber;
    private Integer seats;
    private Seat seatsState[];
    private Integer standingPassengers;
    private String trunkWeight;
    private JourneyStatus status;


    public List<JourneyPatchField> getUpdatedFields() {
        return updatedFields;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        updatedFields.add(JourneyPatchField.tenantId);
        this.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        updatedFields.add(JourneyPatchField.id);
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        updatedFields.add(JourneyPatchField.service);
        this.service = service;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        updatedFields.add(JourneyPatchField.date);
        this.date = date;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        updatedFields.add(JourneyPatchField.bus);
        this.bus = bus;
    }

    public String getThirdPartyBus() {
        return thirdPartyBus;
    }

    public void setThirdPartyBus(String thirdPartyBus) {
        updatedFields.add(JourneyPatchField.thirdPartyBus);
        this.thirdPartyBus = thirdPartyBus;
    }

    public HumanResource getDriver() {
        return driver;
    }

    public void setDriver(HumanResource driver) {
        updatedFields.add(JourneyPatchField.driver);
        this.driver = driver;
    }

    public Integer getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(Integer busNumber) {
        updatedFields.add(JourneyPatchField.busNumber);
        this.busNumber = busNumber;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        updatedFields.add(JourneyPatchField.seats);
        this.seats = seats;
    }

    public Seat[] getSeatsState() {
        return seatsState;
    }

    public void setSeatsState(Seat[] seatsState) {
        updatedFields.add(JourneyPatchField.seatsState);
        this.seatsState = seatsState;
    }

    public Integer getStandingPassengers() {
        return standingPassengers;
    }

    public void setStandingPassengers(Integer standingPassengers) {
        updatedFields.add(JourneyPatchField.standingPassengers);
        this.standingPassengers = standingPassengers;
    }

    public String getTrunkWeight() {
        return trunkWeight;
    }

    public void setTrunkWeight(String trunkWeight) {
        updatedFields.add(JourneyPatchField.trunkWeight);
        this.trunkWeight = trunkWeight;
    }

    public JourneyStatus getStatus() {
        return status;
    }

    public void setStatus(JourneyStatus status) {
        updatedFields.add(JourneyPatchField.status);
        this.status = status;
    }
}
