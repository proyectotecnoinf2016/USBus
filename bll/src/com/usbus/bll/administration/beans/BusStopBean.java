package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.BusStopLocal;
import com.usbus.bll.administration.interfaces.BusStopRemote;
import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.dal.dao.BusStopDAO;
import com.usbus.dal.dao.RouteDAO;
import com.usbus.dal.model.BusStop;
import com.usbus.dal.model.Route;
import org.bson.types.ObjectId;


import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jpmartinez on 04/06/16.
 */
@Stateless(name = "BusStopEJB")
public class BusStopBean implements BusStopLocal, BusStopRemote {
    private final BusStopDAO dao = new BusStopDAO();

    public BusStopBean() {
    }

    @Override
    public long countTenant(long tenantId) {
        return dao.countTenant(tenantId);
    }

    @Override
    public String persist(BusStop busStop) {
        busStop.setId(dao.getNextId(busStop.getTenantId()));
        return dao.persist(busStop);
    }

    @Override
    public BusStop getById(String id) {
        return dao.getById(id);
    }

    @Override
    public BusStop getByLocalId(long tenantId, Long id) {
        return dao.getByLocalId(tenantId, id);
    }

    @Override
    public BusStop getByName(long tenantId, String name) {
        return dao.getByName(tenantId, name);
    }

    @Override
    public void setInactive(long tenantId, Long id) {
        dao.setInactive(tenantId, id);
    }

    @Override
    public void setActive(long tenantId, Long id) {
        dao.setActive(tenantId, id);
    }

    @Override
    public List<BusStop> getByTenant(long tenantId, int offset, int limit, String name) {
        return dao.getByTenant(tenantId, offset, limit, name);
    }

    @Override
    public List<RouteStop> getDestinations(long tenantId, int offset, int limit, String origin) {
        RouteDAO routeDAO = new RouteDAO();
        List<Route> routes = routeDAO.getRoutesByOrigin(tenantId, offset, limit, origin);
        List<RouteStop> routeStops = new ArrayList<>();
        for (Route route :
                routes) {
            routeStops.addAll(route.getBusStops());

        }
        if (routeStops.size() ==0){
            return null;
        }
        Set<RouteStop> hs = new HashSet<>();
        hs.addAll(routeStops);
        routeStops.clear();
        routeStops.addAll(hs);
        return routeStops;
    }

    @Override
    public List<RouteStop> getOrigins(long tenantId, int offset, int limit, String destination) {
        RouteDAO routeDAO = new RouteDAO();
        List<Route> routes = routeDAO.getRoutesByDestination(tenantId, offset, limit, destination);
        List<RouteStop> routeStops = new ArrayList<>();
        for (Route route :
                routes) {
            routeStops.addAll(route.getBusStops());

        }
        if (routeStops.size() ==0){
            return null;
        }
        Set<RouteStop> hs = new HashSet<>();
        hs.addAll(routeStops);
        routeStops.clear();
        routeStops.addAll(hs);
        return routeStops;    }
}
