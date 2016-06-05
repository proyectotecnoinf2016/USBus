package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.BusStopLocal;
import com.usbus.bll.administration.interfaces.BusStopRemote;
import com.usbus.dal.dao.BusStopDAO;
import com.usbus.dal.model.BusStop;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

import javax.ejb.Stateless;

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
    public ObjectId persist(BusStop busStop) {
        return dao.persist(busStop);
    }

    @Override
    public BusStop getById(ObjectId id) {
        return dao.getById(id);
    }

    @Override
    public BusStop getByLocalId(long tenantId, Long id) {
        return dao.getByLocalId(tenantId,id);
    }

    @Override
    public BusStop getByName(long tenantId, String name) {
        return dao.getByName(tenantId,name);
    }

    @Override
    public void setInactive(long tenantId, Long id) {
        dao.setInactive(tenantId,id);
    }

    @Override
    public void setActive(long tenantId, Long id) {
        dao.setActive(tenantId,id);
    }
}
