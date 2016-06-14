package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.TicketLocal;
import com.usbus.bll.administration.interfaces.TicketRemote;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.dao.TicketDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.Ticket;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@Stateless(name = "TicketEJB")
public class TicketBean implements TicketLocal, TicketRemote{
    private final TicketDAO dao = new TicketDAO();
    private final UserDAO udao = new UserDAO();

    public TicketBean(){}

    @Override
    public Long persist(Ticket ticket) {
        ObjectId ticketOID = null;
        try {
            ticket.setId(dao.countAll() + 1);
            ticketOID = dao.persist(ticket);
            return ticket.getId();
        } catch (Exception ex) {
            dao.remove(ticketOID);
            return null;
        }
    }

    @Override
    public Ticket setPassenger(long tenantId, Long ticketId, String passengerName) {
        User passenger = udao.getByUsername(tenantId, passengerName);
        return dao.setPassenger(tenantId, ticketId, passenger);
    }

    @Override
    public Ticket getById(ObjectId oid){
        return dao.getById(oid);
    }

    @Override
    public Ticket getByLocalId(long tenantId, Long id) {
        Ticket ticketAux = dao.getByLocalId(tenantId, id);
        return ticketAux;
    }

    @Override
    public List<Ticket> TicketsByBuyerAndStatus(String username, TicketStatus status, int offset, int limit) {
        return dao.TicketsByBuyerAndStatus(username, status, offset, limit);
    }

}
