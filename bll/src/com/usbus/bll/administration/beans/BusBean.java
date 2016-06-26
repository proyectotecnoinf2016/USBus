package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.BusLocal;
import com.usbus.bll.administration.interfaces.BusRemote;
import com.usbus.commons.enums.BusStatus;
import com.usbus.dal.dao.BusDAO;
import com.usbus.dal.model.Bus;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by Lufasoch on 05/06/2016.
 */
@Stateless(name = "BusEJB")
public class BusBean implements BusLocal, BusRemote {
    private final BusDAO dao = new BusDAO();
    public BusBean() {}

    @Override
    public String persist(Bus bus01) {
        return dao.persist(bus01);
    }

    @Override
    public Bus getById(String oid) {
        Bus busAux = dao.getById(oid);
        return busAux;
    }

    @Override
    public Bus getByLocalId(long tenantId, String id) {
        Bus busAux = dao.getByLocalId(tenantId, id);
        return busAux;
    }

    @Override
    public void setInactive(long tenantId, String busId) {
        dao.setInactive(tenantId, busId);
    }

    @Override
    public void setActive(long tenantId, String busId) { dao.setActive(tenantId, busId); }

    @Override
    public Bus getByBusId(long tenantId, String id) { return dao.getByBusId(tenantId, id); }

    @Override
    public List<Bus> BusesByTenantIdAndStatus(long tenantId, BusStatus status, int offset, int limit) {
        return dao.BusesByTenantIdAndStatus(tenantId, status, offset, limit);
    }
}
