package com.usbus.services.administration;

import com.usbus.bll.administration.beans.ReservationBean;
import com.usbus.commons.auxiliaryClasses.ServicePOST;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.exceptions.ServiceException;
import com.usbus.dal.model.Reservation;
import com.usbus.services.auth.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 06/07/2016.
 */
@Path("{tenantId}/reservation")
public class ReservationService {
    ReservationBean ejb = new ReservationBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
            public Response createReservation(Reservation reservation) {
        String oid = ejb.persist(reservation);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @PUT
    @Path("{reservationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.ASSISTANT,Rol.CASHIER})
    public Response updateReservation( @PathParam("tenantId")Long tenantId,
                                       @PathParam("reservationId")Long reservationId,
                                       Reservation reservation){
            Reservation reservationAux = ejb.getByLocalId(tenantId, reservationId);
        reservation.set_id(reservationAux.get_id());
            String oid = ejb.persist(reservation);
            if (oid==null){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            return Response.ok(ejb.getById(oid)).build();
    }
    //-
    @GET
    @Path("{reservationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT,Rol.CASHIER})
    public Response getReservation(@PathParam("tenantId")Long tenantId,
                               @PathParam("reservationId") Long reservationId){

        Reservation reservationAux = ejb.getByLocalId(tenantId, reservationId);
        if (reservationAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(reservationAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT, Rol.DRIVER,Rol.CASHIER})
    public Response getReservationList(@PathParam("tenantId")Long tenantId,
                                   @QueryParam("query") String query,
                                   @QueryParam("journeyId") Long journeyId,
                                   @QueryParam("clientId") String clientId,
                                   @QueryParam("status") Boolean status,
                                   @QueryParam("offset") int offset,
                                   @QueryParam("limit") int limit) {

        List<Reservation> reservationList;
        switch (query.toUpperCase()) {
            case "USERANDSTATUS":
                reservationList = ejb.getByUserNameAndStatus(tenantId,clientId,status,offset,limit);
                if (reservationList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(reservationList).build();
            case "JOURNEY":
                reservationList = ejb.getByJourney(tenantId,journeyId,offset,limit, status);
                if (reservationList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(reservationList).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{reservationId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response removeReservation(@PathParam("tenantId")Long tenantId,
                                  @PathParam("reservationId")Long reservationId){
        try {
            ejb.setInactive(tenantId, reservationId);

            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
