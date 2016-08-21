package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.ParcelLocal;
import com.usbus.bll.administration.interfaces.ParcelRemote;
import com.usbus.dal.dao.ParcelDAO;
import com.usbus.dal.model.Parcel;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 02/07/2016.
 */
@Stateless(name = "ParcelEJB")
public class ParcelBean implements ParcelLocal, ParcelRemote{
    private final ParcelDAO dao = new ParcelDAO();

    public ParcelBean() {
    }


    @Override
    public String persist(Parcel parcel) {
        return dao.persist(parcel);
    }

    @Override
    public Parcel getByLocalId(long tenantId, Long id) {
        return dao.getByLocalId(tenantId, id);
    }

    @Override
    public Parcel getById(String id) {
        return dao.getById(id);
    }

    @Override
    public List<Parcel> getOnDestination(long tenantId, Long destinationId, Boolean onDestination, int offset, int limit) {
        return dao.getOnDestination(tenantId, destinationId, onDestination, offset, limit);
    }

    @Override
    public List<Parcel> getOnDestinationNotDelivered(long tenantId, Long destinationId, Boolean onDestination, int offset, int limit) {
        return dao.getOnDestinationNotDelivered(tenantId, destinationId, onDestination, offset, limit);
    }

    @Override
    public List<Parcel> getByOrigin(long tenantId, Long originId, int offset, int limit) {
        return dao.getByOrigin(tenantId, originId, offset, limit);
    }
    @Override
    public List<Parcel> getParcels(long tenantId, int offset, int limit) {
        return dao.getParcels(tenantId, offset, limit);
    }
    @Override
    public List<Parcel> getByJourney(long tenantId, Long journeyId, int offset, int limit) {
        return dao.getByJourney(tenantId, journeyId, offset, limit);
    }
    @Override
    public List<Parcel> getByShippedDate(long tenantId, Date date, int offset, int limit) {
        return dao.getByShippedDate(tenantId, date, offset, limit);
    }
    @Override
    public List<Parcel> getByEnteredDate(long tenantId, Date date, int offset, int limit) {
        return dao.getByEnteredDate(tenantId, date, offset, limit);
    }
    @Override
    public List<Parcel> getBySender(long tenantId, String sender, int offset, int limit) {
        return dao.getBySender(tenantId, sender, offset, limit);
    }
    @Override
    public List<Parcel> getByReceiver(long tenantId, String receiver, int offset, int limit) {
        return dao.getByReceiver(tenantId, receiver, offset, limit);
    }
}
