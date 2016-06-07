package com.usbus.dal.model;


import org.mongodb.morphia.annotations.*;
import com.usbus.commons.enums.Gender;
import com.usbus.dal.BaseEntity;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by JuanPablo on 4/27/2016.
 */

@XmlRootElement
@Entity(value = "users",noClassnameStored = false)
@Indexes({
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "username") }, options = @IndexOptions(name="iUserKey", unique=true)),
        @Index(fields = { @Field(value = "tenantId"), @Field(value = "email") }, options = @IndexOptions(name="iUserEmail", unique=true))})
public class User extends BaseEntity {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    @Transient
    private String password;
    private byte[] passwordHash;
    private byte[] salt;
    private Gender gender;
    private Date startDate;
    private Date lastActive;
    private Boolean active;

    public User() {
    }

    public User(long tenantId, String username, String email, String firstName, String lastName, Date birthDate, String password, Gender gender, Date startDate, Date lastActive, Boolean active) {
        super(tenantId);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;
        this.startDate = startDate;
        this.lastActive = lastActive;
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public byte[] getPasswordHash(){
        return passwordHash;
    }
    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
