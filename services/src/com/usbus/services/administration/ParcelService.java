package com.usbus.services.administration;

import com.usbus.bll.administration.beans.ParcelBean;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Parcel;
import com.usbus.services.auth.Secured;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created by jpmartinez on 12/08/16.
 */
@Path("{tenantId}/parcel")
public class ParcelService {
    ParcelBean ejb = new ParcelBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CASHIER})
    public Response createParcel(Parcel parcel) {
        String oid = ejb.persist(parcel);
        if (oid == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @PUT
    @Path("{parcelId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response updateParcel(@PathParam("tenantId") Long tenantId, @PathParam("parcelId") Long parcelId, Parcel parcel) {
        Parcel parcelAux = ejb.getByLocalId(tenantId, parcelId);
        parcel.set_id(parcelAux.get_id());
        String oid = ejb.persist(parcel);
        if (oid == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @GET
    @Path("{parcelId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getParcel(@PathParam("tenantId") long tenantId, @PathParam("parcelId") Long parcelId) {

        Parcel parcelAux = ejb.getByLocalId(tenantId, parcelId);
        if (parcelAux == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(parcelAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.ASSISTANT})
    public Response getParcels(@PathParam("tenantId")Long tenantId,
                               @QueryParam("query") String query,
                               @QueryParam("journey") Long journeyId,
                               @QueryParam ("date") Date date,
                               @QueryParam ("origin") Long origin,
                               @QueryParam ("destination") Long destination,
                               @QueryParam("offset") int offset,
                               @QueryParam("username") String username,
                               @QueryParam("limit") int limit) {

        List<Parcel> parcelList = null, parcelListAux = null;
        switch (query.toUpperCase()) {
            case "JOURNEY":
                parcelList = ejb.getByJourney(tenantId, journeyId, offset, limit);
                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();
            case "ENTERED":
                parcelList = ejb.getByEnteredDate(tenantId, date, offset, limit);

                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();
            case "SHIPPED":
                parcelList = ejb.getByShippedDate(tenantId, date, offset, limit);

                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();
            case "ORIGIN":

                parcelList = ejb.getByOrigin(tenantId, origin, offset, limit);

                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();
            case "ON_DESTINATION":
                parcelList = ejb.getOnDestination(tenantId, destination, true, offset, limit);
                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();
            case "DESTINATION":
                parcelList = ejb.getOnDestination(tenantId, destination, true, offset, limit);
                parcelListAux = ejb.getOnDestination(tenantId, destination, false, offset, limit);
                for (Parcel parcel : parcelListAux
                        ) {
                    parcelList.add(parcel);
                }
                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();

            case "SENDER":
                parcelList = ejb.getBySender(tenantId, username, offset, limit);
                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();
            case "RECEIVER":
                parcelList = ejb.getByReceiver(tenantId, username, offset, limit);
                if (parcelList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(parcelList).build();
            default:
                return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}