package com.usbus.dal.dao;

import com.usbus.commons.enums.JourneyStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Journey;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class JourneyDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public JourneyDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Journey journey) {
        return dao.persist(journey);
    }

    public long countAll() {
        return dao.count(Journey.class);
    }

    public long countTenant(long tenantId) {
        Query<Journey> query = ds.createQuery(Journey.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Journey getById(ObjectId id) {
        return dao.get(Journey.class, id);
    }

    public Journey getByJourneyId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Journey> query = ds.createQuery(Journey.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public Journey getByLocalId(long tenantId, Long id) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Journey> query = ds.createQuery(Journey.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public void setInactive(long tenantId, Long journeyId) {
        if (!(tenantId > 0) || (journeyId == null)) {
        } else {
            Query<Journey> query = ds.createQuery(Journey.class);

            query.and(query.criteria("id").equal(journeyId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Journey> updateOp = ds.createUpdateOperations(Journey.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void setActive(long tenantId, Long journeyId) {
        if (!(tenantId > 0) || (journeyId == null)) {
        } else {
            Query<Journey> query = ds.createQuery(Journey.class);

            query.and(query.criteria("id").equal(journeyId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Journey> updateOp = ds.createUpdateOperations(Journey.class).set("active", true);
            ds.update(query, updateOp);
        }
    }

    public List<Journey> JourneysByTenantIdAndStatus(long tenantId, JourneyStatus status, int offset, int limit){
        if(!(tenantId > 0) || (status == null)){
            return null;
        }
        Query<Journey> query = ds.createQuery(Journey.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("status").equal(status));
        return query.offset(offset).limit(limit).asList();
    }

    public void remove(ObjectId id) {
        dao.remove(Journey.class, id);
    }

    public void clean(){
        ds.delete(ds.createQuery(Journey.class));
    }
}
