package com.usbus.commons.auxiliaryClasses;

/**
 * Created by jpmartinez on 25/05/16.
 */
public class Credentials {
    private Long tenantId;
    private String username;
    private String password;
    private String type;

    public Long getTenantId() {
        return tenantId;
    }

    public Credentials() {
    }

    public Credentials(Long tenantId, String username, String password, String type) {
        this.tenantId = tenantId;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType(){return type;}

    public void setType(String type){ this.type = type;}
}
