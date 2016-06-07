package com.usbus.bll.administration.interfaces;

import com.usbus.commons.auxiliaryClasses.Credentials;
import com.usbus.commons.auxiliaryClasses.Token;
import org.jose4j.lang.JoseException;

import javax.ejb.Remote;

/**
 * Created by jpmartinez on 05/06/16.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface AuthenticationRemote {
    Token authenticateUser(Credentials credentials) throws JoseException;
}
