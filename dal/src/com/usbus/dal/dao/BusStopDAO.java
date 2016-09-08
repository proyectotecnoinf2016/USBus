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

    public String persist(BusStop busStop) {
        if(busStop != null) {
            return dao.persist(busStop);
        } else {
            return null;
        }
    }

    public long countAll() {
        return dao.count(BusStop.class);
    }

    public long countTenant(long tenantId) {
        if(tenantId > 0) {
            Query<BusStop> query = ds.createQuery(BusStop.class);
            query.criteria("tenantId").equal(tenantId);
            return query.countAll();
        } else {
            return 0;
        }
    }

    public BusStop getById(String id) {
        if(id == null || id.isEmpty()) {
            return null;
        } else {
            return dao.get(BusStop.class, id);
        }
    }

    public List<BusStop> getByTenant(long tenantId, int offset, int limit, boolean status, String name) {
        if (tenantId < 0 || offset < 0 || limit < 0) {
            return null;
        } else {
            Query<BusStop> query = ds.createQuery(BusStop.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("active").equal(status));
            if (!(name == null) && !(name.isEmpty())) {
                query.and(query.criteria("name").containsIgnoreCase(name));
            }
            return query.offset(offset).limit(limit).asList();
        }
    }

    public BusStop getByLocalId(long tenantId, Long id) {
        if (tenantId < 0 || id == null) {
            return null;
        } else {
            Query<BusStop> query = ds.createQuery(BusStop.class);
            query.and(query.criteria("id").equal(id),
                    query.criteria("tenantId").equal(tenantId));
            return query.get();
        }
    }

    public BusStop getByName(long tenantId, String name) {
        if (tenantId < 0 || name == null || name.isEmpty()) {
            return null;
        } else {
            Query<BusStop> query = ds.createQuery(BusStop.class);
            query.and(query.criteria("name").equal(name),
                    query.criteria("tenantId").equal(tenantId));
            return query.get();
        }
    }

    public void remove(String id) {
        if (id != null && !id.isEmpty()) {
            dao.remove(BusStop.class, id);
        }
    }

    public void setInactive(long tenantId, Long id) {
        if (tenantId >= 0 && id != null && id >= 0) {
            Query<BusStop> query = ds.createQuery(BusStop.class);
            query.and(query.criteria("id").equal(id),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<BusStop> updateOp = ds.createUpdateOperations(BusStop.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void setActive(long tenantId, Long id) {
        if (tenantId >= 0 && id != null && id >= 0) {
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
        if (tenantId < 0) {
            return null;
        } else {
            Query<BusStop> query = ds.createQuery(BusStop.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            BusStop busStop = query.get();
            if (busStop==null){
                return 1L;
            }
            return busStop.getId() + 1;
        }
    }
}
