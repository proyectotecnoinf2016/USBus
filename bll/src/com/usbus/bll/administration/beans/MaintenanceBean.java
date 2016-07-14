package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.MaintenanceLocal;
import com.usbus.bll.administration.interfaces.MaintenanceRemote;
import com.usbus.dal.dao.MaintenanceDAO;
import com.usbus.dal.model.Maintenance;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 27/06/2016.
 */
@Stateless(name = "MaintenanceEJB")
public class MaintenanceBean implements MaintenanceLocal, MaintenanceRemote{
    private final MaintenanceDAO dao = new MaintenanceDAO();
    public MaintenanceBean() {}

    @Override
    public String persist(Maintenance maintenance) {
        return dao.persist(maintenance);
    }

    @Override
    public Maintenance getById(String id) {
        return dao.getById(id);
    }

    @Override
    public Maintenance getByLocalId(long tenantId, Long id) {
        return dao.getByLocalId(tenantId, id);
    }

    @Override
    public List<Maintenance> getByTenant(long tenantId, int offset, int limit) {
        return dao.getByTenant(tenantId, offset, limit);
    }

    @Override
    public List<Maintenance> getMaintenancesBetweenDates(long tenantId, Date time1, Date time2, int offset, int limit) {
        return dao.getMaintenancesBetweenDates(tenantId, time1, time2, offset, limit);
    }

    @Override
    public List<Maintenance> getByBus(long tenantId, String busId, int offset, int limit) {
        return dao.getByBus(tenantId, busId, offset, limit);
    }

    @Override
    public Boolean setMaintenanceStatus(long tenantId, String busId, Boolean toMaintenance) {
        return dao.setMaintenanceStatus(tenantId, busId, toMaintenance);
    }
}
