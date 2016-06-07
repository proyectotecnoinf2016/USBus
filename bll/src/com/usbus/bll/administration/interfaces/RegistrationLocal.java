package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Tenant;
import com.usbus.dal.model.User;

import javax.ejb.Local;


/**
 * Created by jpmartinez on 05/06/16.
 */
@Local
public interface RegistrationLocal {
    long registerTenant(Tenant tenant);
    Boolean registerUser(User user);
    Boolean registerClient(User user);

}
