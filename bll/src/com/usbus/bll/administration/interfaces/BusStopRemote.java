package com.usbus.bll.administration.interfaces;

import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.dal.model.BusStop;
import org.bson.types.ObjectId;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by jpmartinez on 04/06/16.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface BusStopRemote {
    long countTenant(long tenantId);
    String persist(BusStop busStop);
    BusStop getById(String id);
    BusStop getByLocalId(long tenantId, Long id);
    BusStop getByName(long tenantId, String name);
    void setInactive(long tenantId, Long id);
    void setActive(long tenantId, Long id);
    List<BusStop> getByTenant(long tenantId, int offset, int limit, boolean status, String name);
    List<RouteStop> getDestinations(long tenantId, int offset, int limit, String origin);
    List<RouteStop> getOrigins(long tenantId, int offset, int limit, String destination);
}
