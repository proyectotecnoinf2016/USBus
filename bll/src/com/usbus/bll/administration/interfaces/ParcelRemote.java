package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Parcel;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Lufasoch on 02/07/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface ParcelRemote {
    String persist(Parcel parcel);
    Parcel getByLocalId(long tenantId, Long id);
    List<Parcel> getOnDestination(long tenantId, Long destinationId, Boolean onDestination, int offset, int limit);
    List<Parcel> getOnDestinationNotDelivered(long tenantId, Long destinationId, Boolean onDestination, int offset, int limit);
    List<Parcel> getByOrigin(long tenantId, Long originId, int offset, int limit);

    List<Parcel> getParcels(long tenantId, int offset, int limit);

    List<Parcel> getByJourney(long tenantId, Long journeyId, int offset, int limit);
}
