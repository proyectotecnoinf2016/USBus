package com.usbus.services.administration;

import com.usbus.bll.administration.beans.JourneyBean;
import com.usbus.commons.enums.JourneyStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Journey;
import com.usbus.services.auth.Secured;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 05/06/2016.
 */
@Path("{tenantId}/journey")
//    @Secured(Rol.ADMINISTRATOR)
public class JourneyService {
    JourneyBean ejb = new JourneyBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response createJourney(Journey journey01){
        ObjectId oid = ejb.persist(journey01);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @PUT
    @Path("{journeyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.ASSISTANT})
    public Response updateJourney(@PathParam("tenantId")Long tenantId,
                                  @PathParam("journeyId")Long journeyId,
                                  Journey journey){
        Journey journeyAux = ejb.getByLocalId(tenantId, journeyId);
        journey.set_id(journeyAux.get_id());
        ObjectId oid = ejb.persist(journey);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @GET
    @Path("{journeyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
    public Response getJourney(@PathParam("tenantId")Long tenantId,
                               @PathParam("journeyId") Long journeyId){

        Journey journeyAux = ejb.getByLocalId(tenantId, journeyId);
        if (journeyAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(journeyAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
    public Response getJourneyList(@PathParam("tenantId")Long tenantId,
                                   @QueryParam("journeyStatus") JourneyStatus journeyStatus,
                                   @QueryParam("offset") int offset,
                                   @QueryParam("limit") int limit){

        List<Journey> journeyList = ejb.JourneysByTenantIdAndStatus(tenantId, journeyStatus, offset, limit);
        if (journeyList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(journeyList).build();
    }

    @GET
    @Path("{journeyId}/price")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
    public Response getJourneyPrice(@PathParam("tenantId")Long tenantId,
                                    @PathParam("journeyId")Long journeyId,
                                    @QueryParam("origin") String origin,
                                    @QueryParam("destination") String destination){
        Double price = ejb.getJourneyPrice(tenantId, journeyId, origin, destination);
        if (price == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } else {
            return Response.ok(price).build();
        }
    }

    @GET
    @Path("get/jdateAndStatus")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
    public Response journeysByTenantIdAndStatus(@PathParam("tenantId") long tenantId,
                                                @QueryParam ("date") Date date,
                                                @QueryParam ("status") JourneyStatus status,
                                                @QueryParam ("offset") int offset,
                                                @QueryParam ("limit") int limit){
        List<Journey> JList = ejb.getJourneysByTenantDateAndStatus(tenantId, date, status, offset, limit);

        if (JList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(JList).build();
    }

    @GET
    @Path("get/jdate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
    public Response journeysByTenantIdAndStatus(@PathParam("tenantId") long tenantId,
                                                @QueryParam ("date") Date date,
                                                @QueryParam ("offset") int offset,
                                                @QueryParam ("limit") int limit){
        List<Journey> JList = ejb.getJourneysByTenantAndDate(tenantId, date, offset, limit);

        if (JList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(JList).build();
    }

    @GET
    @Path("get/jbyDateOriginAndDestination")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
    public Response getJourneysByDateOriginAndDestination(@PathParam("tenantId") long tenantId,
                                                          @QueryParam ("date") Date date,
                                                          @QueryParam ("origin") String origin,
                                                          @QueryParam ("destination") String destination){
        List<Journey> JList = ejb.getJourneysByDateOriginAndDestination(tenantId, date, origin, destination);
        if (JList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(JList).build();
    }

    @DELETE
    @Path("{journeyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response removeJourney(@PathParam("tenantId")Long tenantId,
                                  @PathParam("journeyId")Long journeyId){
        try {
            ejb.setInactive(tenantId, journeyId); //POR AHORA SOLO IMPLEMENTAMOS UN BORRADO LÓGICO.
            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
