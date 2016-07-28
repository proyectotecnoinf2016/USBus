package com.usbus.dal.dao;

import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.Reservation;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class ReservationDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public ReservationDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Reservation reservation) {
        return dao.persist(reservation);
    }

    public long countAll() {
        return dao.count(Reservation.class);
    }

    public long countTenant(long tenantId) {
        Query<Reservation> query = ds.createQuery(Reservation.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Reservation getById(String id) {
        return dao.get(Reservation.class, id);
    }

    public Reservation getByLocalId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }
        updateReservationsStatus(tenantId, null, null, id);

        Query<Reservation> query = ds.createQuery(Reservation.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

//    //NOT TESTED
//    //CON EL JOURNEY (OBJETO!!!!)
//    public List<Reservation> getByUserNameAndStatus(long tenantId, String username, Boolean status, int offset, int limit){
//        if (!(tenantId > 0) || (username == null) || (username.isEmpty()) || (status == null) || offset < 0 || limit < 0) {
//            return null;
//        }
//
//        Query<Reservation> query = ds.createQuery(Reservation.class);
//        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("active").equal(status));
//        List<Reservation> resultList = query.asList();
//        List<Reservation> auxList = new ArrayList<>(resultList);
//        if(auxList.isEmpty()) {
//            return null;
//        } else {
//            for (Reservation res : auxList) {
//                User user = res.getPassenger();
//                if(user.getUsername() != username){
//                    resultList.remove(res);
//                }
//            }
//            if(resultList.isEmpty()){
//                return null;
//            }
//            else {
//                return resultList.subList(offset, (offset + limit));
//            }
//        }
//    }

//    //CON EL JOURNEY (OBJETO!!!!)
//    public List<Reservation> getByJourney(long tenantId, Long journeyId, int offset, int limit){
//        if (!(tenantId > 0) || (journeyId == null) || (journeyId < 1) || offset < 0 || limit < 0) {
//            return null;
//        }
//
//        Query<Reservation> query = ds.createQuery(Reservation.class);
//        query.criteria("tenantId").equal(tenantId);
//        List<Reservation> resultList = query.asList();
//        List<Reservation> auxList = new ArrayList<>(resultList);
//
//        if(auxList.isEmpty()) {
//            return null;
//        } else {
//            for (Reservation res : auxList) {
//                Journey journey = res.getJourney();
//                if(journey.getId() != journeyId){
//                    resultList.remove(res);
//                }
//            }
//            if(resultList.isEmpty()){
//                return null;
//            }
//            else {
//                return resultList.subList(offset, (offset + limit));
//            }
//        }
//    }

    public List<Reservation> getByUserNameAndStatus(long tenantId, String clientId, Boolean status, int offset, int limit){
        if (!(tenantId > 0) || (clientId == null) || (clientId.isEmpty()) || (status == null) || offset < 0 || limit < 0) {
            return null;
        }
        updateReservationsStatus(tenantId, null, clientId, null);

        Query<Reservation> query = ds.createQuery(Reservation.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("active").equal(status),
                query.criteria("clientId").equal(clientId));

        return query.offset(offset).limit(limit).asList();

    }

    public List<Reservation> getByJourney(long tenantId, Long journeyId, int offset, int limit, boolean active){
        if ((tenantId < 1) || (journeyId == null) || (journeyId < 1) || offset < 0 || limit < 0) {
            return null;
        }
        updateReservationsStatus(tenantId, journeyId, null, null);

        Query<Reservation> query = ds.createQuery(Reservation.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("journeyId").equal(journeyId), query.criteria("active").equal(active));
        List<Reservation> reservations = query.offset(offset).limit(limit).asList();

        return reservations;
    }

    public void remove(String id) {
        dao.remove(Reservation.class, id);
    }

    public void setInactive(long tenantId, Long reservationId) {
        if (!(tenantId > 0) || (reservationId == null)) {
        } else {
            Query<Reservation> query = ds.createQuery(Reservation.class);

            query.and(query.criteria("id").equal(reservationId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Reservation> updateOp = ds.createUpdateOperations(Reservation.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Reservation> query = ds.createQuery(Reservation.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            Reservation reservation = query.get();
            if (reservation==null){
                return new Long(1);
            }
            return reservation.getId() + 1;
        }
    }

    private void updateReservationsStatus (Long tenantId, Long journeyId, String clientId, Long reservationId) {
        if (tenantId == null || !(tenantId > 0)) {
        } else {
            Date now = new Date();

            if(reservationId != null && reservationId > 0) {
                Query<Reservation> reservationQuery = ds.createQuery(Reservation.class);
                reservationQuery.and(reservationQuery.criteria("id").equal(reservationId),
                        reservationQuery.criteria("tenantId").equal(tenantId));
                Reservation reservation = reservationQuery.get();
                if (reservation.getDueDate().before(now)) {
                    setInactive(tenantId, reservation.getId());
                }
            }

            if (journeyId != null && journeyId > 0) {
                Query<Reservation> reservationsQuery = ds.createQuery(Reservation.class);
                reservationsQuery.and(reservationsQuery.criteria("active").equal(true),
                        reservationsQuery.criteria("journeyId").equal(journeyId),
                        reservationsQuery.criteria("tenantId").equal(tenantId));
                List<Reservation> reservations = reservationsQuery.asList();

                for (Reservation r : reservations) {
                    Date dueDate = r.getDueDate();
                    if (dueDate.before(now)) {
                        setInactive(tenantId, r.getId());
                    }
                }
            }

            if (clientId != null && !clientId.isEmpty()) {
                Query<Reservation> reservationsQuery = ds.createQuery(Reservation.class);
                reservationsQuery.and(reservationsQuery.criteria("active").equal(true),
                        reservationsQuery.criteria("clientId").equal(clientId),
                        reservationsQuery.criteria("tenantId").equal(tenantId));
                List<Reservation> reservations = reservationsQuery.asList();

                for (Reservation r : reservations) {
                    Date dueDate = r.getDueDate();
                    if (dueDate.before(now)) {
                        setInactive(tenantId, r.getId());
                    }
                }
            }
        }
    }
}
