package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.ServiceLocal;
import com.usbus.bll.administration.interfaces.ServiceRemote;
import com.usbus.dal.dao.ServiceDAO;
import com.usbus.dal.model.Service;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 08/06/2016.
 */
@Stateless(name = "ServiceEJB")
public class ServiceBean implements ServiceLocal, ServiceRemote{
    private final ServiceDAO dao = new ServiceDAO();
    public ServiceBean(){}

    @Override
    public Service getById(ObjectId id) {
        return dao.getById(id);
    }

    @Override
    public Service getByLocalId(long tenantId, Long serviceId) {
        return dao.getByLocalId(tenantId, serviceId);
    }

    @Override
    public ObjectId persist(Service service) {
        service.setId(dao.getNextId(service.getTenantId()));
        return dao.persist(service);
    }

    @Override
    public List<Service> getServicesByTenantAndDayOfTheWeek(long tenantId, DayOfWeek dayOfWeek, int offset, int limit) {
        return dao.getServicesByDayOfTheWeek(tenantId, dayOfWeek, offset, limit);
    }

    @Override
    public List<Service> getServicesByTenantDOWAndStops(long tenantId, DayOfWeek day, String origin, String destination, int offset, int limit) {
        return dao.getServicesByTenantDOWAndStops(tenantId, day, origin, destination, offset, limit);
    }

    @Override
    public List<Service> getServicesByTenant(long tenantId, int offset, int limit) {
        return dao.getServicesByTenant(tenantId, offset, limit);
    }

    @Override
    public void setInactive(long tenantId, Long serviceId) {
        dao.setInactive(tenantId, serviceId);
    }

    @Override
    public void setActive(long tenantId, Long serviceId) {
        dao.setActive(tenantId, serviceId);
    }
}
