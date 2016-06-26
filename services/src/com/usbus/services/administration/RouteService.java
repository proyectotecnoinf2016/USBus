package com.usbus.services.administration;

/**
 * Created by Lufasoch on 21/06/2016.
 */

import com.usbus.bll.administration.beans.RouteBean;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Route;
import com.usbus.services.auth.Secured;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("{tenantId}/route")
public class RouteService {
    RouteBean ejb = new RouteBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRoute(Route route) {
        String oid = ejb.persist(route);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @PUT
    @Path("{routeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response updateRoute(@PathParam("tenantId")Long tenantId, @PathParam("routeId")Long routeId, Route route){
        Route serviceAux = ejb.getByLocalId(tenantId, routeId);
        route.set_id(serviceAux.get_id());
        String oid = ejb.persist(route);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @GET
    @Path("{routeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getRoute(@PathParam("tenantId")long tenantId, @PathParam("routeId") Long routeId){

        Route routeAux = ejb.getByLocalId(tenantId,routeId);
        if (routeAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(routeAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response queryRoutes(@PathParam("tenantId")long tenantId,
                                @QueryParam("query") String query,
                                @QueryParam("origin")String origin,
                                @QueryParam("destination")String destination,
                                @QueryParam("offset") int offset,
                                @QueryParam("limit") int limit){
        List<Route> routeList = null;
        switch (query.toUpperCase()){
            case "ALL":
                routeList = ejb.getRoutesByTenant(tenantId, offset, limit);
                if (routeList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(routeList).build();
            case "ORIGIN":
                routeList = ejb.getRoutesByOrigin(tenantId, offset, limit, origin);
                if (routeList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(routeList).build();
            case "DESTINATION":
                routeList = ejb.getRoutesByDestination(tenantId, offset, limit, destination);
                if (routeList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(routeList).build();
            case "ORIGIN_DESTINATION":
                routeList = ejb.getRoutesByOriginDestination(tenantId, offset, limit, destination, origin);
                if (routeList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(routeList).build();        }
        return Response.status(Response.Status.BAD_REQUEST).build();

    }

//    @GET
//    @Path("get/all")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
//    public Response getServiceListByTenant(@PathParam("tenantId")long tenantId, @QueryParam("offset") int offset, @QueryParam("limit") int limit){
//
//        List<Route> routeList = ejb.getRoutesByTenant(tenantId, offset, limit);
//        if (routeList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(routeList).build();
//    }
//
//    @GET
//    @Path("get/byOrigin")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
//    public Response getRoutesByOrigin(@PathParam("tenantId")long tenantId, @QueryParam("origin")String origin, @QueryParam("offset") int offset, @QueryParam("limit") int limit){
//        List<Route> routeList = ejb.getRoutesByOrigin(tenantId, offset, limit, origin);
//        if (routeList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(routeList).build();
//    }
//
//    @GET
//    @Path("get/byDestination")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
//    public Response getRoutesByDestination(@PathParam("tenantId")long tenantId,
//                                           @QueryParam("destination")String destination,
//                                           @QueryParam("offset") int offset,
//                                           @QueryParam("limit") int limit){
//        List<Route> routeList = ejb.getRoutesByDestination(tenantId, offset, limit, destination);
//        if (routeList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(routeList).build();
//    }
//
//    @GET
//    @Path("get/byDAndO")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
//    public Response getRoutesByDestination(@PathParam("tenantId")long tenantId,
//                                           @QueryParam("destination")String destination,
//                                           @QueryParam("offset") int offset,
//                                           @QueryParam("limit") int limit,
//                                           @QueryParam("origin")String origin){
//        List<Route> routeList = ejb.getRoutesByOriginDestination(tenantId, offset, limit, destination, origin);
//        if (routeList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(routeList).build();
//    }

    @DELETE
    @Path("{routeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response removeService(@PathParam("tenantId")Long tenantId, @PathParam("routeId") Long routeId){
        try {
            ejb.setInactive(tenantId,routeId);
            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
