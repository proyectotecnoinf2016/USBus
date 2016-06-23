package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.RouteLocal;
import com.usbus.bll.administration.interfaces.RouteRemote;
import com.usbus.dal.dao.RouteDAO;
import com.usbus.dal.model.Route;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Lufasoch on 21/06/2016.
 */
@Stateless(name = "RouteEJB")
public class RouteBean implements RouteLocal, RouteRemote {
    private final RouteDAO dao = new RouteDAO();
    public RouteBean(){};

    @Override
    public Route getById(ObjectId id) {
        return dao.getById(id);
    }

    @Override
    public Route getByLocalId(long tenantId, Long id) {
        return dao.getByLocalId(tenantId, id);
    }

    @Override
    public Route getByName(long tenantId, String name) {
        return dao.getByName(tenantId, name);
    }

    @Override
    public ObjectId persist(Route route) {
        return dao.persist(route);
    }

    @Override
    public List<Route> getRoutesByTenant(long tenantId, int offset, int limit) {
        return dao.getRoutesByTenant(tenantId, offset, limit);
    }

    @Override
    public List<Route> getRoutesByOrigin(long tenantId, int offset, int limit, String origin) {
        return dao.getRoutesByOrigin(tenantId, offset, limit, origin);
    }

    @Override
    public List<Route> getRoutesByDestination(long tenantId, int offset, int limit, String destination) {
        return dao.getRoutesByDestination(tenantId, offset, limit, destination);
    }

    @Override
    public List<Route> getRoutesByOriginDestination(long tenantId, int offset, int limit, String origin, String destination) {
        return dao.getRoutesByOriginDestination(tenantId, offset, limit, origin, destination);
    }

    @Override
    public void setInactive(long tenantId, Long serviceId) {
        dao.setInactive(tenantId, serviceId);
    }
}
