package com.usbus.dal.model;

import com.usbus.commons.enums.BusStatus;
import com.usbus.commons.enums.TicketStatus;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kavesa on 27/06/16.
 */
public class TicketPatch {

    private Long id;
    private Boolean hasCombination;
    @Reference
    private Service combination;
    @Transient
    private Long combinationId;
    private Double amount;
    @Reference
    private User passenger;
    @Transient
    private String passengerName;
    @Reference
    private HumanResource seller;
    @Transient
    private String sellerName;
    private Boolean closed;
    private TicketStatus status;
    private String paymentToken;
    @Reference
    private Journey journey;
    @Transient
    private Long journeyId;
    private Integer seat;
    @Reference
    private BusStop getsOn;
    @Transient
    private String getOnStopName;
    @Reference
    private BusStop getsOff;
    @Transient
    private String getOffStopName;
    @Reference
    private Route route;
    @Transient
    private Long routeId;
    private Date dueDate;
    private Long branchId;
    private Long windowId;
    private Double kmGetsOn;
    private Double kmGetsOff;


    private final List<TicketPatchField> updatedFields = new ArrayList<>();

    public List<TicketPatchField> updatedFields() {
        return updatedFields;
    }

    public List<TicketPatchField> getUpdatedFields() {
        return updatedFields;
    }

    public enum TicketPatchField {
        id,
        hasCombination,
        combination,
        combinationId,
        amount,
        passenger,
        passengerName,
        seller,
        sellerName,
        closed,
        status,
        paymentToken,
        journey,
        journeyId,
        seat,
        getsOn,
        getOnStopName,
        getsOff,
        getOffStopName,
        route,
        routeId,
        dueDate,
        branchId,
        windowId,
        kmGetsOn,
        kmGetsOff
    }

    public void setId(Long id) {
        updatedFields.add(TicketPatchField.id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Boolean getHasCombination() {
        return hasCombination;
    }

    public void setHasCombination(Boolean hasCombination) {
        this.hasCombination = hasCombination;
        updatedFields.add(TicketPatchField.hasCombination);
    }

    public Service getCombination() {
        return combination;
    }

    public void setCombination(Service combination) {
        this.combination = combination;
        updatedFields.add(TicketPatchField.combination);
    }

    public Long getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(Long combinationId) {
        this.combinationId = combinationId;
        updatedFields.add(TicketPatchField.combinationId);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
        updatedFields.add(TicketPatchField.amount);
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
        updatedFields.add(TicketPatchField.passenger);
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
        updatedFields.add(TicketPatchField.passengerName);
    }

    public HumanResource getSeller() {
        return seller;
    }

    public void setSeller(HumanResource seller) {
        this.seller = seller;
        updatedFields.add(TicketPatchField.seller);
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
        updatedFields.add(TicketPatchField.sellerName);
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
        updatedFields.add(TicketPatchField.closed);
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
        updatedFields.add(TicketPatchField.status);
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
        updatedFields.add(TicketPatchField.paymentToken);
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
        updatedFields.add(TicketPatchField.journey);
    }

    public Long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
        updatedFields.add(TicketPatchField.journeyId);
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
        updatedFields.add(TicketPatchField.seat);
    }

    public BusStop getGetsOn() {
        return getsOn;
    }

    public void setGetsOn(BusStop getsOn) {
        this.getsOn = getsOn;
        updatedFields.add(TicketPatchField.getsOn);
    }

    public String getGetOnStopName() {
        return getOnStopName;
    }

    public void setGetOnStopName(String getOnStopName) {
        this.getOnStopName = getOnStopName;
        updatedFields.add(TicketPatchField.getOnStopName);
    }

    public BusStop getGetsOff() {
        return getsOff;
    }

    public void setGetsOff(BusStop getsOff) {
        this.getsOff = getsOff;
        updatedFields.add(TicketPatchField.getsOff);
    }

    public String getGetOffStopName() {
        return getOffStopName;
    }

    public void setGetOffStopName(String getOffStopName) {
        this.getOffStopName = getOffStopName;
        updatedFields.add(TicketPatchField.getOffStopName);
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
        updatedFields.add(TicketPatchField.route);
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
        updatedFields.add(TicketPatchField.routeId);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        updatedFields.add(TicketPatchField.dueDate);
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
        updatedFields.add(TicketPatchField.branchId);
    }

    public Long getWindowId() {
        return windowId;
    }

    public void setWindowId(Long windowId) {
        this.windowId = windowId;
        updatedFields.add(TicketPatchField.windowId);
    }

    public Double getKmGetsOn() {
        return kmGetsOn;
    }

    public void setKmGetsOn(Double kmGetsOn) {
        this.kmGetsOn = kmGetsOn;
        updatedFields.add(TicketPatchField.kmGetsOn);
    }

    public Double getKmGetsOff() {
        return kmGetsOff;
    }

    public void setKmGetsOff(Double kmGetsOff) {
        this.kmGetsOff = kmGetsOff;
        updatedFields.add(TicketPatchField.kmGetsOff);
    }
}
