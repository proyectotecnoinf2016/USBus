package com.usbus.commons.auxiliaryClasses;

import com.usbus.commons.enums.TicketStatus;

/**
 * Created by jpmartinez on 22/06/16.
 */
public class TicketConfirmation {
    private long tenantId;
    private long id;
    private String paymentToken;
    private String username;
    private TicketStatus status;

    public TicketConfirmation() {
    }

    public TicketConfirmation(TicketStatus status, long tenantId, long id, String paymentToken, String username) {
        this.status = status;
        this.tenantId = tenantId;
        this.id = id;
        this.paymentToken = paymentToken;
        this.username = username;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }


}
