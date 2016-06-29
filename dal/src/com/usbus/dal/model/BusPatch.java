package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.enums.BusStatus;
import com.usbus.commons.enums.JourneyStatus;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kavesa on 27/06/16.
 */
public class BusPatch {

    private String id;
    private String brand;
    private String model;
    private Double kms;
    private Double nextMaintenance;
    private BusStatus status;
    private Boolean active;
    private Integer seats;
    private Integer trunkMaxWeight;
    private Integer standingPassengers;


    private final List<BusPatchField> updatedFields = new ArrayList<>();

    public List<BusPatchField> updatedFields() {
        return updatedFields;
    }

    public List<BusPatchField> getUpdatedFields() {
        return updatedFields;
    }

    public enum BusPatchField {
        id,
        brand,
        model,
        kms,
        nextMaintenance,
        status,
        active,
        seats,
        trunkMaxWeight,
        standingPassengers
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        updatedFields.add(BusPatchField.id);
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        updatedFields.add(BusPatchField.brand);
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        updatedFields.add(BusPatchField.model);
        this.model = model;
    }

    public Double getKms() {
        return kms;
    }

    public void setKms(Double kms) {
        updatedFields.add(BusPatchField.kms);
        this.kms = kms;
    }

    public Double getNextMaintenance() {
        return nextMaintenance;
    }

    public void setNextMaintenance(Double nextMaintenance) {
        updatedFields.add(BusPatchField.nextMaintenance);
        this.nextMaintenance = nextMaintenance;
    }

    public BusStatus getStatus() {
        return status;
    }

    public void setStatus(BusStatus status) {
        updatedFields.add(BusPatchField.status);
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        updatedFields.add(BusPatchField.active);
        this.active = active;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        updatedFields.add(BusPatchField.seats);
        this.seats = seats;
    }

    public Integer getTrunkMaxWeight() {
        return trunkMaxWeight;
    }

    public void setTrunkMaxWeight(Integer trunkMaxWeight) {
        updatedFields.add(BusPatchField.trunkMaxWeight);
        this.trunkMaxWeight = trunkMaxWeight;
    }

    public Integer getStandingPassengers() {
        return standingPassengers;
    }

    public void setStandingPassengers(Integer standingPassengers) {
        updatedFields.add(BusPatchField.standingPassengers);
        this.standingPassengers = standingPassengers;
    }
}
