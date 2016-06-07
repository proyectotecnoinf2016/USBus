package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.ClosedTicket;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

/**
 * Created by Lufasoch on 06/06/2016.
 */
public class ClosedTicketDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public ClosedTicketDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(ClosedTicket closedTicket) {
        return dao.persist(closedTicket);
    }
}
