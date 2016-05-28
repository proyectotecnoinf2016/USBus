package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Maintenance;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class MaintenanceDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public MaintenanceDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Maintenance maintenance) {
        return dao.persist(maintenance);
    }

    public long countAll() {
        return dao.count(Maintenance.class);
    }

    public long countTenant(long tenantId) {
        Query<Maintenance> query = ds.createQuery(Maintenance.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Maintenance getById(ObjectId id) {
        return dao.get(Maintenance.class, id);
    }

    public Maintenance getByJourneyId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Maintenance> query = ds.createQuery(Maintenance.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }
}
