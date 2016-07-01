package com.usbus.bll.administration.interfaces;

import com.usbus.commons.auxiliaryClasses.ServicePOST;
import com.usbus.commons.exceptions.ServiceException;
import com.usbus.dal.model.Service;
import org.bson.types.ObjectId;

import javax.ejb.Remote;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 08/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface ServiceRemote {
    Service getById(String id);
    Service getByLocalId(long tenantId, Long serviceId);

    void multiPersist(ServicePOST service) throws ServiceException;

    String persist(Service service);
    List<Service> getServicesByTenantAndDayOfTheWeek(long tenantId, DayOfWeek dayOfWeek, int offset, int limit);
    List<Service> getServicesByTenantDOWAndStops(long tenantId, DayOfWeek day, String origin, String destination, int offset, int limit);
    List<Service> getServicesByTenant(long tenantId, int offset, int limit);
    void setInactive(long tenantId, Long serviceId);
    void setActive(long tenantId, Long serviceId);
}
