package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Reservation;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class ReservationDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public ReservationDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Reservation reservation) {
        return dao.persist(reservation);
    }

    public long countAll() {
        return dao.count(Reservation.class);
    }

    public long countTenant(long tenantId) {
        Query<Reservation> query = ds.createQuery(Reservation.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Reservation getById(String id) {
        return dao.get(Reservation.class, id);
    }

    public Reservation getByBranchId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Reservation> query = ds.createQuery(Reservation.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public void remove(String id) {
        dao.remove(Reservation.class, id);
    }

    public void setInactive(long tenantId, Long reservationId) {
        if (!(tenantId > 0) || (reservationId == null)) {
        } else {
            Query<Reservation> query = ds.createQuery(Reservation.class);

            query.and(query.criteria("id").equal(reservationId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Reservation> updateOp = ds.createUpdateOperations(Reservation.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Reservation> query = ds.createQuery(Reservation.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            Reservation reservation = query.get();
            if (reservation==null){
                return new Long(1);
            }
            return reservation.getId() + 1;
        }
    }
}
