package com.usbus.dal.model;

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
    private Service combination;
    private Double amount;
    private User passager;
    private HumanResource seller;

    public Ticket(){
    }

    public Ticket(long tenantId, Long id, Date emissionDate, Boolean hasCombination, Service combination, Double amount) {
        super(tenantId);
        this.id = id;
        this.emissionDate = emissionDate;
        this.hasCombination = hasCombination;
        this.combination = combination;
        this.amount = amount;
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
}
