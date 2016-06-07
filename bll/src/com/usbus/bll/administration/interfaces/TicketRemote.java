package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Ticket;
import org.bson.types.ObjectId;

import javax.ejb.Remote;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface TicketRemote {
    ObjectId persist(Ticket ticket);
    Ticket getById(ObjectId oid);
}
