package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.ReservationLocal;
import com.usbus.bll.administration.interfaces.ReservationRemote;
import com.usbus.dal.dao.ReservationDAO;
import com.usbus.dal.model.Reservation;

import javax.ejb.Stateless;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lufasoch on 01/07/2016.
 */
@Stateless(name = "ReservationEJB")
public class ReservationBean implements ReservationLocal, ReservationRemote {
    private final ReservationDAO dao = new ReservationDAO();

    public ReservationBean() {
    }

    @Override
    public String persist(Reservation reservation) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(reservation.getJourney().getDate());
//        cal.add(Calendar.MINUTE, -30);
        reservation.setId(dao.getNextId(reservation.getTenantId()));
        return dao.persist(reservation);
    }

    @Override
    public Reservation getById(String id) {
        return dao.getById(id);
    }

    @Override
    public Reservation getByLocalId(long tenantId, Long id) {
        return dao.getByLocalId(tenantId, id);
    }

    @Override
    public void remove(String id) {
        dao.remove(id);
    }

    @Override
    public void setInactive(long tenantId, Long reservationId) {
        dao.setInactive(tenantId, reservationId);
    }

    @Override
    public List<Reservation> getByUserNameAndStatus(long tenantId, String clientId, Boolean status, int offset, int limit) {
        return dao.getByUserNameAndStatus(tenantId, clientId, status, offset, limit);
    }

    @Override
    public List<Reservation> getByJourney(long tenantId, Long journeyId, int offset, int limit) {
        return dao.getByJourney(tenantId, journeyId, offset, limit);
    }
}
