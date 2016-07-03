package com.usbus.bll.administration.interfaces;

import com.usbus.commons.enums.CashOrigin;
import com.usbus.commons.enums.CashPayment;
import com.usbus.commons.enums.CashType;
import com.usbus.commons.exceptions.CashRegisterException;
import com.usbus.dal.model.CashRegister;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;

/**
 * Created by jpmartinez on 02/07/16.
 */
@SuppressWarnings("EjbRemoteRequirementsInspection")
@Remote
public interface CashRegisterRemote {
    void openCashRegister(Long tenantId, Long branchId, Long windowId, String sellerName, Double initialAmount) throws CashRegisterException;
    void closeCashRegister(Long tenantId, Long branchId, Long windowId, String sellerName, Double finalAmount) throws CashRegisterException;
    Double cashCount(Long tenantId, Long branchId, Long windowId) throws CashRegisterException;
    void persist(CashRegister cashRegister) throws  CashRegisterException;

    CashRegister getByLocalId(Long tenantId, Long cashRegisterId);

    List<CashRegister> getByTenantBranchWindow(Long tenantId, Long branchId, Long windowsId, int limit, int offset);

    List<CashRegister> getBetweenDates(Long tenantId, Long branchId, Long windowsId, Date start, Date end, int limit, int offset);

    List<CashRegister> getByTypeOriginPaymentDate(Long tenantId, Long branchId, Long windowsId, Date start, Date end, CashType type, CashOrigin origin, CashPayment payment, String user, int limit, int offset);
}
