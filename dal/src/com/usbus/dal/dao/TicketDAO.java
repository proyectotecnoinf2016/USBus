package com.usbus.dal.dao;

import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class TicketDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public TicketDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Ticket ticket) {
        return dao.persist(ticket);
    }

    public long countAll() {
        return dao.count(Ticket.class);
    }

    public long countTenant(long tenantId) {
        Query<Ticket> query = ds.createQuery(Ticket.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Ticket getById(String id) {
        return dao.get(Ticket.class, id);
    }

    public Ticket getByLocalId(long tenantId, Long id) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Ticket> query = ds.createQuery(Ticket.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public List<Ticket> TicketsByBuyerAndStatus(Long tenantId,String username, TicketStatus status, int offset, int limit) {
        if ((!(tenantId>0))|| (username == null) || (status == null)) {
            return null;
        }
        Query<User> queryUser = ds.createQuery(User.class);

        queryUser.and(queryUser.criteria("username").equal(username),
                queryUser.criteria("tenantId").equal(tenantId));
        User user =  queryUser.get();

        Query<Ticket> query = ds.createQuery(Ticket.class);
        query.and(query.criteria("passenger").equal(user), query.criteria("status").equal(status));
        return query.offset(offset).limit(limit).asList();
    }

    public List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit, TicketStatus ticketStatus) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }


        Query<Journey> queryJ = ds.createQuery(Journey.class);

        queryJ.and(queryJ.criteria("id").equal(id),
                queryJ.criteria("tenantId").equal(tenantId));

        Journey journey = queryJ.get();

        Query<Ticket> query = ds.createQuery(Ticket.class);

        query.and(query.criteria("journey").equal(journey),
                query.criteria("tenantId").equal(tenantId),
                query.criteria("status").equal(ticketStatus));

        return query.offset(offset).limit(limit).asList();
    }

    public Ticket setPassenger(long tenantId, Long ticketId, User passenger) {
        if (!(tenantId > 0) || (passenger == null) || !(ticketId > 0)) {
            return null;
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);

            query.and(query.criteria("id").equal(ticketId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Ticket> updateOp = ds.createUpdateOperations(Ticket.class).set("passenger", passenger);
            ds.update(query, updateOp);

            return getByLocalId(tenantId, ticketId);
        }
    }

    public void remove(String id) {
        dao.remove(Ticket.class, id);
    }

    public void clean() {
        ds.delete(ds.createQuery(Ticket.class));
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true, "id");
            Ticket ticket = query.get();
            if (ticket == null) {
                return new Long(1);
            }
            return ticket.getId() + 1;
        }
    }

    public void setInactive(Long tenantId, Long ticketId) {
        if (!(tenantId > 0) || (ticketId == null)) {
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);

            query.and(query.criteria("id").equal(ticketId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Ticket> updateOp = ds.createUpdateOperations(Ticket.class).set("status", TicketStatus.CANCELED);
            ds.update(query, updateOp);
        }
    }

    public List<Ticket> /*getByGetsOff*/getByJourney(long tenantId/*, String getOffStopName*/, Journey journey){
        if (tenantId > 0 /*&& !getOffStopName.equals(null)*/ && !journey.equals(null)) {
            Query<Ticket> query = ds.createQuery(Ticket.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("journey").equal(journey),
                    query.criteria("closed").equal(true), query.criteria("status").equal(TicketStatus.CONFIRMED));
            return  query.asList();
        } else {
            return null;
        }
    }

    public List<Integer> getFreeSeatsForRouteStop(long tenantId, Double routeStopKmA, Double routeStopKmB, Long journeyId){
        if (tenantId > 0 && routeStopKmA != null && routeStopKmB != null && journeyId != null) {
//GET TICKETS OF A JOURNEY
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("id").equal(journeyId));
            Journey journey = query.get();

            List<Ticket> ticketList = getByJourney(tenantId, journey);
            List<Ticket> auxTicketList = new ArrayList<>(ticketList);
//GET TICKETS OF A JOURNEY

//GET RESERVATIONS BY JOURNEY
            Query<Reservation> rquery = ds.createQuery(Reservation.class);
            rquery.and(rquery.criteria("tenantId").equal(tenantId), rquery.criteria("journeyId").equal(journeyId),
                    rquery.criteria("active").equal(true));
            List<Reservation> reservations = rquery.asList();
            List<Reservation> auxReservationList = new ArrayList<>(reservations);
//GET RESERVATIONS BY JOURNEY

//GET RESERVATIONS KM...
            List<RouteStop> routeList = journey.getService().getRoute().getBusStops();
            List<RouteStop> auxRouteStopList = new ArrayList<>(routeList);
            Map<String,Double> map = new HashMap<>();

            for(RouteStop auxRouteStop : auxRouteStopList){
                map.put(auxRouteStop.getBusStop(),auxRouteStop.getKm());
            }
//GET RESERVATIONS KM...

            List<Integer> returnSeatNumberList = new ArrayList<>();
            int numberOfSeats = journey.getBus().getSeats();
            for (Integer i = 1; i <= numberOfSeats; i++) { returnSeatNumberList.add(i); }

            if (!auxTicketList.isEmpty()) {
//REMOVE OCCUPIED SEATS
                for (Ticket auxTicket : auxTicketList) {
                    if( returnSeatNumberList.contains(auxTicket.getSeat())
                        && ( (routeStopKmA >= auxTicket.getKmGetsOn() && routeStopKmA < auxTicket.getKmGetsOff())
                            || (routeStopKmB > auxTicket.getKmGetsOn() && routeStopKmB <= auxTicket.getKmGetsOff())
                            || (routeStopKmA <= auxTicket.getKmGetsOn() && routeStopKmB >= auxTicket.getKmGetsOff()) ) ){
                        returnSeatNumberList.remove(auxTicket.getSeat());
                    }
                }
                for (Reservation auxReservation : auxReservationList) {
                    if( returnSeatNumberList.contains(auxReservation.getSeat())
                            && ( (routeStopKmA >= map.get(auxReservation.getGetsOn()) && routeStopKmA < map.get(auxReservation.getGetsOff()))
                            || (routeStopKmB > map.get(auxReservation.getGetsOn()) && routeStopKmB <= map.get(auxReservation.getGetsOff()))
                            || (routeStopKmA <= map.get(auxReservation.getGetsOn()) && routeStopKmB >= map.get(auxReservation.getGetsOff())) ) ){
                        returnSeatNumberList.remove(auxReservation.getSeat());
                    }
                }
//REMOVE OCCUPIED SEATS
                return returnSeatNumberList;
            }
            else {
                return null;
            }
        } else {
            return null;
        }
    }
}
