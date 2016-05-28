package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Branch;
import com.usbus.dal.model.Bus;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

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

    public ObjectId persist(Bus bus) {
        return dao.persist(bus);
    }

    public long countAll() {
        return dao.count(Bus.class);
    }

    public long countTenant(long tenantId) {
        Query<Bus> query = ds.createQuery(Bus.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Bus getById(ObjectId id) {
        return dao.get(Bus.class, id);
    }

    public Bus getByBusId(long tenantId, String id){
        if (!(tenantId > 0) || (id.isEmpty())) {
            return null;
        }

        Query<Bus> query = ds.createQuery(Bus.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }
}
