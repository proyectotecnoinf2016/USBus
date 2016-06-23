package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.TicketLocal;
import com.usbus.bll.administration.interfaces.TicketRemote;
import com.usbus.commons.auxiliaryClasses.TicketConfirmation;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.commons.exceptions.TicketException;
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
public class TicketBean implements TicketLocal, TicketRemote {
    private final TicketDAO dao = new TicketDAO();
    private final UserDAO udao = new UserDAO();

    public TicketBean() {
    }

    @Override
    public ObjectId persist(Ticket ticket) {
        ticket.setId(dao.getNextId(ticket.getTenantId()));
        return dao.persist(ticket);
    }

    @Override
    public Ticket setPassenger(long tenantId, Long ticketId, String passengerName) {
        User passenger = udao.getByUsername(tenantId, passengerName);
        return dao.setPassenger(tenantId, ticketId, passenger);
    }

    @Override
    public Ticket confirmTicket(TicketConfirmation ticketConfirmation) throws TicketException {
        Ticket ticket = dao.getByLocalId(ticketConfirmation.getTenantId(), ticketConfirmation.getId());
        if (!(ticket == null)) {
            switch (ticket.getStatus()) {
                case CANCELED:
                    throw new TicketException("El ticket se encuentra CANCELADO");
                case USED:
                    throw new TicketException("El ticket ya fue UTILIZADO");
            }
            ticket.setPassenger(udao.getByUsername(ticketConfirmation.getTenantId(), ticketConfirmation.getUsername()));
            ticket.setPaymentToken(ticketConfirmation.getPaymentToken());
            ticket.setStatus(ticketConfirmation.getStatus());
            if (dao.persist(ticket) != null) {
                return ticket;
            }

        }
        return null;
    }


    @Override
    public Ticket getById(ObjectId oid) {
        return dao.getById(oid);
    }

    @Override
    public Ticket getByLocalId(long tenantId, Long id) {
        Ticket ticketAux = dao.getByLocalId(tenantId, id);
        return ticketAux;
    }

    @Override
    public List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit) {
        return dao.getByJourneyId(tenantId, id, offset, limit);

    }

    @Override
    public List<Ticket> TicketsByBuyerAndStatus(String username, TicketStatus status, int offset, int limit) {
        return dao.TicketsByBuyerAndStatus(username, status, offset, limit);
    }

}
