package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Parcel;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

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

    public long countTenant(long tenantId) {
        Query<Parcel> query = ds.createQuery(Parcel.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Parcel getById(String id) {
        return dao.get(Parcel.class, id);
    }

    public Parcel getByJourneyId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Parcel> query = ds.createQuery(Parcel.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
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
