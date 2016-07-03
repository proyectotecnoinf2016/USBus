package com.usbus.bll.administration.beans;

import com.sun.deploy.util.ArrayUtil;
import com.usbus.bll.administration.interfaces.TicketLocal;
import com.usbus.bll.administration.interfaces.TicketRemote;
import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.auxiliaryClasses.TicketConfirmation;
import com.usbus.commons.enums.Position;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.commons.exceptions.CashRegisterException;
import com.usbus.commons.exceptions.TicketException;
import com.usbus.dal.dao.JourneyDAO;
import com.usbus.dal.dao.TicketDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.Ticket;
import com.usbus.dal.model.User;
import org.bson.types.ObjectId;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@Stateless(name = "TicketEJB")
public class TicketBean implements TicketLocal, TicketRemote {
    private final TicketDAO dao = new TicketDAO();
    private final UserDAO udao = new UserDAO();
    private final JourneyDAO jdao = new JourneyDAO();
    private final CashRegisterBean cashRegisterBean = new CashRegisterBean();

    public TicketBean() {
    }

    @Override
    public String persist(Ticket ticket) throws TicketException {

        ticket.setId(dao.getNextId(ticket.getTenantId()));
        if (ticket.getSeat() != 999 && ticket.getStatus() == TicketStatus.CONFIRMED) {
            addToJourney(ticket.getTenantId(), ticket.getJourneyId(), ticket.getSeat());
        }
        String oid = dao.persist(ticket);
        if (oid == null) {
            throw new TicketException("Ocurrió un error al insertar el TICKET");
        }
        if (ticket.getStatus()==TicketStatus.CONFIRMED || ticket.getStatus() == TicketStatus.CANCELED){
            try {
                cashRegisterBean.persist(cashRegisterBean.registerFromTicket(ticket));
            }catch (CashRegisterException e){
                dao.remove(oid);
                throw new TicketException("Ocurrió un error al actualizar la caja.");
            }
        }
        return oid;
    }

    @Override
    public Ticket setPassenger(long tenantId, Long ticketId, String passengerName) {
        User passenger = udao.getByUsername(tenantId, passengerName);
        return dao.setPassenger(tenantId, ticketId, passenger);
    }

    @Override
    public Ticket confirmTicket(TicketConfirmation ticketConfirmation) throws TicketException {
        Ticket ticket = dao.getByLocalId(ticketConfirmation.getTenantId(), ticketConfirmation.getId());
        if (!(ticket == null)) {
            switch (ticket.getStatus()) {
                case CANCELED:
                    throw new TicketException("El ticket se encuentra CANCELADO");
                case USED:
                    throw new TicketException("El ticket ya fue UTILIZADO");
                case CONFIRMED:
                    if (ticketConfirmation.getStatus()==TicketStatus.USED){
                        ticket.setStatus(ticketConfirmation.getStatus());
                        if (dao.persist(ticket) != null) {
                            return ticket;
                        } else {
                            throw new TicketException("Ocurrió un error al intentar actualizar el TICKET");
                        }
                    }
                    throw new TicketException("El ticket ya está CONFIRMADO");
            }
            ticket.setPassenger(udao.getByUsername(ticketConfirmation.getTenantId(), ticketConfirmation.getUsername()));
            ticket.setPaymentToken(ticketConfirmation.getPaymentToken());
            ticket.setStatus(ticketConfirmation.getStatus());
            if (dao.persist(ticket) != null) {
                if (ticket.getStatus()==TicketStatus.CONFIRMED || ticket.getStatus() == TicketStatus.CANCELED){
                    if (ticketConfirmation.getStatus() == TicketStatus.CONFIRMED) {
                        addToJourney(ticket.getTenantId(), ticket.getJourney().getId(), ticket.getSeat());
                    }
                    try {
                        cashRegisterBean.persist(cashRegisterBean.registerFromTicket(ticket));
                    }catch (CashRegisterException e){
                        throw new TicketException("Ocurrió un error al actualizar la caja. ERROR: " + e.getMessage());
                    }
                }

                return ticket;
            } else {
                throw new TicketException("Ocurrió un error al intentar actualizar el TICKET");
            }
        }else {
            throw new TicketException("El ticket NO EXISTE");
        }
    }


    @Override
    public Ticket getById(String oid) {
        return dao.getById(oid);
    }

    @Override
    public Ticket getByLocalId(long tenantId, Long id) {
        Ticket ticketAux = dao.getByLocalId(tenantId, id);
        return ticketAux;
    }

    @Override
    public List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit) {
        return dao.getByJourneyId(tenantId, id, offset, limit);

    }

    @Override
    public List<Ticket> TicketsByBuyerAndStatus(Long tenantId, String username, TicketStatus status, int offset, int limit) {
        return dao.TicketsByBuyerAndStatus(tenantId, username, status, offset, limit);
    }

    public void setInactive(Long tenantId, Long ticketId) throws TicketException {
        dao.setInactive(tenantId, ticketId);
        removeFromJourney(tenantId, ticketId);
        try {
            cashRegisterBean.persist(cashRegisterBean.registerFromTicket(dao.getByLocalId(tenantId,ticketId)));
        }catch (CashRegisterException e){
            throw new TicketException("Ocurrió un error al actualizar la caja. ERROR: " + e.getMessage());
        }
    }

    private void addToJourney(Long tenantId, Long journeyId, int seatNumber) throws TicketException {
        Journey journey = jdao.getByJourneyId(tenantId, journeyId);
        Seat[] seats = journey.getSeatsState();
        Boolean found = false;
        for (Seat seat : seats) {
            if(seat.getNumber() == seatNumber) {
                seat.setFree(false);
                found = true;
                break;
            }
        }
        if(!found) {
            Seat seat = new Seat();
            seat.setFree(false);
            seat.setNumber(seatNumber);
            //Ir a buscar al omnibus del journey que asiento es.
            if (seats == null) {
                seats = new Seat[]{seat};
            } else {
                seats = Arrays.copyOf(seats, seats.length + 1);
                seats[seats.length - 1] = seat;
            }
        }

        journey.setSeatsState(seats);
        String oid = jdao.persist(journey);
        if (oid == null) {
            throw new TicketException("Error al actualizar el Journey");
        }
    }

    private void removeFromJourney(Long tenantId, Long ticketId) throws TicketException {
        Ticket ticket = dao.getByLocalId(tenantId, ticketId);
        Journey journey = ticket.getJourney();
        Seat[] seats = journey.getSeatsState();
        for (Seat seat : seats) {
            if (seat.getNumber().equals(ticket.getSeat())) {
                seat.setFree(true);
                break;
            }
        }
        journey.setSeatsState(seats);
        String oid = jdao.persist(journey);
        if (oid == null) {
            throw new TicketException("Error al actualizar el Journey");
        }
    }
}
