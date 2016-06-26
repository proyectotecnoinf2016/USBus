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
    String persist(Ticket ticket) throws TicketException;
    Ticket getById(String oid);
    Ticket getByLocalId(long tenantId, Long id);
    List<Ticket> TicketsByBuyerAndStatus(Long tenantId,String username, TicketStatus status, int offset, int limit);
    Ticket setPassenger(long tenantId, Long ticketId, String passenger);
    Ticket confirmTicket(TicketConfirmation ticketConfirmation) throws TicketException;
    List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit);

}
