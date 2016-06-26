package com.usbus.bll.administration.interfaces;

import com.usbus.commons.exceptions.UserException;
import com.usbus.dal.model.User;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by jpmartinez on 26/06/16.
 */
@Local
public interface UserLocal {
    void persist(User user) throws UserException;
    User getByUsername(long tenantId, String username);
    User getByEmail(long tenantId, String email);
    List<User> getAllUsers(long tenantId, int offset, int limit);
    void setInactive(long tenantId, String username) throws UserException;

}
