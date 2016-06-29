package com.usbus.dal.dao;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Bus;
import com.usbus.dal.model.Maintenance;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class MaintenanceDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public MaintenanceDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Maintenance maintenance) {
        return dao.persist(maintenance);
    }

    public long countAll() {
        return dao.count(Maintenance.class);
    }

    public long countTenant(long tenantId) {
        if(!(tenantId > 0)){
            return 0;
        }
        Query<Maintenance> query = ds.createQuery(Maintenance.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Maintenance getById(String id) {
        return dao.get(Maintenance.class, id);
    }

    public Maintenance getByLocalId(long tenantId, Long id){
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Maintenance> query = ds.createQuery(Maintenance.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public List<Maintenance> getByTenant(long tenantId, int offset, int limit) {
        if(!(tenantId > 0) || (offset < 0) || ( limit <= 0)){
            return null;
        }
        Query<Maintenance> query = ds.createQuery(Maintenance.class);
        query.criteria("tenantId").equal(tenantId);
        return query.offset(offset).limit(limit).asList();
    }

    public List<Maintenance> getMaintenancesBetweenDates(long tenantId, Date time1, Date time2, int offset, int limit){
        if(!(tenantId > 0) || (time1 == null) || (time2 == null) || (offset < 0) || ( limit <= 0)){
            return null;
        }
//        Date timeAux = time1;
//        Date timeAux2 = time2;
        //CAL1
        Calendar cal = Calendar.getInstance();
        cal.setTime(time1);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        time1 = cal.getTime();
        //CAL2
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(time2);
        cal2.set(Calendar.HOUR_OF_DAY, cal2.getMaximum(Calendar.HOUR_OF_DAY));
        cal2.set(Calendar.MINUTE, cal2.getMaximum(Calendar.MINUTE));
        cal2.set(Calendar.SECOND, cal2.getMaximum(Calendar.SECOND));
        cal2.set(Calendar.MILLISECOND, cal2.getMaximum(Calendar.MILLISECOND));
        time2 = cal2.getTime();
        //END
//        System.out.println("Fecha inicial: " + time1.toString());
//        System.out.println("Fecha final: " + time2.toString());
        Query<Maintenance> query = ds.createQuery(Maintenance.class);
        query.and(query.criteria("date").greaterThanOrEq(time1), query.criteria("date").lessThanOrEq(time2),
                query.criteria("tenantId").equal(tenantId));
        return query.offset(offset).limit(limit).asList();
    }

    public List<Maintenance> getByBus(long tenantId, String busId, int offset, int limit) {
        if(!(tenantId > 0) || (offset < 0) || ( limit <= 0)){
            return null;
        }
        Query<Maintenance> query = ds.createQuery(Maintenance.class);
        query.criteria("tenantId").equal(tenantId);
        List<Maintenance> queryList = query.offset(offset).limit(limit).order("-startDate").asList();
        List<Maintenance> auxList = new ArrayList<>(queryList);
        List<Maintenance> resultList = new ArrayList<>();
        if(auxList.isEmpty()) {
            return null;
        } else {
            for (Maintenance aux : auxList) {
                if(aux.getBus().getId() == busId){
                    resultList.add(aux);
                }
            }
            return resultList;
        }
    }

    public void remove(String id) {
        dao.remove(Maintenance.class, id);
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Maintenance> query = ds.createQuery(Maintenance.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            Maintenance maintenance = query.get();
            if (maintenance==null){
                return new Long(1);
            }
            return maintenance.getId() + 1;
        }
    }
}
