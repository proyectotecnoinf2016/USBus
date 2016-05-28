package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.Window;
import com.usbus.dal.BaseEntity;

/**
 * Created by Lufasoch on 28/05/2016.
 */
public class Branch extends BaseEntity {
    private Long id;
    private String name;
    private Window[] windows;
    private Boolean active;

    public Branch(){
    }

    public Branch(long tenantId, Long id, String name, Window[] windows, Boolean active) {
        super(tenantId);
        this.id = id;
        this.name = name;
        this.windows = windows;
        this.active = active;
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

    public Window[] getWindows() {
        return windows;
    }

    public void setWindows(Window[] windows) {
        this.windows = windows;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
