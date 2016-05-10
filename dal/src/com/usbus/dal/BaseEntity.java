package com.usbus.dal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;


/**
 * Created by JuanPablo on 4/27/2016.
 */


public abstract class BaseEntity {
    @Id
    private ObjectId id;
    private long tenantId;
    private Date creationDate;
    private Date lastChange;
    @Version
    private long version;

    public BaseEntity() {
        super();
    }

    public BaseEntity(long tenantId) {
        super();
        setTenantId(tenantId);
    }


    public ObjectId getId() {
        return id;
    }
    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    @PrePersist
    public void prePersist() {
        this.creationDate = (creationDate == null) ? new Date() : creationDate;
        this.lastChange = (lastChange == null) ? creationDate : new Date();
    }

}
