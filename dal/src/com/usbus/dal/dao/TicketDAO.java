package com.usbus.dal.dao;

import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Journey;
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

    public String persist(Ticket ticket) {
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

    public Ticket getById(String id) {
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

    public List<Ticket> TicketsByBuyerAndStatus(Long tenantId,String username, TicketStatus status, int offset, int limit) {
        if ((!(tenantId>0))|| (username == null) || (status == null)) {
            return null;
        }
        Query<User> queryUser = ds.createQuery(User.class);

        queryUser.and(queryUser.criteria("username").equal(username),
                queryUser.criteria("tenantId").equal(tenantId));
        User user =  queryUser.get();

        Query<Ticket> query = ds.createQuery(Ticket.class);
        query.and(query.criteria("passenger").equal(user.get_id()), query.criteria("status").equal(status));
        return query.offset(offset).limit(limit).asList();
    }

    public List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }


        Query<Journey> queryJ = ds.createQuery(Journey.class);

        queryJ.and(queryJ.criteria("id").equal(id),
                queryJ.criteria("tenantId").equal(tenantId));

        Journey journey = queryJ.get();

        Query<Ticket> query = ds.createQuery(Ticket.class);

        query.and(query.criteria("journey").equal(journey.get_id()),
                query.criteria("tenantId").equal(tenantId));

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

    public void remove(String id) {
        dao.remove(Ticket.class, id);
    }

    public void clean() {
        ds.delete(ds.createQuery(Ticket.class));
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true, "id");
            Ticket ticket = query.get();
            if (ticket == null) {
                return new Long(1);
            }
            return ticket.getId() + 1;
        }
    }
}
