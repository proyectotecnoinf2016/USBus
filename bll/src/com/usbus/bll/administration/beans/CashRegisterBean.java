package com.usbus.bll.administration.beans;

import com.usbus.bll.administration.interfaces.CashRegisterLocal;
import com.usbus.bll.administration.interfaces.CashRegisterRemote;
import com.usbus.commons.enums.CashOrigin;
import com.usbus.commons.enums.CashPayment;
import com.usbus.commons.enums.CashType;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.commons.exceptions.CashRegisterException;
import com.usbus.dal.dao.CashRegisterDAO;
import com.usbus.dal.model.CashRegister;
import com.usbus.dal.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by jpmartinez on 02/07/16.
 */
public class CashRegisterBean implements CashRegisterLocal, CashRegisterRemote {
    private final CashRegisterDAO crDAO = new CashRegisterDAO();

    private static final Logger logger = LoggerFactory.getLogger(CashRegisterBean.class);

    public CashRegisterBean() {
    }

    @Override
    public void openCashRegister(Long tenantId, Long branchId, Long windowId, String sellerName, Double initialAmount) throws CashRegisterException {

        logger.debug("openCashRegister ==> TenantId: " + tenantId.toString() + " BranchId: " + branchId.toString() + " WindowId: " + windowId.toString() + " Seller: " + sellerName + " Initial Amount: " + initialAmount.toString());

        Long cashRegisterId = crDAO.getNextId(tenantId);
        CashRegister cashRegister = new CashRegister(tenantId, cashRegisterId, branchId, windowId, "", sellerName, null, null, CashOrigin.CASH_REGISTER, CashType.CASH_INIT, CashPayment.OTHER, initialAmount, new Date(), "");
        String cashRegisterOID = crDAO.persist(cashRegister);
        if (cashRegisterOID == null || cashRegisterOID == "") {
            throw new CashRegisterException("Ocurrió un error al insertar la apertura de caja.");
        }
    }

    @Override
    public void closeCashRegister(Long tenantId, Long branchId, Long windowId, String sellerName, Double finalAmount) throws CashRegisterException {
        logger.debug("openCashRegister ==> TenantId: " + tenantId.toString() + " BranchId: " + branchId.toString() + " WindowId: " + windowId.toString() + " Seller: " + sellerName + " Final Amount: " + finalAmount.toString());

        Long cashRegisterId = crDAO.getNextId(tenantId);
        CashRegister cashRegister = new CashRegister(tenantId, cashRegisterId, branchId, windowId, "", sellerName, null, null, CashOrigin.CASH_REGISTER, CashType.CASH_CLOSURE, CashPayment.OTHER, finalAmount, new Date(), "");
        String cashRegisterOID = crDAO.persist(cashRegister);
        if (cashRegisterOID == null || cashRegisterOID == "") {
            throw new CashRegisterException("Ocurrió un error al insertar el cierre de caja.");
        }
    }

    @Override
    public Double cashCount(Long tenantId, Long branchId, Long windowId) throws CashRegisterException {
        return crDAO.cashCount(tenantId, branchId, windowId);
    }

    @Override
    public void persist(CashRegister cashRegister) throws CashRegisterException {
        if (crDAO.isCashRegisterOpen(cashRegister.getTenantId(), cashRegister.getBranchId(), cashRegister.getWindowsId())) {
            logger.debug("persist ==> CashRegister: " + cashRegister.toString());
            String cashRegisterOID = crDAO.persist(cashRegister);
            if (cashRegisterOID == null || cashRegisterOID == "") {
                throw new CashRegisterException("Ocurrió un error al guardar el registro de caja.");
            }
        } else {
            throw new CashRegisterException("La caja no se encuentra abierta.");
        }
    }

    @Override
    public CashRegister getByLocalId(Long tenantId, Long cashRegisterId) {
        return crDAO.getByLocalId(tenantId, cashRegisterId);
    }
    @Override
    public List<CashRegister> getByTenantBranchWindow(Long tenantId, Long branchId, Long windowsId, int limit, int offset) {
        return crDAO.getByTenantBranchWindow(tenantId,branchId,windowsId,limit,offset);
    }
    @Override
    public List<CashRegister> getBetweenDates(Long tenantId, Long branchId, Long windowsId, Date start, Date end, int limit, int offset) {
        return crDAO.getBetweenDates(tenantId,branchId,windowsId,start,end,limit,offset);
    }

    @Override
    public List<CashRegister> getByTypeOriginPaymentDate(Long tenantId, Long branchId, Long windowsId, Date start, Date end, CashType type, CashOrigin origin, CashPayment payment, String user, int limit, int offset){
        return crDAO.getByTypeOriginPaymentDate(tenantId,branchId,windowsId,start,end,type,origin,payment,user,limit,offset);
    }



    public CashRegister registerFromTicket(Ticket ticket) throws CashRegisterException {
        logger.debug("registerFromTicket ==> Ticket: " + ticket.toString());
        if (ticket == null) throw new CashRegisterException("ERROR: El ticket no puede ser null");
        if (!(ticket.getStatus() == TicketStatus.CONFIRMED || ticket.getStatus() == TicketStatus.CANCELED))
            throw new CashRegisterException(" El ticket no está confirmado ni cancelado.");
        //Tipo de registro
        CashType cashType;
        if (ticket.getStatus() == TicketStatus.CONFIRMED) {
            cashType = CashType.ENTRY;
        } else {
            cashType = CashType.WITHDRAWAL;
        }

        //Origen de registro
        CashOrigin cashOrigin;
        if (ticket.getBranchId() == null || ticket.getBranchId().equals(0)) {
            if (ticket.getSeller() == null) {
                cashOrigin = CashOrigin.CLIENT;
            } else {
                cashOrigin = CashOrigin.BUS;
            }
        } else {
            cashOrigin = CashOrigin.TICKET;
        }
        //Tipo de pago
        CashPayment cashPayment;
        if (cashOrigin == CashOrigin.CLIENT) {
            cashPayment = CashPayment.PAYPAL;
        } else {
            cashPayment = CashPayment.CASH;
        }
        Long cashRegisterId = crDAO.getNextId(ticket.getTenantId());

        return new CashRegister(ticket.getTenantId(), cashRegisterId, ticket.getBranchId(), ticket.getWindowId(), ticket.getJourney().getBus().getId(),
                ticket.getSeller().getUsername(), ticket.getId(), null, cashOrigin, cashType, cashPayment, ticket.getAmount(), new Date(), "");
    }

    //CASHREGISTER FROM PARCEL
}
