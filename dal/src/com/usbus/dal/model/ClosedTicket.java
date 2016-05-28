package com.usbus.dal.model;

import java.util.Date;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class ClosedTicket extends Ticket {
    private Journey journey;
    private Integer seat;
    private BusStop getsOn;
    private BusStop getsOff;

    public ClosedTicket(){
    }

    public ClosedTicket(long tenantId, Long id, Date emissionDate, Boolean hasCombination, Service combination, Double amount, Journey journey, Integer seat, BusStop getsOn, BusStop getsOff) {
        super(tenantId, id, emissionDate, hasCombination, combination, amount);
        this.journey = journey;
        this.seat = seat;
        this.getsOn = getsOn;
        this.getsOff = getsOff;
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

    public BusStop getGetsOn() {
        return getsOn;
    }

    public void setGetsOn(BusStop getsOn) {
        this.getsOn = getsOn;
    }

    public BusStop getGetsOff() {
        return getsOff;
    }

    public void setGetsOff(BusStop getsOff) {
        this.getsOff = getsOff;
    }
}
