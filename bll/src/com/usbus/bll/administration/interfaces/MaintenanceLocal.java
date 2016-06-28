package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Maintenance;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Lufasoch on 27/06/2016.
 */
@Local
public interface MaintenanceLocal {
    String persist(Maintenance maintenance);
    Maintenance getById(String id);
    Maintenance getByLocalId(long tenantId, Long id);
    List<Maintenance> getByTenant(long tenantId, int offset, int limit);
}
