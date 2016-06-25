package com.usbus.dal.model;

import com.usbus.commons.auxiliaryClasses.HumanResourceStatus;
import com.usbus.commons.enums.Gender;
import com.usbus.commons.enums.HRStatus;
import com.usbus.commons.enums.Rol;
import org.mongodb.morphia.annotations.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by Lufasoch on 28/05/2016.
 */
@XmlRootElement
@Entity(value = "users",noClassnameStored = false)
public class HumanResource extends User {
    private Boolean status;
    private List<HumanResourceStatus> statusHistory;
    private List<Rol> roles;

    public HumanResource(){
    }

    public HumanResource(User user, Boolean status, List<Rol> roles) {
        super(user.getTenantId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getPassword(), user.getGender(), user.getStartDate(), user.getLastActive(), user.getActive());
        this.status = status;
        this.roles = roles;
    }

    public HumanResource(long tenantId, String username, String email, String firstName, String lastName, Date birthDate, String password, Gender gender, Date startDate, Date lastActive, Boolean active, Boolean status, List<Rol> roles) {
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

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<HumanResourceStatus> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<HumanResourceStatus> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public void addStatus(HumanResourceStatus status){
        switch (status.getStatus()){
            case ACTIVE:
                this.status = true;
                break;
            case DISMISSED:
                this.status = false;
                break;
            case VACATION:
                this.status = false;
                break;
            case LAYOFF:
                this.status = false;
                break;
        }
        this.statusHistory.add(status);
    }
}
