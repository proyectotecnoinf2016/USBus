package com.usbus.bll.administration.interfaces;

import com.usbus.commons.auxiliaryClasses.Credentials;
import com.usbus.commons.auxiliaryClasses.Token;
import com.usbus.commons.exceptions.AuthException;
import org.jose4j.lang.JoseException;

import javax.ejb.Local;

/**
 * Created by jpmartinez on 05/06/16.
 */
@Local
public interface AuthenticationLocal {
    Token authenticateUser(Credentials credentials) throws JoseException, AuthException;
}
