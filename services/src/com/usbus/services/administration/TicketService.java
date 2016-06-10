package com.usbus.services.administration;

import com.usbus.bll.administration.beans.TicketBean;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.enums.TicketStatus;
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
    public Response createTicket(Ticket ticketAux){
        ObjectId oid = ejb.persist(ticketAux);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @GET
    @Path("{ticketId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getTicket(@PathParam("tenantId")Long tenantId, @PathParam("ticketId") Long ticketId){
        Ticket ticketAux = ejb.getByLocalId(tenantId, ticketId);
        if (ticketAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(ticketAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getTicketsByBuyerAndStatus(@QueryParam("username")String username, @QueryParam("status") TicketStatus ticketStatus, @QueryParam("offset") int offset, @QueryParam("limit") int limit){
        List<Ticket> ticketList = ejb.TicketsByBuyerAndStatus(username, ticketStatus, offset, limit);
        if (ticketList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(ticketList).build();
    }

    @PUT
    @Path("{ticketId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response setPassenger(@PathParam("tenantId")Long tenantId, @PathParam("ticketId")long ticketId, @QueryParam("username") String username){
        Ticket ticket = ejb.setPassenger(tenantId, ticketId, username);
        if(ticket == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ticket).build();
    }
}
