package com.usbus.services.administration;

import com.usbus.bll.administration.beans.TicketBean;
import com.usbus.commons.auxiliaryClasses.TicketConfirmation;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.commons.exceptions.TicketException;
import com.usbus.dal.model.Ticket;
import com.usbus.services.auth.Secured;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@Path("{tenantId}/ticket")
public class TicketService {
    TicketBean ejb = new TicketBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT, Rol.ASSISTANT})
    public Response createTicket(Ticket ticketAux) {
        try {
            Ticket ticket = new Ticket(ticketAux);
            String ticketId = ejb.persist(ticket);
            Ticket ticketAux2 = ejb.getById(ticketId);

            if (ticketId != null) {
                return Response.ok(ticketAux2).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }catch (TicketException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("{ticketId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT, Rol.ASSISTANT})
    public Response getTicket(@PathParam("tenantId") Long tenantId, @PathParam("ticketId") Long ticketId) {
        Ticket ticketAux = ejb.getByLocalId(tenantId, ticketId);
        if (ticketAux == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(ticketAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT, Rol.ASSISTANT})
    public Response getTickets(@PathParam("tenantId") Long tenantId, @QueryParam("username") String username, @QueryParam("status") TicketStatus ticketStatus, @QueryParam("offset") int offset, @QueryParam("limit") int limit, @QueryParam("journeyId") long journeyId) {
        if (journeyId > 0) {
            List<Ticket> ticketList = ejb.getByJourneyId(tenantId, journeyId, offset, limit);
            if (ticketList == null) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.ok(ticketList).build();
        }
        if (!(username.isEmpty()) && !(ticketStatus == null)) {
            List<Ticket> ticketList = ejb.TicketsByBuyerAndStatus(tenantId,username, ticketStatus, offset, limit);
            if (ticketList == null) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.ok(ticketList).build();
        }
        return Response.status(Response.Status.EXPECTATION_FAILED).build();

    }

    @PUT
    @Path("{ticketId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT, Rol.ASSISTANT})
    public Response setPassenger(@PathParam("tenantId") Long tenantId, @PathParam("ticketId") long ticketId, TicketConfirmation ticketConfirmation) {
        try {
            Ticket ticket = ejb.confirmTicket(ticketConfirmation);
            if (ticket == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            return Response.ok(ticket).build();
        } catch (TicketException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }

    }
}
