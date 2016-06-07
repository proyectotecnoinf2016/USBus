package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.TicketLocal;
import com.usbus.bll.administration.interfaces.TicketRemote;
import com.usbus.dal.model.Ticket;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@Stateless(name = "TicketEJB")
public class TicketBean implements TicketLocal, TicketRemote{
    private final TicketBean dao = new TicketBean();
    public TicketBean(){}

    @Override
    public ObjectId persist(Ticket ticket) {
        return dao.persist(ticket);
    }

    @Override
    public Ticket getById(ObjectId oid){
        return dao.getById(oid);
    }
}
