package com.usbus.bll.administration.interfaces;

import com.itextpdf.text.DocumentException;
import com.usbus.commons.auxiliaryClasses.TicketConfirmation;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.commons.exceptions.TicketException;
import com.usbus.dal.model.Ticket;
import org.bson.types.ObjectId;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.ejb.Local;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@Local
public interface TicketLocal {
    String persist(Ticket ticket) throws TicketException;
    Ticket confirmTicket(TicketConfirmation ticketConfirmation) throws TicketException;
    Ticket getById(String oid);
    Ticket getByLocalId(long tenantId, Long id);
    List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit, TicketStatus status);
    List<Ticket> TicketsByBuyerAndStatus(Long tenantId,String username, TicketStatus status, int offset, int limit);
    Ticket setPassenger(long tenantId, Long ticketId, String passenger);
    List<Integer> getFreeSeatsForRouteStop(long tenantId, Double routeStopKmA, Double routeStopKmB, Long journeyId);
    JSONObject getOccupiedSeatsForSubRoute(long tenantId, Double routeStopKmA, Double routeStopKmB, Long journeyId);
    List<Ticket> updateTicketsStatus(long tenantId, Long journeyId, String routeStop);
    String createPDF(String tenantName, Long ticketid) throws IOException, DocumentException;
}
