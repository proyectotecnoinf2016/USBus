package com.usbus.services.auth;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials){

        try {
            System.out.println("AUTENTICANDO");
            // Authenticate the user using the credentials provided
            authenticate(credentials);

            // Issue a token for the user
            Token token = issueToken(credentials.getUsername());

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(Credentials credentials) throws Exception {
        System.out.println("authenticate");
        User user = null;
        try {
            UserDAO udao = new UserDAO();
            user = udao.getByUsername(credentials.getTenantId(),credentials.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }

        if(user==null){
            throw new Exception();
        }else{
            System.out.println("Existe el usuario");
        }
    }

    private Token issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);

        return new Token(token);

    }
}