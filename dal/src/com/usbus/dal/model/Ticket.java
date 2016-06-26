package com.usbus.dal.model;

import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.BaseEntity;
import com.usbus.dal.dao.*;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "tickets", noClassnameStored = false)
@Indexes({
        @Index(fields = {@Field(value = "tenantId"), @Field(value = "id")}, options = @IndexOptions(name = "iTicketKey", unique = true))})
public class Ticket extends BaseEntity {
    private Long id;
    private Date emissionDate;
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


    public Ticket() {
    }

    public Ticket(long tenantId, Long id, Date emissionDate, Boolean hasCombination, Service combination, Long combinationId, Double amount, User passenger, String passengerName, HumanResource seller, String sellerName, Boolean closed, TicketStatus status, String paymentToken, Journey journey, Long journeyId, Integer seat, BusStop getsOn, String getOnStopName, BusStop getsOff, String getOffStopName, Route route, Long routeId, Date dueDate) {
        super(tenantId);
        this.id = id;
        this.emissionDate = emissionDate;
        this.hasCombination = hasCombination;
        if (!(combinationId == null)) {
            this.combination = new ServiceDAO().getByLocalId(tenantId, combinationId);
            this.combinationId = combinationId;
        }
        this.amount = amount;
        if (!(passengerName == null)) {
            this.passenger = new UserDAO().getByUsername(tenantId, passengerName);
            this.passengerName = passengerName;
        }
        if (!(sellerName == null)) {
            this.seller = new HumanResourceDAO().getByUsername(tenantId, sellerName);
            this.sellerName = sellerName;
        }
        this.closed = closed;
        this.status = status;
        this.paymentToken = paymentToken;
        if (!(journeyId == null)) {

            this.journey = new JourneyDAO().getByJourneyId(tenantId, journeyId);
            this.journeyId = journeyId;
        }

        this.seat = seat;
        if (!(getOnStopName == null)) {

            this.getsOn = new BusStopDAO().getByName(tenantId, getOnStopName);
            this.getOnStopName = getOnStopName;
        }
        if (!(getOffStopName == null)) {
            this.getsOff = new BusStopDAO().getByName(tenantId, getOffStopName);
            this.getOffStopName = getOffStopName;
        }
        if (!(routeId == null)) {
            this.route = new RouteDAO().getByLocalId(tenantId, routeId);
            this.routeId = routeId;
        }
        this.dueDate = dueDate;
    }

    public Ticket(Ticket ticket) {
        super(ticket.getTenantId());
        this.id = ticket.getId();
        this.emissionDate = ticket.getEmissionDate();
        this.hasCombination = ticket.getHasCombination();
        if (!(ticket.getCombinationId() == null)) {
            this.combination = new ServiceDAO().getByLocalId(ticket.getTenantId(), ticket.getCombinationId());
            this.combinationId = ticket.getCombinationId();
        }
        this.amount = ticket.getAmount();
        if (!(ticket.getPassengerName() == null)) {
            this.passenger = new UserDAO().getByUsername(ticket.getTenantId(), ticket.getPassengerName());
            this.passengerName = ticket.getPassengerName();
        }
        if (!(ticket.getSellerName() == null)) {
            this.seller = new HumanResourceDAO().getByUsername(ticket.getTenantId(), ticket.getSellerName());
            this.sellerName = ticket.getSellerName();
        }
        this.closed = ticket.getClosed();
        this.status = ticket.getStatus();
        this.paymentToken = ticket.getPaymentToken();
        if (!(ticket.getJourneyId() == null)) {
            this.journey = new JourneyDAO().getByJourneyId(ticket.getTenantId(), ticket.getJourneyId());
            this.journeyId = ticket.getJourneyId();
        }
        this.seat = ticket.getSeat();
        if (!(ticket.getGetOnStopName() == null)) {
            this.getsOn = new BusStopDAO().getByName(ticket.getTenantId(), ticket.getGetOnStopName());
            this.getOnStopName = ticket.getGetOnStopName();
        }
        if (!(ticket.getGetOffStopName() == null)) {
            this.getsOff = new BusStopDAO().getByName(ticket.getTenantId(), ticket.getGetOffStopName());
            this.getOffStopName = ticket.getGetOffStopName();
        }
        if (!(ticket.getRouteId() == null)) {
            this.route = new RouteDAO().getByLocalId(ticket.getTenantId(), ticket.getRouteId());
            this.routeId = ticket.getRouteId();
        }
        this.dueDate = ticket.getDueDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }

    public Boolean getHasCombination() {
        return hasCombination;
    }

    public void setHasCombination(Boolean hasCombination) {
        this.hasCombination = hasCombination;
    }

    public Service getCombination() {
        return combination;
    }

    public void setCombination(Service combination) {
        this.combination = combination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public HumanResource getSeller() {
        return seller;
    }

    public void setSeller(HumanResource seller) {
        this.seller = seller;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Long getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(Long combinationId) {
        this.combinationId = combinationId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

    public String getGetOnStopName() {
        return getOnStopName;
    }

    public void setGetOnStopName(String getOnStopName) {
        this.getOnStopName = getOnStopName;
    }

    public String getGetOffStopName() {
        return getOffStopName;
    }

    public void setGetOffStopName(String getOffStopName) {
        this.getOffStopName = getOffStopName;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
