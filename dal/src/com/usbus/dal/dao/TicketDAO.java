package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Service;
import com.usbus.dal.model.Ticket;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class TicketDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public TicketDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Ticket ticket) {
        return dao.persist(ticket);
    }

    public long countAll() {
        return dao.count(Ticket.class);
    }

    public long countTenant(long tenantId) {
        Query<Ticket> query = ds.createQuery(Ticket.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Ticket getById(ObjectId id) {
        return dao.get(Ticket.class, id);
    }

//    public Ticket getByService(long tenantId, Service service){
//        if (!(tenantId > 0) || (service == null)) {
//            return null;
//        }
//
//        Query<Ticket> query = ds.createQuery(Ticket.class);
//
//        query.and(query.criteria("service").equal(service),
//                query.criteria("tenantId").equal(tenantId));
//
//        return query.get();
//    }

    public void remove(ObjectId id) {
        dao.remove(Ticket.class, id);
    }
}
