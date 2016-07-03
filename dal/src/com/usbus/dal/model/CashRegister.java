package com.usbus.dal.model;

import com.usbus.commons.enums.CashPayment;
import com.usbus.commons.enums.CashType;
import com.usbus.commons.enums.CashOrigin;
import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by jpmartinez on 02/07/16.
 */
@XmlRootElement
@Entity(value = "cashRegister",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iCashRegisterKey", unique=true)),
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "branchId") }, options = @IndexOptions(name="iCashBranch", unique=false)),
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "branchId") ,@Field(value = "windowsId") }, options = @IndexOptions(name="iCashWindow", unique=false))})
public class CashRegister extends BaseEntity {

    private Long id;
    private Long branchId;
    private Long windowsId;
    private String busId;
    private String sellerName;
    private Long ticketId;
    private Long parcelId;
    private CashOrigin origin;
    private CashType type;
    private CashPayment payment;
    private Double amount;
    private Date date;
    private String notes;

    public CashRegister(){}
    public CashRegister(long tenantId, Long id, Long branchId, Long windowsId, String busId, String sellerName, Long ticketId, Long parcelId, CashOrigin origin, CashType type, CashPayment payment, Double amount, Date date, String notes) {
        super(tenantId);
        this.id = id;
        this.branchId = branchId;
        this.windowsId = windowsId;
        this.busId = busId;
        this.sellerName = sellerName;
        this.ticketId = ticketId;
        this.parcelId = parcelId;
        this.origin = origin;
        this.payment = payment;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getWindowsId() {
        return windowsId;
    }

    public void setWindowsId(Long windowsId) {
        this.windowsId = windowsId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public CashOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(CashOrigin origin) {
        this.origin = origin;
    }

    public CashType getType() {
        return type;
    }

    public void setType(CashType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CashPayment getPayment() {
        return payment;
    }

    public void setPayment(CashPayment payment) {
        this.payment = payment;
    }
}
