package com.usbus.bll.administration.interfaces;

import com.usbus.commons.enums.BusStatus;
import com.usbus.dal.model.Bus;
import org.bson.types.ObjectId;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Lufasoch on 05/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface BusRemote {
    String persist(Bus bus01); //service
    Bus getById(String oid);
    Bus getByLocalId(long tenantId, String id);
    void setInactive(long tenantId, String busId);
    void setActive(long tenantId, String busId);
    Bus getByBusId(long tenantId, String id);
    List<Bus> BusesByTenantIdAndStatus(long tenantId, BusStatus status, int offset, int limit);

    List<Bus> BusesByTenantId(long tenantId, boolean active, int offset, int limit);
}
