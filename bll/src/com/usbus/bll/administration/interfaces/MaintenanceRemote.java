package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Maintenance;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 27/06/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface MaintenanceRemote {
    String persist(Maintenance maintenance);
    Maintenance getById(String id);
    Maintenance getByLocalId(long tenantId, Long id);
    List<Maintenance> getByTenant(long tenantId, int offset, int limit);
    List<Maintenance> getMaintenancesBetweenDates(long tenantId, Date time1, Date time2, int offset, int limit);
    List<Maintenance> getByBus(long tenantId, String busId, int offset, int limit);

}
