package com.usbus.services.administration;

import com.usbus.services.auth.Secured;
import com.usbus.bll.administration.beans.BusBean;

import com.usbus.commons.enums.BusStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Bus;
import org.bson.types.ObjectId;

import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Lufasoch on 05/06/2016.
 */
@Path("{tenantId}/bus")
//    @Secured(Rol.ADMINISTRATOR)
public class BusService {
    BusBean ejb = new BusBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBus(Bus bus01){
        ObjectId oid = ejb.persist(bus01);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @PUT
    @Path("{busId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response updateBus(@PathParam("tenantId")Long tenantId, @PathParam("busId")String busId, Bus bus){
        Bus busAux = ejb.getByLocalId(tenantId, busId);
        bus.set_id(busAux.get_id());
        ObjectId oid = ejb.persist(bus);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @GET
    @Path("{busId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response getBus(@PathParam("tenantId")Long tenantId, @PathParam("busId") String busId){

        Bus BusAux = ejb.getByLocalId(tenantId,busId);
        if (BusAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(BusAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response getBusList(@PathParam("tenantId")Long tenantId, @QueryParam("busStatus") BusStatus busStatus, @QueryParam("offset") int offset, @QueryParam("limit") int limit){

        List<Bus> busList = ejb.BusesByTenantIdAndStatus(tenantId, busStatus, offset, limit);
        if (busList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(busList).build();
    }

    @DELETE
    @Path("{busId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response removeBus(@PathParam("tenantId")Long tenantId, @PathParam("busId") String busId){
        try {
            ejb.setInactive(tenantId,busId); //POR AHORA SOLO IMPLEMENTAMOS UN BORRADO LÓGICO.
            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
