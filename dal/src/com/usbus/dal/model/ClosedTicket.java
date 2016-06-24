//package com.usbus.dal.model;
//
//import com.usbus.commons.enums.TicketStatus;
//import org.mongodb.morphia.annotations.Entity;
//import org.mongodb.morphia.annotations.Reference;
//
//import javax.xml.bind.annotation.XmlRootElement;
//import java.util.Date;
//
///**
// * Created by Lufasoch on 28/05/2016.
// */
//@XmlRootElement
//@Entity(value = "closedTickets",noClassnameStored = false)
//public class ClosedTicket extends Ticket {
//    @Reference
//    private Journey journey;
//    private Integer seat;
//    @Reference
//    private BusStop getsOn;
//    @Reference
//    private BusStop getsOff;
//
//    public Journey getJourney() {
//        return journey;
//    }
//
//    public void setJourney(Journey journey) {
//        this.journey = journey;
//    }
//
//    public Integer getSeat() {
//        return seat;
//    }
//
//    public void setSeat(Integer seat) {
//        this.seat = seat;
//    }
//
//    public BusStop getGetsOn() {
//        return getsOn;
//    }
//
//    public void setGetsOn(BusStop getsOn) {
//        this.getsOn = getsOn;
//    }
//
//    public BusStop getGetsOff() {
//        return getsOff;
//    }
//
//    public void setGetsOff(BusStop getsOff) {
//        this.getsOff = getsOff;
//    }
//
//    public ClosedTicket(){
//    }
//
//    public ClosedTicket(long tenantId, Long id, Date emissionDate, Boolean hasCombination, Service combination, Double amount, User passenger, HumanResource seller, Journey journey, Integer seat, BusStop getsOn, BusStop getsOff, TicketStatus ticketStatus) {
//        super(tenantId, id, emissionDate, hasCombination, combination, amount, passenger, seller,ticketStatus);
//        this.journey = journey;
//        this.seat = seat;
//        this.getsOn = getsOn;
//        this.getsOff = getsOff;
//    }
//
//
//}
