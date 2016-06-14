package com.usbus.dal.model;

import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by jpmartinez on 08/05/16.
 */
@XmlRootElement
@Entity(value = "tenants",noClassnameStored = true)
@Indexes({@Index(fields = { @Field(value = "tenantId")}, options = @IndexOptions(name="iTenantKey", unique=true)),
        @Index(fields = { @Field(value = "name")}, options = @IndexOptions(name="iTenantName", unique=true))})

public class Tenant extends BaseEntity {
    public String name;

    public Tenant() {
    }

    public Tenant(long tenantId, String name){
        super(tenantId);
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
