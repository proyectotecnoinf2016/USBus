package com.usbus.dal.dao;

import com.usbus.commons.enums.BusStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Bus;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class BusDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public BusDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Bus bus) {
        return dao.persist(bus);
    }

    public Bus getById(String id) {
        return dao.get(Bus.class, id);
    }

    public long countAll() {
        return dao.count(Bus.class);
    }

    public long countTenant(long tenantId) {
        Query<Bus> query = ds.createQuery(Bus.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Bus getByBusId(long tenantId, String busId){
        if (!((tenantId > 0) || (busId != null && !busId.isEmpty()))) {
            return null;
        }
        Query<Bus> query = ds.createQuery(Bus.class);
        query.and(query.criteria("id").equal(busId), query.criteria("tenantId").equal(tenantId));
        return query.get();
    }

    public List<Bus> BusesByTenantIdAndStatus(long tenantId, BusStatus status, int offset, int limit){
        if(!(tenantId > 0) || (status == null)){
            return null;
        }
        Query<Bus> query = ds.createQuery(Bus.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("status").equal(status));
        return query.offset(offset).limit(limit).asList();
    }

    public Bus getByLocalId(long tenantId, String busId) {
        if (!((tenantId > 0) || (busId != null && !busId.isEmpty()))) {
            return null;
        }

        Query<Bus> query = ds.createQuery(Bus.class);

        query.and(query.criteria("id").equal(busId),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public void remove(String id) {
        dao.remove(Bus.class, id);
    }

    public void setInactive(long tenantId, String busId) {
        if (!((tenantId > 0) || (busId != null && !busId.isEmpty()))) {
        } else {
            Query<Bus> query = ds.createQuery(Bus.class);

            query.and(query.criteria("id").equal(busId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Bus> updateOp = ds.createUpdateOperations(Bus.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void setActive(long tenantId, String busId) {
        if (!((tenantId > 0) || (busId != null && !busId.isEmpty()))) {
        } else {
            Query<Bus> query = ds.createQuery(Bus.class);

            query.and(query.criteria("id").equal(busId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Bus> updateOp = ds.createUpdateOperations(Bus.class).set("active", true);
            ds.update(query, updateOp);
        }
    }

    public void clean(){
        ds.delete(ds.createQuery(Bus.class));
    }
}
