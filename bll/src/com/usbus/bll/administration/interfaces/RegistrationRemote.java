package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Tenant;
import com.usbus.dal.model.User;

import javax.ejb.Remote;

/**
 * Created by jpmartinez on 05/06/16.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface RegistrationRemote {
    long registerTenant(Tenant tenant);
    int registerUser(User user);
    int registerClient(User user);

}