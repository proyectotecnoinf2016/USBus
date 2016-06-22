package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Route;
import org.bson.types.ObjectId;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Lufasoch on 21/06/2016.
 */
@Local
public interface RouteLocal {
    Route getById(ObjectId id);
    Route getByLocalId(long tenantId, Long id);
    ObjectId persist(Route route);
    List<Route> getRoutesByOrigin(long tenantId, int offset, int limit, String origin);
    List<Route> getRoutesByDestination(long tenantId, int offset, int limit, String destination);
}
