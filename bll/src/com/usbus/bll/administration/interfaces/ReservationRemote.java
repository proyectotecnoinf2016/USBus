package com.usbus.bll.administration.interfaces;

import com.usbus.dal.model.Reservation;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Lufasoch on 01/07/2016.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface ReservationRemote {
    String persist(Reservation reservation);
    Reservation getById(String id);
    Reservation getByLocalId(long tenantId, Long id);
    void remove(String id);
    void setInactive(long tenantId, Long reservationId);
    List<Reservation> getByUserNameAndStatus(long tenantId, String username, Boolean status, int offset, int limit);
    List<Reservation> getByJourney(long tenantId, Long journeyId, int offset, int limit);
}
