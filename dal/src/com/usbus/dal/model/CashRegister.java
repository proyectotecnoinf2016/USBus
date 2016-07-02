package com.usbus.dal.model;

import com.usbus.commons.enums.CashType;
import com.usbus.commons.enums.CashOrigin;
import com.usbus.dal.BaseEntity;

import java.util.Date;

/**
 * Created by jpmartinez on 02/07/16.
 */
public class CashRegister extends BaseEntity {

    private Long id;
    private Long branchId;
    private Long windowsId;
    private String bus;
    private String sellerName;
    private Long ticketId;
    private Long parcelId;
    private CashOrigin origin;
    private CashType type;
    private Double amount;
    private Date date;


}
