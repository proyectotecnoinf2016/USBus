package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Tenant;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Created by jpmartinez on 08/05/16.
 */
public class TenantDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public TenantDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Tenant tenant) {
        return dao.persist(tenant);
    }

    public long countAll() {
        return dao.count(Tenant.class);
    }

    public Tenant getById(ObjectId id) {
        return dao.get(Tenant.class, id);
    }

    public void remove(ObjectId id){
        dao.remove(Tenant.class, id);
    }
    public Tenant getByName(String name) {
        if (name.isEmpty()) {
            return null;
        }

        Query<Tenant> query = ds.createQuery(Tenant.class);

        query.limit(1).criteria("name").equal(name);

        return query.get();

    }
    public Tenant getByLocalId(Long id){

        if (!(id>0)) {
            return null;
        }

        Query<Tenant> query = ds.createQuery(Tenant.class);

        query.limit(1).criteria("tenantId").equal(id);

        return query.get();

    }
    public void clean(){
        ds.delete(ds.createQuery(Tenant.class));
    }
}
