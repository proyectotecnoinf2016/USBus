package com.usbus.services.auth;

import com.usbus.bll.administration.beans.RegistrationBean;
import com.usbus.dal.dao.TenantDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.Tenant;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by jpmartinez on 08/05/16.
 */
@Path("/register")
public class Register {
    //@EJB
    RegistrationBean ejb = new RegistrationBean();

    @POST
    @Path("/tenant")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerTenant(Tenant tenant) {
        long tenantId = ejb.registerTenant(tenant);

        if (tenantId > 0) {
            return Response.ok(tenantId).build();
        } else {
            if (tenantId == -1) {
                return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN).entity("Ya existe una empresa virtual registrada con el mismo nombre. Por favor seleccione un nombre distinto.").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Ocurrió un error al registrar el tenant, intentelo nuevamente.").build();
            }
        }
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerUser(User user) {
        int result = ejb.registerUser(user);
        if (result == 1) {
            return Response.ok("Usuario Guardado").build();
        } else {
            if (result < 0) switch (result) {
                case -1:
                    return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN).entity("Ya existe el usuario que intenta crear!").build();

                case -2:
                    return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN).entity("Ya existe un usuario con ese email.").build();

            }
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Ocurrió un error al registrar el usuario, intentelo nuevamente.").build();
    }

    @POST
    @Path("/client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerClient(User user) {
        int result = ejb.registerClient(user);
        if (result == 1) {
            return Response.ok("Usuario Guardado").build();
        }else {
            if (result < 0) switch (result) {
                case -1:
                    return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN).entity("Ya existe el cliente que intenta crear!").build();

                case -2:
                    return Response.status(Response.Status.CONFLICT).type(MediaType.TEXT_PLAIN).entity("Ya existe un cliente con ese email.").build();
            }
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Ocurrió un error al registrar el cliente, intentelo nuevamente.").build();

    }
}
