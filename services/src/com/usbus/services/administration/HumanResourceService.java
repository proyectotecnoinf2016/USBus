package com.usbus.services.administration;

import com.usbus.bll.administration.beans.HumanResourceBean;
import com.usbus.commons.enums.HRStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.exceptions.UserException;
import com.usbus.dal.model.HumanResource;
import com.usbus.services.auth.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by jpmartinez on 26/06/16.
 */
@Path("{tenantId}/hr")

public class HumanResourceService {
    HumanResourceBean ejb = new HumanResourceBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRoute(HumanResource user) {
        try {
            ejb.persist(user);
            return Response.ok(ejb.getByUsername(user.getTenantId(), user.getUsername())).build();
        } catch (UserException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response updateRoute(@PathParam("tenantId") Long tenantId, @PathParam("username") String username, HumanResource user) {
        try {
            HumanResource userAux = ejb.getByUsername(tenantId, username);
            user.set_id(userAux.get_id());
            ejb.persist(user);
            return Response.ok(user).build();
        } catch (UserException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getRoute(@PathParam("tenantId") long tenantId, @PathParam("username") String username) {

        HumanResource userAux = ejb.getByUsername(tenantId, username);
        if (userAux == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(userAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response queryRoutes(@PathParam("tenantId") long tenantId,
                                @QueryParam("query") String query,
                                @QueryParam("email") String email,
                                @QueryParam("status") Boolean status,
                                @QueryParam("rol") Rol rol,
                                @QueryParam("hrstatus") HRStatus hrstatus,
                                @QueryParam("offset") int offset,
                                @QueryParam("limit") int limit) {
        List<HumanResource> userList = null;
        if (query == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No se envio el query param \"query\"").build();
        }
        switch (query.toUpperCase()) {
            case "ALL":
                userList = ejb.getAllHumanResources(tenantId, offset, limit);
                if (userList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(userList).build();
            case "STATUS":
                userList = ejb.getByStatus(tenantId, status, offset, limit);
                if (userList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(userList).build();
            case "HRSTATUS":
                userList = ejb.getByHRStatus(tenantId, hrstatus, offset, limit);
                if (userList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(userList).build();
            case "EMAIL": {
                HumanResource userAux = ejb.getByEmail(tenantId, email);
                if (userAux == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(userAux).build();
            }
            case "ROL": {
                userList = ejb.getByRol(tenantId, rol, offset, limit);
                if (userList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(userList).build();
            }
            case "ROL_STATUS": {
                userList = ejb.getByRolAndStatus(tenantId, hrstatus, rol, offset, limit);
                if (userList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(userList).build();
            }
            case "ROL_AVAILABLE": {
                userList = ejb.getByRolAvailable(tenantId,rol, offset, limit);
                if (userList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(userList).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();

    }

    @DELETE
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response removeService(@PathParam("tenantId") Long tenantId, @PathParam("username") String username) {
        try {
            ejb.setInactive(tenantId, username);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
