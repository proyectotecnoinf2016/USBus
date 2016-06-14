package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Bus;
import com.usbus.dal.model.BusStop;
import com.usbus.dal.model.Route;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.CriteriaContainerImpl;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class BusStopDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public BusStopDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(BusStop busStop) {
        return dao.persist(busStop);
    }

    public long countAll() {
        return dao.count(BusStop.class);
    }

    public long countTenant(long tenantId) {
        Query<BusStop> query = ds.createQuery(BusStop.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public BusStop getById(ObjectId id) {
        return dao.get(BusStop.class, id);
    }

    public List<BusStop> getByTenant(long tenantId, int offset, int limit, String name) {
        if (!(tenantId > 0)) {
            return null;
        }
        Query<BusStop> query = ds.createQuery(BusStop.class);
        query.and(query.criteria("tenantId").equal(tenantId),query.criteria("active").equal(true));
        if (!(name == null) && !(name.isEmpty())) {
            query.and(query.criteria("name").containsIgnoreCase(name));
        }
        return query.offset(offset).limit(limit).asList();

    }

    public BusStop getByLocalId(long tenantId, Long id) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<BusStop> query = ds.createQuery(BusStop.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public BusStop getByName(long tenantId, String name) {
        if (!(tenantId > 0) || (name.isEmpty())) {
            return null;
        }

        Query<BusStop> query = ds.createQuery(BusStop.class);

        query.and(query.criteria("name").equal(name),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public void remove(ObjectId id) {
        dao.remove(BusStop.class, id);
    }

    public void setInactive(long tenantId, Long id) {
        if (!(tenantId > 0) || !(id > 0)) {
        } else {
            Query<BusStop> query = ds.createQuery(BusStop.class);

            query.and(query.criteria("id").equal(id),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<BusStop> updateOp = ds.createUpdateOperations(BusStop.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void setActive(long tenantId, Long id) {
        if (!(tenantId > 0) || !(id > 0)) {
        } else {
            Query<BusStop> query = ds.createQuery(BusStop.class);

            query.and(query.criteria("id").equal(id),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<BusStop> updateOp = ds.createUpdateOperations(BusStop.class).set("active", true);
            ds.update(query, updateOp);
        }
    }

    public void clean() {
        ds.delete(ds.createQuery(BusStop.class));
    }

    public Long getNextId(long tenantId) {
        if (!(tenantId > 0)) {
            return null;
        } else {
            Query<BusStop> query = ds.createQuery(BusStop.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            BusStop busStop = query.get();
            if (busStop==null){
                return new Long(1);
            }
            return busStop.getId() + 1;

        }
    }

}
