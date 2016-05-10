package com.usbus.bll.aut;

import com.mongodb.MongoException;
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

    protected UserDAO udao = new UserDAO();
    protected TenantDAO tdao = new TenantDAO();

    @POST
    @Path("/tenant")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response regTenant(Tenant tenant) {
        ObjectId tenantOID = null;
        try {
            tenantOID = tdao.persist(tenant);
            return Response.ok("Tenant Guardado").build();


        } catch (Exception ex) {
            tdao.remove(tenantOID);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Ocurrió un error al registrar el tenant, intentelo nuevamente.").build();

        }

    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response regUser( User user) {
        ObjectId userOID = null;
        try {
            userOID = udao.persist(user);

            Random random = new SecureRandom();
            String token = new BigInteger(130, random).toString(32);
            return Response.ok(token).build();

        } catch (Exception ex) {
            udao.remove(userOID);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.TEXT_PLAIN).entity("Ocurrió un error al registrar el usuario, intentelo nuevamente.").build();

        }

    }
}
