package com.usbus.bll.administration.interfaces;

import com.usbus.commons.exceptions.UserException;
import com.usbus.dal.model.User;
import jdk.nashorn.internal.ir.annotations.Reference;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by jpmartinez on 26/06/16.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface UserRemote {
    void persist(User user) throws UserException;
    User getByUsername(long tenantId, String username);
    User getByEmail(long tenantId, String email);
    List<User> getAllUsers(long tenantId, int offset, int limit);
    void setInactive(long tenantId, String username) throws UserException;

}
