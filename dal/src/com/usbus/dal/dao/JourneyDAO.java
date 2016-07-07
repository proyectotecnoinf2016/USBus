package com.usbus.dal.dao;

import com.sun.deploy.util.ArrayUtil;
import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.commons.enums.JourneyStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.HumanResource;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.Route;
import org.bson.types.ObjectId;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.*;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class JourneyDAO {
    private final Datastore ds;
    private final GenericPersistence dao;
    private final BusStopDAO bsdao = new BusStopDAO();
    private static final Logger logger = LoggerFactory.getLogger(JourneyDAO.class);


    public JourneyDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Journey journey) {
        if(journey != null) {
            return dao.persist(journey);
        } else {
            return null;
        }
    }

    public long countAll() {
        return dao.count(Journey.class);
    }

    public long countTenant(long tenantId) {
        if(tenantId > 0) {
            Query<Journey> query = ds.createQuery(Journey.class);
            query.criteria("tenantId").equal(tenantId);
            return query.countAll();
        } else {
            return 0;
        }
    }

    public Journey getById(String id) {
        if(id == null || id.isEmpty()) {
            return null;
        } else {
            return dao.get(Journey.class, id);
        }
    }

    public Journey getByJourneyId(long tenantId, Long id){
        if (tenantId < 0 || id == null || id < 0) {
            return null;
        } else {
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("id").equal(id), query.criteria("tenantId").equal(tenantId));
            return query.get();
        }
    }

    public Journey getByLocalId(long tenantId, Long id) {
//        if (tenantId < 0 || id == null || id < 0) {
//            return null;
//        } else {
//            Query<Journey> query = ds.createQuery(Journey.class);
//            query.and(query.criteria("id").equal(id), query.criteria("tenantId").equal(tenantId));
//            return query.get();
//        }
        return getByJourneyId(tenantId, id); //Just do it!!!
    }

    public void setInactive(long tenantId, Long journeyId) {
        if (tenantId >= 0 && journeyId != null) {
            Query<Journey> query = ds.createQuery(Journey.class);

            query.and(query.criteria("id").equal(journeyId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Journey> updateOp = ds.createUpdateOperations(Journey.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void setActive(long tenantId, Long journeyId) {
        if ((tenantId > 0) && (journeyId != null)) {
            Query<Journey> query = ds.createQuery(Journey.class);

            query.and(query.criteria("id").equal(journeyId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Journey> updateOp = ds.createUpdateOperations(Journey.class).set("active", true);
            ds.update(query, updateOp);
        }
    }

    public List<Journey> JourneysByTenantIdAndStatus(long tenantId, JourneyStatus status, int offset, int limit){
        if(tenantId > 0 || status == null){
            return null;
        } else {
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("status").equal(status));
            return query.offset(offset).limit(limit).asList();
        }
    }

    public Double getJourneyPrice(long tenantId, Long journeyId, String origin, String destination){
        if(tenantId < 0 || journeyId == null || journeyId < 0 || origin == null ||
                origin.isEmpty() || destination == null || destination.isEmpty()) {
            return null;
        } else {
            Journey jaux = getByJourneyId(tenantId, journeyId);
            Double pricexkm = jaux.getService().getRoute().getPricePerKm();
            Double kmOrigin = null;
            Double kmDestination = null;
            List<RouteStop> routeStops = jaux.getService().getRoute().getBusStops();
            for (RouteStop stop :
                    routeStops) {
                if (stop.getBusStop().equals(origin)) {
                    kmOrigin = stop.getKm();
                }else if(stop.getBusStop().equals(destination)){
                    kmDestination = stop.getKm();
                }
            }
//            for(int i = 0; i < routeStops.length; i++) {
//                if(routeStops[i].getBusStop().equals(origin)) {
//                    kmOrigin = routeStops[i].getKm();
//                } else if (routeStops[i].getBusStop().equals(destination)) {
//                    kmDestination = routeStops[i].getKm();
//                }
//            }
            if(kmDestination == null || kmOrigin == null) {
                return null;
            } else {
                return pricexkm * (kmDestination - kmOrigin);
            }
        }
    }

    public List<Journey> getJourneysByTenantDateAndStatus(long tenantId, Date time, JourneyStatus journeyStatus, int offset, int limit){
        if(tenantId < 0 || time == null || journeyStatus == null || offset < 0 || limit <= 0){
            return null;
        } else {
            Date timeAux = time;
            //CAL1
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
            cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
            time = cal.getTime();
            //CAL2
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(time);
            cal2.set(Calendar.HOUR_OF_DAY, cal2.getMaximum(Calendar.HOUR_OF_DAY));
            cal2.set(Calendar.MINUTE, cal2.getMaximum(Calendar.MINUTE));
            cal2.set(Calendar.SECOND, cal2.getMaximum(Calendar.SECOND));
            cal2.set(Calendar.MILLISECOND, cal2.getMaximum(Calendar.MILLISECOND));
            timeAux = cal2.getTime();
            //END
            //System.out.println("Fecha inicial: " + time.toString());
            //System.out.println("Fecha final: " + timeAux.toString());
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("date").greaterThanOrEq(time), query.criteria("date").lessThanOrEq(timeAux),
                    query.criteria("tenantId").equal(tenantId), query.criteria("status").equal(journeyStatus));
            return query.offset(offset).limit(limit).asList();
        }
    }

    public List<Journey> getJourneysByTenantAndDate(long tenantId, Date time, int offset, int limit){
        if(tenantId < 0 || time == null || offset < 0 || limit <= 0){
            return null;
        } else {
            //CAL1
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
            cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
            time = cal.getTime();
            //CAL2
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(time);
            cal2.set(Calendar.HOUR_OF_DAY, cal2.getMaximum(Calendar.HOUR_OF_DAY));
            cal2.set(Calendar.MINUTE, cal2.getMaximum(Calendar.MINUTE));
            cal2.set(Calendar.SECOND, cal2.getMaximum(Calendar.SECOND));
            cal2.set(Calendar.MILLISECOND, cal2.getMaximum(Calendar.MILLISECOND));
            Date timeAux = cal2.getTime();
            //END
//            System.out.println("Fecha inicial: " + time.toString());
//            System.out.println("Fecha final: " + timeAux.toString());
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("date").greaterThanOrEq(time), query.criteria("date").lessThanOrEq(timeAux),
                    query.criteria("tenantId").equal(tenantId));
            return query.offset(offset).limit(limit).asList();
        }
    }
    public JSONObject getNextJourneysForUser(long tenantId,String username, Date date, int offset, int limit){
        if(tenantId < 0 || date == null || offset < 0 || limit <= 0 || username==null || username.isEmpty()){
            return null;
        } else {
            logger.debug("tenantId:" + tenantId + " username " + username + "Date" + date);
            List<Journey> asDriverList = new ArrayList<>();
            List<Journey> asAssistantList = new ArrayList<>();

            Query<HumanResource> query = ds.createQuery(HumanResource.class);
            query.disableValidation();
            query.criteria("className").equal(HumanResource.class.getCanonicalName());
            query.and(query.criteria("username").equal(username),
                    query.criteria("tenantId").equal(tenantId));
            query.retrievedFields(false, "salt", "passwordHash");
            HumanResource hr = query.get();

            Query<Journey> qDriver = ds.createQuery(Journey.class);
            qDriver.and(qDriver.criteria("date").greaterThanOrEq(date), qDriver.criteria("tenantId").equal(tenantId), qDriver.criteria("driver").equal(hr));
            asDriverList = qDriver.retrievedFields(true,"service","date","bus").offset(offset).limit(limit).asList();

            Query<Journey> qAssistant = ds.createQuery(Journey.class);
            qAssistant.and(qAssistant.criteria("date").greaterThanOrEq(date), qAssistant.criteria("tenantId").equal(tenantId), qAssistant.criteria("assistant").equal(hr));
            asAssistantList = qAssistant.retrievedFields(true,"service","date","bus")
                    .offset(offset).limit(limit).asList();

            JSONObject list = new JSONObject();
            list.put("asDriver", new JSONArray(asDriverList));
            list.put("asAssistant", new JSONArray(asAssistantList));
            return list;
        }
    }
    public List<Journey> getJourneysByTenantAndDateNoLimits(long tenantId, Date time){
        if(tenantId < 0 || time == null){
            return null;
        } else {
            //CAL1
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
            cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
            time = cal.getTime();
            //CAL2
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(time);
            cal2.set(Calendar.HOUR_OF_DAY, cal2.getMaximum(Calendar.HOUR_OF_DAY));
            cal2.set(Calendar.MINUTE, cal2.getMaximum(Calendar.MINUTE));
            cal2.set(Calendar.SECOND, cal2.getMaximum(Calendar.SECOND));
            cal2.set(Calendar.MILLISECOND, cal2.getMaximum(Calendar.MILLISECOND));
            Date timeAux = cal2.getTime();
            //END
//            System.out.println("Fecha inicial: " + time.toString());
//            System.out.println("Fecha final: " + timeAux.toString());
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("date").greaterThanOrEq(time), query.criteria("date").lessThanOrEq(timeAux),
                    query.criteria("tenantId").equal(tenantId));
            return query.asList();
        }
    }

    public void remove(String id) {
        if (id != null && !id.isEmpty()) {
            dao.remove(Journey.class, id);
        }
    }

    public void clean(){
        ds.delete(ds.createQuery(Journey.class));
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Journey> query = ds.createQuery(Journey.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            Journey journey = query.get();
            if (journey==null){
                return 1L;
            }
            return journey.getId() + 1;
        }
    }
}
