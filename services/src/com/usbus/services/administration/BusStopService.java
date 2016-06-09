package com.usbus.services.administration;

import com.usbus.bll.administration.beans.BusStopBean;

import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.BusStop;
import com.usbus.services.auth.Secured;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jpmartinez on 05/06/16.
 */
/*El path del servicio siempre debe ser en singular y ser el nombre del recurso con el que se va a interactuar*/
@Path("{tenantId}/busstop")
public class BusStopService {
    //@EJB
    BusStopBean ejb = new BusStopBean();


    /*OBTENER LISTA siempre se debe hacer en un GET*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response getBusStopList(@PathParam("tenantId")Long tenantId, @QueryParam("offset") int offset, @QueryParam("limit") int limit, @QueryParam("name") String name){

        List<BusStop> busStopList = ejb.getByTenant(tenantId,offset,limit,name);
        if (busStopList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(busStopList).build();
    }

    /*OBTENER UN ELEMENTO siempre se debe hacer en un get pasando como PATH el código del elemento*/
    @GET
    @Path("{busStopId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response getBusStop(@PathParam("tenantId")Long tenantId, @PathParam("busStopId") Long busStopId){

        BusStop busStop = ejb.getByLocalId(tenantId,busStopId);
        if (busStop == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(busStop).build();
    }

    /*MODIFICAR UN ELEMENTO siempre se debe hacer en un PUT pasando como PATH el código del elemento*/
    @PUT
    @Path("{busStopId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response updateBusStop(@PathParam("tenantId")Long tenantId, @PathParam("busStopId") Long busStopId, BusStop busStop){

        BusStop busStopAux = ejb.getByLocalId(tenantId,busStopId);
        busStop.set_id(busStopAux.get_id());

        ObjectId oid = ejb.persist(busStop);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }


    /*CREACIÓN, siempre debe ser un POST*/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response createBusStop(BusStop busStop){
        ObjectId oid = ejb.persist(busStop);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(ejb.getById(oid)).build();
    }

    /*ELIMINACIÓN siempre debe ser un DELETE indicando como path param el código del elemento*/
    @DELETE
    @Path("{busStopId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response removeBusStop(@PathParam("tenantId")Long tenantId, @PathParam("busStopId") Long busStopId){
        try {
            ejb.setInactive(tenantId,busStopId); //POR AHORA SOLO IMPLEMENTAMOS UN BORRADO LÓGICO.
            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }



}
