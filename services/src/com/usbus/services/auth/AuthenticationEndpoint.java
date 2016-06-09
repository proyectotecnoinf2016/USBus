package com.usbus.services.auth;

import com.usbus.bll.administration.beans.AuthenticationBean;
import com.usbus.commons.auxiliaryClasses.Credentials;
import com.usbus.commons.auxiliaryClasses.Token;
import com.usbus.commons.exceptions.AuthException;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.User;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    static AuthenticationBean ejb = new AuthenticationBean();
    static Logger logger = LoggerFactory.getLogger(AuthenticationEndpoint.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {

        try {
            Token token = ejb.authenticateUser(credentials);
            return Response.ok(token).build();
        } catch (AuthException e) {
            logger.info(e.toString());
            logger.info(credentials.toString());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (JoseException e) {
            logger.error(e.toString());
            logger.info(credentials.toString());
            logger.debug("message",e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error(e.toString());
            logger.info(credentials.toString());
            logger.debug("message",e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}