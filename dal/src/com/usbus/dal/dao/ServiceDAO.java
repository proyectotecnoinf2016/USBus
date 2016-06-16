package com.usbus.dal.dao;

import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Service;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class ServiceDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public ServiceDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public ObjectId persist(Service service) {
        return dao.persist(service);
    }

    public long countAll() {
        return dao.count(Service.class);
    }

    public long countByTenant(long tenantId) {
        Query<Service> query = ds.createQuery(Service.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public List<Service> getServicesByDayOfTheWeek(long tenantId, DayOfWeek dayOfWeek, int offset, int limit){
        if(!(tenantId > 0) || (dayOfWeek == null) || (offset < 0) || ( limit <= 0)){
            return null;
        }
        Query<Service> query = ds.createQuery(Service.class);
        query.and(query.criteria("day").equal(dayOfWeek), query.criteria("tenantId").equal(tenantId));
        return query.offset(offset).limit(limit).asList();
    }

    public List<Service> getServicesByTenantDOWAndStops(long tenantId, DayOfWeek day, String origin, String destination, int offset, int limit){
        if(!(tenantId > 0) ||
                (day == null) ||
                (offset < 0) || (limit <= 0) ||
                origin == null || destination == null ||
                origin.equals(destination)){
            return null;
        }
        Query<Service> query = ds.createQuery(Service.class);
        query.and(query.criteria("day").equal(day),
                query.criteria("tenantId").equal(tenantId));
        List<Service> resultList = query.offset(offset).limit(limit).asList();
        List<Service> auxList = new ArrayList<>(resultList);
        if(auxList.isEmpty()) {
            return null;
        } else {
            int oriIdx = -1, dstIdx = -1;
            for (Service srv : auxList) {
                List<RouteStop> stops = srv.getRoute().getBusStops();
                for (int i = 0; i < stops.size(); i++) {
                    if (stops.get(i).getBusStop().equals(origin)) { //origin found in route
                        oriIdx = i;
                    } else if (stops.get(i).getBusStop().equals(destination)) { //destination found in route
                        dstIdx = i;
                    }
                }
                if(oriIdx < 0 || dstIdx < 0 || oriIdx >= dstIdx){ //check if both found in correct order
                    resultList.remove(srv);
                }
            }
        }
        return resultList;
    }

//    public List<Service> getServicesByTenantStops(long tenantId, String origin, String destination, int offset, int limit){
//        if(!(tenantId > 0) ||
//                (offset < 0) || (limit <= 0) ||
//                origin == null || destination == null ||
//                origin.equals(destination)){
//            return null;
//        }
//        Query<Service> query = ds.createQuery(Service.class);
//        query.criteria("tenantId").equal(tenantId);
//        List<Service> resultList = query.offset(offset).limit(limit).asList();
//        List<Service> auxList = new ArrayList<>(resultList);
//        if(auxList.isEmpty()) {
//            return null;
//        } else {
//            int oriIdx = -1, dstIdx = -1;
//            for (Service srv : auxList) {
//                List<RouteStop> stopsAux = srv.getRoute().getBusStops();
//                for (int i = 0; i < stopsAux.size(); i++) {
//                    if (stopsAux.get(i).getBusStop().equals(origin)) {
//                        oriIdx = i;
//                    } else if (stopsAux.get(i).getBusStop().equals(destination)) {
//                        dstIdx = i;
//                    }
//                }
//                if(oriIdx < 0 || dstIdx < 0 || oriIdx >= dstIdx){
//                    resultList.remove(srv);
//                }
//            }
//        }
//        return resultList;
//    }

    public List<Service> getServicesByTenant(long tenantId, int offset, int limit){
        if(!(tenantId > 0) || (offset < 0) || ( limit <= 0)){
            return null;
        }
        Query<Service> query = ds.createQuery(Service.class);
        query.criteria("tenantId").equal(tenantId);
        return query.offset(offset).limit(limit).asList();
    }

    public Service getById(ObjectId id) {
        return dao.get(Service.class, id);
    }

    public Service getByLocalId(long tenantId, Long serviceId) {
        if (!(tenantId > 0) || (serviceId == null)) {
            return null;
        }

        Query<Service> query = ds.createQuery(Service.class);

        query.and(query.criteria("id").equal(serviceId),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

//    public Service getByBranchId(long tenantId, Long id){
//        if (!(tenantId > 0) || (id == null)) {
//            return null;
//        }
//
//        Query<Service> query = ds.createQuery(Service.class);
//
//        query.and(query.criteria("id").equal(id),
//                query.criteria("tenantId").equal(tenantId));
//
//        return query.get();
//    }

    public void remove(ObjectId id) {
        dao.remove(Service.class, id);
    }

    public void setInactive(long tenantId, Long serviceId) {
        if (!(tenantId > 0) || (serviceId == null)) {
        } else {
            Query<Service> query = ds.createQuery(Service.class);
            query.and(query.criteria("id").equal(serviceId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Service> updateOp = ds.createUpdateOperations(Service.class).set("active", false);
            ds.update(query, updateOp);
        }
    }

    public void setActive(long tenantId, Long serviceName) {
        if (!(tenantId > 0) || (serviceName == null)) {
        } else {
            Query<Service> query = ds.createQuery(Service.class);

            query.and(query.criteria("name").equal(serviceName),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Service> updateOp = ds.createUpdateOperations(Service.class).set("active", true);
            ds.update(query, updateOp);
        }
    }

    public void clean(){
        ds.delete(ds.createQuery(Service.class));
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Service> query = ds.createQuery(Service.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            Service service = query.get();
            if (service==null){
                return new Long(1);
            }
            return service.getId() + 1;
        }
    }
}
