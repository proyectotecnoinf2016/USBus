package com.usbus.services.auth;

import com.usbus.bll.administration.beans.AuthenticationBean;
import com.usbus.commons.auxiliaryClasses.Credentials;
import com.usbus.commons.auxiliaryClasses.Token;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.User;

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
 * Created by jpmartinez on 25/05/16.
 */
@Path("/authentication")
public class AuthenticationEndpoint {
    AuthenticationBean ejb = new AuthenticationBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials){

        try {
            Token token = ejb.authenticateUser(credentials);
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}