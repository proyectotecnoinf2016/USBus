package com.usbus.bll.administration.interfaces;

import com.usbus.commons.enums.HRStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.exceptions.UserException;
import com.usbus.dal.model.HumanResource;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by jpmartinez on 26/06/16.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface HumanResourceRemote {
    void persist(HumanResource user) throws UserException;
    HumanResource getById(String id);
    HumanResource getByUsername(long tenantId, String username);
    HumanResource getByEmail(long tenantId, String email);
    List<HumanResource> getByStatus(long tenantId, Boolean status, int offset, int limit);
    List<HumanResource> getByHRStatus(long tenantId, HRStatus status, int offset, int limit);

    List<HumanResource> getByRol(long tenantId, Rol rol, int offset, int limit);

    List<HumanResource> getByRolAndStatus(long tenantId, HRStatus status, Rol rol, int offset, int limit);

    List<HumanResource> getByRolAvailable(long tenantId, Rol rol, int offset, int limit);

    List<HumanResource> getAllHumanResources(long tenantId, int offset, int limit);
    void setInactive(long tenantId, String username);
}
