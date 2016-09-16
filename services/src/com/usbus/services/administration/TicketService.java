package com.usbus.services.administration;

import com.itextpdf.text.DocumentException;
import com.usbus.bll.administration.beans.TicketBean;
import com.usbus.commons.auxiliaryClasses.TicketConfirmation;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.commons.exceptions.TicketException;
import com.usbus.dal.model.Ticket;
import com.usbus.dal.model.TicketPatch;
import com.usbus.services.PATCH;
import com.usbus.services.auth.Secured;
import org.bson.types.ObjectId;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
    public Response getTickets(@PathParam("tenantId") Long tenantId, @QueryParam("username") String username,
                               @QueryParam("status") TicketStatus ticketStatus, @QueryParam("offset") int offset,
                               @QueryParam("limit") int limit, @QueryParam("journeyId") long journeyId,
                               @QueryParam("routeStopKmA") Double routeStopKmA,
                               @QueryParam("routeStopKmB") Double routeStopKmB,
                               @QueryParam("query") String query,
                               @QueryParam("routeStop") String routeStop,
                               @QueryParam("tenantName") String tenantName,
                               @QueryParam("ticketid")Long ticketid) throws IOException, DocumentException {

        List<Ticket> ticketList;
        switch (query.toUpperCase()) {
            case "JOURNEY":
                if (journeyId > 0) {
                    ticketList = ejb.getByJourneyId(tenantId, journeyId, offset, limit, ticketStatus);
                    if (ticketList == null) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                    }
                    return Response.ok(ticketList).build();
                }
            case "BUYERANDSTATUS":
                if (!(username.isEmpty()) && !(ticketStatus == null)) {
                    ticketList = ejb.TicketsByBuyerAndStatus(tenantId, username, ticketStatus, offset, limit);
                    if (ticketList == null) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                    }
                    return Response.ok(ticketList).build();
                }
            case "FREESEATS":
                if (journeyId > 0) {
                    List<Integer> ticketFreeNoList = ejb.getFreeSeatsForRouteStop(tenantId, routeStopKmA, routeStopKmB, journeyId);
                    if (ticketFreeNoList == null) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                    }
                    return Response.ok(ticketFreeNoList).build();
                }
            case "OCCUPIEDSEATS":
                if (journeyId > 0 && routeStopKmA != null && routeStopKmB != null) {
                    JSONObject occupiedSeats = ejb.getOccupiedSeatsForSubRoute(tenantId, routeStopKmA, routeStopKmB, journeyId);
                    if (occupiedSeats == null || occupiedSeats.isEmpty()) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                    }
                    return Response.ok(occupiedSeats).build();
                }
            case "ROUTESTOP":
                if(journeyId > 0 && routeStop != null && !routeStop.isEmpty()) {
                    List<Ticket> updatedTickets = ejb.updateTicketsStatus(tenantId, journeyId, routeStop);
                    if (updatedTickets == null) {
                        return Response.status(Response.Status.NOT_MODIFIED).build();
                    }
                    return Response.ok(updatedTickets).build();
                }
            case "RECEIPT":
                if (journeyId > 0) {
                    String ticketAux = ejb.createPDF(tenantName,ticketid);
                    if (ticketAux.equals(null) || ticketAux.isEmpty()) {
                        return Response.status(Response.Status.NO_CONTENT).build();
                    }
                    return Response.ok(ticketAux).build();
                }
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

    @DELETE
    @Path("{ticketId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.ASSISTANT})
    public Response removeTicket(@PathParam("tenantId")Long tenantId, @PathParam("ticketId") Long ticketId){
        try {
            ejb.setInactive(tenantId, ticketId);
            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PATCH
    @Path("{ticketId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.ASSISTANT, Rol.CLIENT})
    public Response updateJourney(@PathParam("tenantId") Long tenantId,
                                  @PathParam("ticketId") Long ticketId,
                                  TicketPatch patch) throws TicketException {

        Ticket ticketAux = ejb.getByLocalId(tenantId, ticketId);

        for (TicketPatch.TicketPatchField updatedField : patch.getUpdatedFields()) {
            switch (updatedField) {
                case id:
                    ticketAux.setId(patch.getId());
                    break;

                case hasCombination:
                    ticketAux.setHasCombination(patch.getHasCombination());
                    break;

                case combination:
                    ticketAux.setCombination(patch.getCombination());
                    break;

                case combinationId:
                    ticketAux.setCombinationId(patch.getCombinationId());
                    break;

                case amount:
                    ticketAux.setAmount(patch.getAmount());
                    break;

                case passenger:
                    ticketAux.setPassenger(patch.getPassenger());
                    break;

                case passengerName:
                    ticketAux.setPassengerName(patch.getPassengerName());
                    break;

                case seller:
                    ticketAux.setSeller(patch.getSeller());
                    break;

                case sellerName:
                    ticketAux.setSellerName(patch.getSellerName());
                    break;

                case closed:
                    ticketAux.setClosed(patch.getClosed());
                    break;

                case status:
                    ticketAux.setStatus(patch.getStatus());
                    break;

                case paymentToken:
                    ticketAux.setPaymentToken(patch.getPaymentToken());
                    break;

                case journey:
                    ticketAux.setJourney(patch.getJourney());
                    break;

                case journeyId:
                    ticketAux.setJourneyId(patch.getJourneyId());
                    break;

                case seat:
                    ticketAux.setSeat(patch.getSeat());
                    break;

                case getsOn:
                    ticketAux.setGetsOn(patch.getGetsOn());
                    break;

                case getOnStopName:
                    ticketAux.setGetOnStopName(patch.getGetOnStopName());
                    break;

                case getsOff:
                    ticketAux.setGetsOff(patch.getGetsOff());
                    break;

                case getOffStopName:
                    ticketAux.setGetOffStopName(patch.getGetOffStopName());
                    break;

                case route:
                    ticketAux.setRoute(patch.getRoute());
                    break;

                case routeId:
                    ticketAux.setRouteId(patch.getRouteId());
                    break;

                case dueDate:
                    ticketAux.setDueDate(patch.getDueDate());
                    break;

                case branchId:
                    ticketAux.setBranchId(patch.getBranchId());
                    break;

                case windowId:
                    ticketAux.setWindowId(patch.getWindowId());
                    break;

                case kmGetsOn:
                    ticketAux.setKmGetsOn(patch.getKmGetsOn());
                    break;

                case kmGetsOff:
                    ticketAux.setKmGetsOff(patch.getKmGetsOff());

            }
        }
        String oid = ejb.persist(ticketAux);
        if (oid == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }
}
