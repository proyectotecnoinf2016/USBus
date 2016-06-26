package com.usbus.bll.administration.interfaces;

import com.usbus.commons.auxiliaryClasses.TenantStyleAux;
import com.usbus.dal.model.Tenant;
import org.bson.types.ObjectId;

import javax.ejb.Remote;
import java.io.IOException;

/**
 * Created by Lufasoch on 26/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface TenantRemote {
    Tenant getById(ObjectId id);
    Tenant getByLocalId(Long id);
    ObjectId saveTenantStyle(long tenantId, TenantStyleAux style) throws IOException;
}
