package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Ticket;
import org.bson.types.ObjectId;

import javax.ejb.Local;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@Local
public interface TicketLocal {
    ObjectId persist(Ticket ticket);
    Ticket getById(ObjectId oid);
}
