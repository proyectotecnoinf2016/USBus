package com.usbus.dal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;


/**
 * Created by JuanPablo on 4/27/2016.
 */


public abstract class BaseEntity {
    @Id
    private String _id;
    private long tenantId;
    private Date creationDate;
    private Date lastChange;
    @Version
    private long version;

    public BaseEntity() {
        super();
        set_id(new ObjectId().toString());
    }

    public BaseEntity(long tenantId) {
        super();
        setTenantId(tenantId);
        set_id(new ObjectId().toString());
    }


    public String get_id() {
        return _id;
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

    public void set_id(String _id) {
        this._id = _id;
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
