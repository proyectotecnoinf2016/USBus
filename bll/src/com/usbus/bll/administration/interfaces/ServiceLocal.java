package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Service;
import org.bson.types.ObjectId;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 08/06/2016.
 */
@Local
public interface ServiceLocal {
    Service getById(ObjectId id);
    Service getByLocalId(long tenantId, Long serviceId);
    ObjectId persist(Service service);
    List<Service> getServicesByTenantAndDate(long tenantId, Date time, int offset, int limit);
    List<Service> getServicesByTenant(long tenantId, int offset, int limit);
    void setInactive(long tenantId, Long serviceId);
    void setActive(long tenantId, Long serviceId);
}
