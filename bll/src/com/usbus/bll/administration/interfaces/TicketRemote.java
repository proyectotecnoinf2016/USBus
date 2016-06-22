package com.usbus.bll.administration.interfaces;

import com.usbus.commons.auxiliaryClasses.TicketConfirmation;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.commons.exceptions.TicketException;
import com.usbus.dal.model.Ticket;
import org.bson.types.ObjectId;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface TicketRemote {
    ObjectId persist(Ticket ticket);
    Ticket getById(ObjectId oid);
    Ticket getByLocalId(long tenantId, Long id);
    List<Ticket> TicketsByBuyerAndStatus(String username, TicketStatus status, int offset, int limit);
    Ticket setPassenger(long tenantId, Long ticketId, String passenger);
    Ticket confirmTicket(TicketConfirmation ticketConfirmation) throws TicketException;

}
