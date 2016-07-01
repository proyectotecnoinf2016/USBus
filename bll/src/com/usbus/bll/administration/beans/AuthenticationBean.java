package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.AuthenticationLocal;
import com.usbus.bll.administration.interfaces.AuthenticationRemote;
import com.usbus.commons.auxiliaryClasses.Credentials;
import com.usbus.commons.auxiliaryClasses.RSAKeys;
import com.usbus.commons.auxiliaryClasses.Token;
import com.usbus.commons.auxiliaryClasses.Utils;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.exceptions.AuthException;
import com.usbus.dal.dao.TenantDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.Tenant;
import com.usbus.dal.model.User;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

/**
 * Created by jpmartinez on 05/06/16.
 */
@Stateless(name = "AuthenticationEJB")
public class AuthenticationBean implements AuthenticationLocal, AuthenticationRemote{
    static Logger logger = LoggerFactory.getLogger(AuthenticationBean.class);

    private final UserDAO userDAO = new UserDAO();
    private final TenantDAO tenantDAO = new TenantDAO();
    public AuthenticationBean(){

    }

    @Override
    public Token authenticateUser(Credentials credentials) throws JoseException, AuthException, Exception{
            if (credentials.getTenantId()==null || credentials.getTenantId()==0){
                Tenant tenant = tenantDAO.getByName(credentials.getTenantName());
                credentials.setTenantId(tenant.getTenantId());
            }
            authenticate(credentials);
            Token token = issueToken(credentials);
            return token;
    }

    private void authenticate(Credentials credentials) throws AuthException {
        if (!(userDAO.validatePassword(credentials.getTenantId(),credentials.getUsername(),credentials.getPassword()))){
            throw new AuthException("Error al autenticar el usuario.");
        }
    }

    private Token issueToken(Credentials credentials) throws JoseException, Exception {

        User user = userDAO.getByUsername(credentials.getTenantId(),credentials.getUsername());

        String keyPath = System.getProperty("jboss.server.config.dir") + "/private_key.der";
        PrivateKey pk = RSAKeys.getPrivateKey(keyPath);

        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Usbus");
        claims.setAudience("Audience");
        claims.setExpirationTimeMinutesInTheFuture(180); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setSubject(credentials.getUsername());
        claims.setClaim("tenantId",credentials.getTenantId());
        List<Rol> roles = userDAO.getRoles(user);
        List<String> rolesString = Utils.stringListFromEnumList(Rol.class,roles);
        if (roles.size() > 0) {
            claims.setStringListClaim("roles",rolesString);
        }else {
            roles.add(Rol.CLIENT);
            rolesString = Utils.stringListFromEnumList(Rol.class,roles);
            claims.setClaim("roles", rolesString);
        }
        logger.debug(rolesString.toString());
        //Firmar
        JsonWebSignature jws = new JsonWebSignature();
        jws.setKey(pk);
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA512);
        String jwt = jws.getCompactSerialization();

        return new Token(jwt,credentials.getTenantId(),roles);
    }
}
