package com.usbus.dal.model;

import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "tickets",noClassnameStored = false)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iTicketKey", unique=true))})
public class Ticket extends BaseEntity{
    private Long id;
    private Date emissionDate;
    private Boolean hasCombination;
    @Reference
    private Service combination;
    private Double amount;
    @Reference
    private User passenger;
    @Reference
    private HumanResource seller;
    private TicketStatus status;

    public Ticket(){
    }

    public Ticket(long tenantId, Long id, Date emissionDate, Boolean hasCombination, Service combination, Double amount, User passenger, HumanResource seller) {
        super(tenantId);
        this.id = id;
        this.emissionDate = emissionDate;
        this.hasCombination = hasCombination;
        this.combination = combination;
        this.amount = amount;
        this.passenger = passenger;
        this.seller = seller;
        this.status = TicketStatus.UNUSED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }

    public Boolean getHasCombination() {
        return hasCombination;
    }

    public void setHasCombination(Boolean hasCombination) {
        this.hasCombination = hasCombination;
    }

    public Service getCombination() {
        return combination;
    }

    public void setCombination(Service combination) {
        this.combination = combination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public HumanResource getSeller() {
        return seller;
    }

    public void setSeller(HumanResource seller) {
        this.seller = seller;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
