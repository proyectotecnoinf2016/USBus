package com.usbus.services.administration;

import com.usbus.commons.enums.Rol;
import com.usbus.services.auth.Secured;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jpmartinez on 08/05/16.
 */
@Stateless
@Path("/test")
public class TestREST {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        String res = System.getProperty("jboss.server.log.dir");//${jboss.server.home.dir}/log/USBUS.log
        return Response.ok(res).build();
    }

    @GET
    @Path("/cliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Secured({Rol.CLIENT})
    public Response soloCliente() {
        return Response.ok("Cliente").build();
    }

    @GET
    @Path("/administrador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Secured({Rol.ADMINISTRATOR})
    public Response soloAdministrador() {
        return Response.ok("Administrador").build();
    }

    @GET
    @Path("/cajayadministrador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Secured({Rol.ADMINISTRATOR, Rol.CASHIER})
    public Response cajeroYAdmin() {
        return Response.ok("Cajero y Administrador").build();
    }
}
