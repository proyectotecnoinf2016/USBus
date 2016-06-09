package com.usbus.dal.model;

import org.mongodb.morphia.annotations.Entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "openTickets",noClassnameStored = false)
public class OpenTicket extends Ticket {
    private Route route;
    private Date dueDate;
    private Boolean closed;

    public OpenTicket(){
    }

    public OpenTicket(long tenantId, Long id, Date emissionDate, Boolean hasCombination, Service combination, Double amount, User passenger, HumanResource seller, Route route, Date dueDate, Boolean closed) {
        super(tenantId, id, emissionDate, hasCombination, combination, amount, passenger, seller);
        this.route = route;
        this.dueDate = dueDate;
        this.closed = closed;
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
}
