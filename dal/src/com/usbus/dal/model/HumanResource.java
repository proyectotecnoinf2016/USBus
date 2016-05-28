package com.usbus.dal.model;

import com.usbus.commons.enums.Gender;
import com.usbus.commons.enums.Rol;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "users",noClassnameStored = false)
public class HumanResource extends User {
    private Boolean status;
    private Rol[] roles;

    public HumanResource(){
    }

    public HumanResource(long tenantId, String username, String email, String firstName, String lastName, Date birthDate, String password, Gender gender, Date startDate, Date lastActive, Boolean active, Boolean status, Rol[] roles) {
        super(tenantId, username, email, firstName, lastName, birthDate, password, gender, startDate, lastActive, active);
        this.status = status;
        this.roles = roles;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Rol[] getRoles() {
        return roles;
    }

    public void setRoles(Rol[] roles) {
        this.roles = roles;
    }
}
