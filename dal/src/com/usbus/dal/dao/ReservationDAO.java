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

        Query<Reservation> query = ds.createQuery(Reservation.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("active").equal(status));
        List<Reservation> resultList = query.asList();
        List<Reservation> auxList = new ArrayList<>(resultList);
        if(auxList.isEmpty()) {
            return null;
        } else {
            for (Reservation res : auxList) {
                if(res.getClientId() != clientId){
                    resultList.remove(res);
                }
            }
            if(resultList.isEmpty()){
                return null;
            }
            else {
                return resultList.subList(offset, (offset + limit));
            }
        }
    }

    public List<Reservation> getByJourney(long tenantId, Long journeyId, int offset, int limit){
        if (!(tenantId > 0) || (journeyId == null) || (journeyId < 1) || offset < 0 || limit < 0) {
            return null;
        }

        Query<Reservation> query = ds.createQuery(Reservation.class);
        query.criteria("tenantId").equal(tenantId);
        List<Reservation> resultList = query.asList();
        List<Reservation> auxList = new ArrayList<>(resultList);

        if(auxList.isEmpty()) {
            return null;
        } else {
            for (Reservation res : auxList) {
                if(res.getJourneyId() != journeyId){
                    resultList.remove(res);
                }
            }
            if(resultList.isEmpty()){
                return null;
            }
            else {
                return resultList.subList(offset, (offset + limit));
            }
        }
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
}
