package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.TenantStyle;
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
    private String name;
    private TenantStyle style;

    public Tenant() {
    }

    public Tenant(long tenantId, String name){
        super(tenantId);
        this.name = name;
        this.style = null;
    }

    public Tenant(long tenantId, String name, TenantStyle style) {
        super(tenantId);
        this.name = name;
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TenantStyle getStyle() {
        return style;
    }

    public void setStyle(TenantStyle style) {
        this.style = style;
    }
}
