package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.Window;
import com.usbus.dal.BaseEntity;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "branches",noClassnameStored = true)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "id") }, options = @IndexOptions(name="iBranchKey", unique=true)),
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "name") }, options = @IndexOptions(name="iBranchName", unique=true))})
public class Branch extends BaseEntity {
    private Long id;
    private String name;
    private List<Window> windows;
    private Boolean active;
    private Double latitude;
    private Double longitude;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Branch(){
    }

    public Branch(long tenantId, Long id, String name, List<Window> windows, Boolean active, Double latitude, Double longitude) {
        super(tenantId);
        this.id = id;
        this.name = name;
        this.windows = windows;
        this.active = active;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Window> getWindows() {
        return windows;
    }

    public void setWindows(List<Window> windows) {
        this.windows = windows;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
