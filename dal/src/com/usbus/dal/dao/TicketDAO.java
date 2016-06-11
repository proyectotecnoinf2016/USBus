package com.usbus.dal.dao;

import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Service;
import com.usbus.dal.model.Ticket;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;

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

    public Ticket getByLocalId(long tenantId, Long id) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Ticket> query = ds.createQuery(Ticket.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public List<Ticket> TicketsByBuyerAndStatus(String username, TicketStatus status, int offset, int limit){
        if((username == null) || (status == null)){
            return null;
        }
        Query<Ticket> query = ds.createQuery(Ticket.class);
        query.and(query.criteria("passenger.username").equal(username), query.criteria("status").equal(status));
        return query.offset(offset).limit(limit).asList();
    }

    public Ticket setPassenger(long tenantId, Long ticketId, User passenger) {
        if (!(tenantId > 0) || (passenger == null) || !(ticketId > 0)) {
            return null;
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);

            query.and(query.criteria("id").equal(ticketId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Ticket> updateOp = ds.createUpdateOperations(Ticket.class).set("passenger", passenger);
            ds.update(query, updateOp);

            return getByLocalId(tenantId, ticketId);
        }
    }

    public void remove(ObjectId id) {
        dao.remove(Ticket.class, id);
    }

    public void clean(){
        ds.delete(ds.createQuery(Ticket.class));
    }
}
