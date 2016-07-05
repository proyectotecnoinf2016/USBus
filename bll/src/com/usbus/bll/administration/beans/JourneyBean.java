package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.JourneyLocal;
import com.usbus.bll.administration.interfaces.JourneyRemote;
import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.commons.enums.JourneyStatus;
import com.usbus.dal.dao.JourneyDAO;
import com.usbus.dal.model.Journey;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 05/06/2016.
 */
@Stateless(name = "JourneyEJB")
public class JourneyBean implements JourneyLocal, JourneyRemote {
    private final JourneyDAO dao = new JourneyDAO();
    public JourneyBean() {}

    @Override
    public String persist(Journey journey) {
        if(journey.getId() == null) {
            journey.setId(dao.getNextId(journey.getTenantId()));
        }
        return dao.persist(journey);
    }

    @Override
    public Journey getById(String oid) {
        Journey journeyAux = dao.getById(oid);
        return journeyAux;
    }

    @Override
    public Journey getByLocalId(long tenantId, Long id) {
        Journey journeyAux = dao.getByLocalId(tenantId, id);
        return journeyAux;
    }

    @Override
    public void setInactive(long tenantId, Long journeyId) {
        dao.setInactive(tenantId, journeyId);
    }

    @Override
    public void setActive(long tenantId, Long journeyId) { dao.setActive(tenantId, journeyId); }

    @Override
    public Journey getByJourneyId(long tenantId, Long id) { return dao.getByJourneyId(tenantId, id); }

    @Override
    public List<Journey> JourneysByTenantIdAndStatus(long tenantId, JourneyStatus status, int offset, int limit) {
        return dao.JourneysByTenantIdAndStatus(tenantId, status, offset, limit);
    }

    @Override
    public List<Journey> getJourneysByTenantAndDate(long tenantId, Date time, int offset, int limit) {
        return dao.getJourneysByTenantAndDate(tenantId, time, offset, limit);
    }

    @Override
    public List<Journey> getJourneysByTenantDateAndStatus(long tenantId, Date time, JourneyStatus journeyStatus, int offset, int limit) {
        return dao.getJourneysByTenantDateAndStatus(tenantId, time, journeyStatus, offset, limit);
    }

    @Override
    public Double getJourneyPrice(long tenantId, Long journeyId, String origin, String destination) {
        return dao.getJourneyPrice(tenantId, journeyId, origin, destination);
    }

    @Override
    public List<Journey> getJourneysByDateOriginAndDestination(long tenantId, Date time, String origin, String destination){
        List<Journey> resultList = dao.getJourneysByTenantAndDateNoLimits(tenantId, time);
        List<Journey> auxList = new ArrayList<>(resultList);
        if(auxList.isEmpty()) {
            return null;
        } else {
            int oriIdx = -1, dstIdx = -1;
            for (Journey journeyAux : auxList) {
                List<RouteStop> stops = journeyAux.getService().getRoute().getBusStops();
                for (int i = 0; i < stops.size(); i++) {
                    if (stops.get(i).getBusStop().equals(origin)) { //origin found in route
                        oriIdx = i;
                    } else if (stops.get(i).getBusStop().equals(destination)) { //destination found in route
                        dstIdx = i;
                    }
                }
                if(oriIdx < 0 || dstIdx < 0 || oriIdx >= dstIdx){ //check if both found in correct order
                    resultList.remove(journeyAux);
                }
            }
        }
        return resultList;
    }
}
