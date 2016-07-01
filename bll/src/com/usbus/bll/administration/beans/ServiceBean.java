package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.ServiceLocal;
import com.usbus.bll.administration.interfaces.ServiceRemote;
import com.usbus.commons.auxiliaryClasses.ServicePOST;
import com.usbus.commons.exceptions.ServiceException;
import com.usbus.dal.dao.RouteDAO;
import com.usbus.dal.dao.ServiceDAO;
import com.usbus.dal.model.Route;
import com.usbus.dal.model.Service;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 08/06/2016.
 */
@Stateless(name = "ServiceEJB")
public class ServiceBean implements ServiceLocal, ServiceRemote {
    private final ServiceDAO dao = new ServiceDAO();

    public ServiceBean() {
    }

    @Override
    public Service getById(String id) {
        return dao.getById(id);
    }

    @Override
    public Service getByLocalId(long tenantId, Long serviceId) {
        return dao.getByLocalId(tenantId, serviceId);
    }

    @Override
    public void multiPersist(ServicePOST sp) throws ServiceException{

        List<String> servicesCreados = new ArrayList<>();
        SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
        Route route = new RouteDAO().getByLocalId(sp.getTenantId(), sp.getRoute());
        for (DayOfWeek day: sp.getDay()) {
            for (Date time : sp.getTime()) {

                String serviceName = sp.getName() + "-" + day.name() + "-" + hour.format(time);
                Service service = new Service(sp.getTenantId(), sp.getId(), serviceName.trim(), day, time, route, sp.getNumberOfBuses(), sp.getActive());
                service.setId(dao.getNextId(service.getTenantId()));
                String oid = dao.persist(service);
                if (oid == null) {
                    for (String s : servicesCreados){
                        dao.remove(s);
                    }
                    throw new ServiceException("Ocurrió un error al intentar grabar el serivicio "+ serviceName);
                } else {
                    servicesCreados.add(oid);
                }
            }
            
        }
    }

    @Override
    public String persist(Service service) {
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
