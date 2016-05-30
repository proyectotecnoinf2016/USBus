package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Service;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class ServiceDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public ServiceDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Service service) {
        return dao.persist(service);
    }

    public long countAll() {
        return dao.count(Service.class);
    }

    public long countTenant(long tenantId) {
        Query<Service> query = ds.createQuery(Service.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Service getById(ObjectId id) {
        return dao.get(Service.class, id);
    }

    public Service getByBranchId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Service> query = ds.createQuery(Service.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }
}
