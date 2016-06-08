package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.ClosedTicket;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<ClosedTicket> getClosedTicketsAfterDateByUser(long tenantId, Date date, User passenger, int offset, int limit){
        if (!(tenantId > 0) || (passenger == null) || (date == null)) {
            return null;
        }
        Query<ClosedTicket> query = ds.createQuery(ClosedTicket.class);

        query.and(query.criteria("passenger").equal(passenger),
                query.criteria("tenantId").equal(tenantId));
        List<ClosedTicket> closedTicketsList = query.offset(offset).limit(limit).asList();
        List<ClosedTicket> futureClosedTicketsList = new ArrayList<>();
        for (ClosedTicket ClosedTicketsAux : closedTicketsList) {
            Journey journey1 = ClosedTicketsAux.getJourney();
            if(journey1.getDate().compareTo(date) >= 0 ){
                futureClosedTicketsList.add(ClosedTicketsAux);
            }
        }
        return futureClosedTicketsList;
    }
}
