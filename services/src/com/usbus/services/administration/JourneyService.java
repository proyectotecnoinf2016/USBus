package com.usbus.services.administration;

import com.usbus.bll.administration.beans.JourneyBean;
import com.usbus.commons.enums.JourneyStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.JourneyPatch;
import com.usbus.services.PATCH;
import com.usbus.services.auth.Secured;

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
        String oid = ejb.persist(journey01);
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
        String oid = ejb.persist(journey);
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
                                   @QueryParam("query") String query,
                                   @QueryParam("status") JourneyStatus journeyStatus,
                                   @QueryParam ("date") Date date,
                                   @QueryParam ("origin") String origin,
                                   @QueryParam ("destination") String destination,
                                   @QueryParam("offset") int offset,
                                   @QueryParam("limit") int limit){

        List<Journey> journeyList = null;
        switch (query.toUpperCase()){
            case "STATUS":
                journeyList = ejb.JourneysByTenantIdAndStatus(tenantId, journeyStatus, offset, limit);
                if (journeyList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(journeyList).build();
            case "DATE":
                journeyList = ejb.getJourneysByTenantAndDate(tenantId, date, offset, limit);

                if (journeyList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(journeyList).build();
            case "DATE_STATUS":
                journeyList = ejb.getJourneysByTenantDateAndStatus(tenantId, date, journeyStatus, offset, limit);

                if (journeyList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(journeyList).build();
            case "DATE_ORIGIN_DESTINATION":
                List<Journey> JList = ejb.getJourneysByDateOriginAndDestination(tenantId, date, origin, destination);
                if (JList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(JList).build();

        }
        return Response.status(Response.Status.BAD_REQUEST).build();

    }

//    @GET
//    @Path("get/jdate")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
//    public Response journeysByTenantIdAndStatus(@PathParam("tenantId") long tenantId,
//                                                @QueryParam ("date") Date date,
//                                                @QueryParam ("offset") int offset,

//                                                @QueryParam ("limit") int limit){
//        List<Journey> JList = ejb.getJourneysByTenantAndDate(tenantId, date, offset, limit);
//
//        if (JList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(JList).build();
//    }
//
//    @GET
//    @Path("get/jdateAndStatus")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
//    public Response journeysByTenantIdAndStatus(@PathParam("tenantId") long tenantId,
//                                                @QueryParam ("date") Date date,
//                                                @QueryParam ("status") JourneyStatus status,
//                                                @QueryParam ("offset") int offset,
//                                                @QueryParam ("limit") int limit){
//        List<Journey> JList = ejb.getJourneysByTenantDateAndStatus(tenantId, date, status, offset, limit);
//
//        if (JList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(JList).build();
//    }
//
//
//
//    @GET
//    @Path("get/jbyDateOriginAndDestination")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
//    public Response getJourneysByDateOriginAndDestination(@PathParam("tenantId") long tenantId,
//                                                          @QueryParam ("date") Date date,
//                                                          @QueryParam ("origin") String origin,
//                                                          @QueryParam ("destination") String destination){
//        List<Journey> JList = ejb.getJourneysByDateOriginAndDestination(tenantId, date, origin, destination);
//        if (JList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(JList).build();
//    }

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

    @PATCH
    @Path("{journeyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR,Rol.ASSISTANT, Rol.CLIENT})
    public Response updateJourney(@PathParam("tenantId")Long tenantId,
                                  @PathParam("journeyId")Long journeyId,
                                  JourneyPatch patch) {

        Journey journeyAux = ejb.getByLocalId(tenantId, journeyId);

        for (JourneyPatch.JourneyPatchField updatedField : patch.getUpdatedFields()) {
            switch (updatedField){
                case tenantId:
                    journeyAux.setTenantId(patch.getTenantId());
                    continue;
                case id:
                    journeyAux.setId(patch.getId());
                    continue;
                case service:
                    journeyAux.setService(patch.getService());
                    continue;
                case date:
                    journeyAux.setDate(patch.getDate());
                    continue;
                case bus:
                    journeyAux.setBus(patch.getBus());
                    continue;
                case thirdPartyBus:
                    journeyAux.setThirdPartyBus(patch.getThirdPartyBus());
                    continue;
                case driver:
                    journeyAux.setDriver(patch.getDriver());
                    continue;
                case busNumber:
                    journeyAux.setBusNumber(patch.getBusNumber());
                    continue;
                case seats:
                    journeyAux.setSeats(patch.getSeats());
                    continue;
                case seatsState:
                    journeyAux.setSeatsState(patch.getSeatsState());
                    continue;
                case standingPassengers:
                    journeyAux.setStandingPassengers(patch.getStandingPassengers());
                    continue;
                case trunkWeight:
                    journeyAux.setTrunkWeight(patch.getTrunkWeight());
                    continue;
                case status:
                    journeyAux.setStatus(patch.getStatus());
            }
        }
        String oid = ejb.persist(journeyAux);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

}
