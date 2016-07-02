package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.BusStop;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.Parcel;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class ParcelDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public ParcelDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Parcel parcel) {
        return dao.persist(parcel);
    }

    public long countAll() {
        return dao.count(Parcel.class);
    }

    public long countParcels(Long parcelId) {
        Query<Parcel> query = ds.createQuery(Parcel.class);
        query.criteria("id").equal(parcelId);
        return query.countAll();
    }

    public Parcel getById(String id) {
        return dao.get(Parcel.class, id);
    }

    public Parcel getByLocalId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Parcel> query = ds.createQuery(Parcel.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public List<Parcel> getByJourney(long tenantId, Long journeyId, int offset, int limit){
        if ((tenantId < 0) || (journeyId == null) || offset < 0 || limit < 0) {
            return null;
        }

        Query<Parcel> query = ds.createQuery(Parcel.class);
        query.criteria("tenantId").equal(tenantId);
        List<Parcel> resultList = query.asList();
        List<Parcel> auxList = new ArrayList<>(resultList);
        if(auxList.isEmpty()) {
            return null;
        } else {
            for (Parcel par : auxList) {
                Journey journey = par.getJourney();
                if(journey.getId() != journeyId){
                    resultList.remove(par);
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

    public List<Parcel> getOnDestination(long tenantId, Long destinationId, Boolean onDestination, int offset, int limit){
        if ((tenantId < 0) || (destinationId == null) || (onDestination == null) || offset < 0 || limit < 0) {
            return null;
        }

        Query<Parcel> query = ds.createQuery(Parcel.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("onDestination").equal(onDestination));
        List<Parcel> resultList = query.asList();
        List<Parcel> auxList = new ArrayList<>(resultList);
        if(auxList.isEmpty()) {
            return null;
        } else {
            for (Parcel par : auxList) {
                BusStop busStop = par.getDestination();
                if(busStop.getId() != destinationId){
                    resultList.remove(par);
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

    public List<Parcel> getOnDestinationNotDelivered(long tenantId, Long destinationId, Boolean onDestination, int offset, int limit){
        if ((tenantId < 0) || (destinationId == null) || (onDestination == null) || offset < 0 || limit < 0) {
            return null;
        }

        Query<Parcel> query = ds.createQuery(Parcel.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("onDestination").equal(onDestination),
                query.criteria("delivered").equal(false));
        List<Parcel> resultList = query.asList();
        List<Parcel> auxList = new ArrayList<>(resultList);
        if(auxList.isEmpty()) {
            return null;
        } else {
            for (Parcel par : auxList) {
                BusStop busStop = par.getDestination();
                if(busStop.getId() != destinationId){
                    resultList.remove(par);
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

    public List<Parcel> getByOrigin(long tenantId, Long originId, int offset, int limit){
        if ((tenantId < 0) || (originId == null) || offset < 0 || limit < 0) {
            return null;
        }

        Query<Parcel> query = ds.createQuery(Parcel.class);
        query.criteria("tenantId").equal(tenantId);
        List<Parcel> resultList = query.asList();
        List<Parcel> auxList = new ArrayList<>(resultList);
        if(auxList.isEmpty()) {
            return null;
        } else {
            for (Parcel par : auxList) {
                BusStop busStop = par.getDestination();
                if(busStop.getId() != originId){
                    resultList.remove(par);
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
        dao.remove(Parcel.class, id);
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Parcel> query = ds.createQuery(Parcel.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            Parcel parcel = query.get();
            if (parcel==null){
                return new Long(1);
            }
            return parcel.getId() + 1;
        }
    }
}
