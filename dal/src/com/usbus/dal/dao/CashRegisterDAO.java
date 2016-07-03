package com.usbus.dal.dao;

import com.usbus.commons.enums.CashOrigin;
import com.usbus.commons.enums.CashPayment;
import com.usbus.commons.enums.CashType;
import com.usbus.commons.exceptions.CashRegisterException;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.CashRegister;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by jpmartinez on 02/07/16.
 */
public class CashRegisterDAO {
    private final Datastore ds;
    private final GenericPersistence dao;
    private static final Logger logger = LoggerFactory.getLogger(CashRegisterDAO.class);


    public CashRegisterDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(CashRegister cashRegister) {
        return dao.persist(cashRegister);
    }

    public long countAll() {
        return dao.count(CashRegister.class);
    }

    public long countTenant(long tenantId) {
        Query<CashRegister> query = ds.createQuery(CashRegister.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public CashRegister getById(String id) {
        return dao.get(CashRegister.class, id);
    }

    public List<CashRegister> getByTenant(long tenantId, int offset, int limit) {
        if (!(tenantId > 0)) {
            return null;
        }
        Query<CashRegister> query = ds.createQuery(CashRegister.class);
        query.and(query.criteria("tenantId").equal(tenantId));
        return query.offset(offset).limit(limit).asList();

    }

    public CashRegister getByLocalId(long tenantId, Long id) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<CashRegister> query = ds.createQuery(CashRegister.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public void remove(String id) {
        dao.remove(CashRegister.class, id);
    }

    public void clean() {
        ds.delete(ds.createQuery(CashRegister.class));
    }

    public Long getNextId(long tenantId) {
        if (!(tenantId > 0)) {
            return null;
        } else {
            Query<CashRegister> query = ds.createQuery(CashRegister.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true,"id");
            CashRegister cashRegister = query.get();
            if (cashRegister==null){
                return new Long(1);
            }
            return cashRegister.getId() + 1;

        }
    }

    public Double cashCount(Long tenantId, Long branchId, Long windowsId) throws CashRegisterException{
        logger.debug("cashCount ==> TenantId: " +tenantId.toString() + " branchId: " + branchId.toString() + " windowsId: " + windowsId.toString() );
        Query<CashRegister> queryInit = ds.createQuery(CashRegister.class);
        queryInit.and(queryInit.criteria("tenantId").equal(tenantId), queryInit.criteria("branchId").equal(branchId), queryInit.criteria("windowsId").equal(windowsId), queryInit.criteria("type").equal(CashType.CASH_INIT));
        queryInit.order("tenantId, brandId, windowsId, -date").limit(1);
        CashRegister cashInit = queryInit.get();
        logger.debug("APERTURA OBTENIDA ==> " +cashInit.toString());
        Query<CashRegister> query = ds.createQuery(CashRegister.class);
        query.and(query.criteria("tenantId").equal(tenantId), query.criteria("branchId").equal(branchId), query.criteria("windowsId").equal(windowsId), query.criteria("date").greaterThan(cashInit.getDate()), query.criteria("date").lessThanOrEq(new Date()));
        List<CashRegister> cashRegisterList = query.asList();
        logger.debug("LISTA OBTENIDA ==> " +cashRegisterList.toString());
        Double cashCount = Double.valueOf(0);
        for (CashRegister cr : cashRegisterList){
            switch (cr.getType()){
                case ENTRY:
                    cashCount = cashCount + cr.getAmount();
                    break;
                case WITHDRAWAL:
                    cashCount = cashCount - cr.getAmount();
                    break;
                case CASH_CLOSURE:
                    logger.error("Se encontro un registro de CASH_CLOSURE ==> " + cr.toString());
                    throw new CashRegisterException("La caja ya se encuentra cerrada.");
                case CASH_INIT:
                    logger.error("Se encontro un registro de CASH_INIT ==> " + cr.toString());
                    throw new CashRegisterException("La caja no puede estar abierta multiples veces.");
                case CASH_COUNT:
                    logger.debug("Se encontro un registro de CASH COUNT ==> " + cr.toString());
                    break;
            }
        }

        logger.info("Resultado del arqueo: " + cashCount.toString());
        return cashCount;
    }

    public boolean isCashRegisterOpen(Long tenantId, Long branchId, Long windowsId){
        logger.debug("cashCount ==> TenantId: " +tenantId.toString() + " branchId: " + branchId.toString() + " windowsId: " + windowsId.toString() );
        Query<CashRegister> queryClose = ds.createQuery(CashRegister.class);
        queryClose.and(queryClose.criteria("tenantId").equal(tenantId), queryClose.criteria("branchId").equal(branchId), queryClose.criteria("windowsId").equal(windowsId), queryClose.criteria("type").equal(CashType.CASH_CLOSURE));
        queryClose.order("tenantId, brandId, windowsId, -date").limit(1);
        CashRegister cashClose = queryClose.get();
        logger.debug("CIERRE OBTENIDO ==> " + cashClose.toString());

        Query<CashRegister> queryInit = ds.createQuery(CashRegister.class);
        queryInit.and(queryInit.criteria("tenantId").equal(tenantId), queryInit.criteria("branchId").equal(branchId), queryInit.criteria("windowsId").equal(windowsId), queryInit.criteria("type").equal(CashType.CASH_INIT));
        queryInit.order("tenantId, brandId, windowsId, -date").limit(1);
        CashRegister cashInit = queryInit.get();
        logger.debug("APERTURA OBTENIDA ==> " +cashInit.toString());
        logger.debug("(cashClose.getDate().before(cashInit.getDate()) ==> " + cashClose.getDate().before(cashInit.getDate()));
        return (cashClose.getDate().before(cashInit.getDate()));
    }

    public List<CashRegister> getByTenantBranchWindow(Long tenantId, Long branchId, Long windowsId, int limit, int offset){
        if (tenantId>0){
            Query<CashRegister> query = ds.createQuery(CashRegister.class);
            List<CashRegister> cashRegisterList;


            if (branchId>0 && windowsId>0){
                query.and(query.criteria("tenantId").equal(tenantId), query.criteria("branchId").equal(branchId), query.criteria("windowsId").equal(windowsId));
            }else if(branchId>0){
                query.and(query.criteria("tenantId").equal(tenantId), query.criteria("branchId").equal(branchId));
            }else if (windowsId>0){
                query.and(query.criteria("tenantId").equal(tenantId), query.criteria("windowsId").equal(windowsId));
            }else{
                query.and(query.criteria("tenantId").equal(tenantId));
            }
            return query.offset(offset).limit(limit).asList();
        }
        return null;
    }
    public List<CashRegister> getBetweenDates(Long tenantId, Long branchId, Long windowsId, Date start, Date end, int limit, int offset){
        if (tenantId>0){
            Query<CashRegister> query = ds.createQuery(CashRegister.class);
            List<CashRegister> cashRegisterList;
            query.and(query.criteria("tenantId").equal(tenantId));
            if(branchId>0) query.and(query.criteria("branchId").equal(branchId));
            if (windowsId>0) query.and(query.criteria("windowsId").equal(windowsId));
            query.and(query.criteria("date").greaterThanOrEq(start),query.criteria("date").lessThanOrEq(end));
            return query.offset(offset).limit(limit).asList();
        }
        return null;
    }
    public List<CashRegister> getByTypeOriginPaymentDate(Long tenantId, Long branchId, Long windowsId, Date start, Date end, CashType type, CashOrigin origin, CashPayment payment, String user, int limit, int offset){
        if (tenantId>0){
            Query<CashRegister> query = ds.createQuery(CashRegister.class);
            List<CashRegister> cashRegisterList;
            query.and(query.criteria("tenantId").equal(tenantId));
            if(branchId>0) query.and(query.criteria("branchId").equal(branchId));
            if (windowsId>0) query.and(query.criteria("windowsId").equal(windowsId));
            if(!(start ==null || end==null)){
                query.and(query.criteria("date").greaterThanOrEq(start),query.criteria("date").lessThanOrEq(end));
            }
            if (!(type==null)) query.and(query.criteria("type").equal(type));
            if (!(origin==null)) query.and(query.criteria("origin").equal(origin));
            if (!(payment==null)) query.and(query.criteria("payment").equal(payment));
            if (!(user==null || user.isEmpty())) query.and(query.criteria("sellerName").equal(user));

            return query.offset(offset).limit(limit).asList();
        }
        return null;
    }



}
