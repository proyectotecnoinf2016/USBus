package com.usbus.commons.auxiliaryClasses;

/**
 * Created by Lufasoch on 24/05/2016.
 */
public class Window {
    private Long id;
    private boolean tickets;
    private boolean parcels;
    private boolean active;

    public Window(){
    }

    public Window(Long id, boolean tickets, boolean parcels, boolean active) {
        this.id = id;
        this.tickets = tickets;
        this.parcels = parcels;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public boolean isTickets() {
        return tickets;
    }

    public void setTickets(boolean tickets) {
        this.tickets = tickets;
    }

    public boolean isParcels() {
        return parcels;
    }

    public void setParcels(boolean parcels) {
        this.parcels = parcels;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
