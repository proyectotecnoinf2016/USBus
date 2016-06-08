package com.usbus.commons.auxiliaryClasses;

/**
 * Created by jpmartinez on 26/05/16.
 */
public class Token {


    private String token;
    private Long tenantId;

    public Token() {
    }

    public Token(String token, Long tenantId) {
        this.token = token;
        this.tenantId = tenantId;
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
}
