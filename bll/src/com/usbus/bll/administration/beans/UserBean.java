package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.UserLocal;
import com.usbus.bll.administration.interfaces.UserRemote;
import com.usbus.commons.exceptions.UserException;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.User;

import java.util.List;

/**
 * Created by jpmartinez on 26/06/16.
 */
public class UserBean implements UserLocal,UserRemote {
    private final UserDAO udao = new UserDAO();
    public UserBean(){

    }
    @Override
    public void persist(User user) throws UserException {
        if (udao.persist(user)==null) throw new UserException("Ocurrió un error al grabar al usuario");
    }

    @Override
    public User getByUsername(long tenantId, String username) {
        return udao.getByUsername(tenantId,username);
    }

    @Override
    public User getByEmail(long tenantId, String email) {
        return udao.getByEmail(tenantId,email);
    }

    @Override
    public List<User> getAllUsers(long tenantId, int offset, int limit) {
        return udao.getAllUsers(tenantId,offset,limit);
    }

    @Override
    public void setInactive(long tenantId, String username) throws UserException {
        udao.setInactive(tenantId,username);
    }
}
