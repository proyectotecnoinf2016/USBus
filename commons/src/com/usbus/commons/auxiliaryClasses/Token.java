package com.usbus.commons.auxiliaryClasses;

import com.usbus.commons.enums.Rol;

import java.util.List;

/**
 * Created by jpmartinez on 26/05/16.
 */
public class Token {


    private String token;
    private Long tenantId;
    private List<Rol> roles;

    public Token() {
    }

    public Token(String token, Long tenantId, List<Rol> roles) {
        this.token = token;
        this.tenantId = tenantId;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
