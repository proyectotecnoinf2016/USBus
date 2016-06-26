package com.usbus.commons.auxiliaryClasses;

/**
 * Created by Lufasoch on 26/06/2016.
 */
public class TenantStyleAux {
    private String logoB64;
    private String logoExtension;
    private String headerB64;
    private String headerExtension;
    private String busColor;
    private Boolean showBus;
    private String theme;

    public TenantStyleAux(){}

    public TenantStyleAux(String logoB64, String logoExtension, String headerB64, String headerExtension, String busColor, Boolean showBus, String theme) {
        this.logoB64 = logoB64;
        this.logoExtension = logoExtension;
        this.headerB64 = headerB64;
        this.headerExtension = headerExtension;
        this.busColor = busColor;
        this.showBus = showBus;
        this.theme = theme;
    }

    public String getLogoB64() {
        return logoB64;
    }

    public void setLogoB64(String logoB64) {
        this.logoB64 = logoB64;
    }

    public String getLogoExtension() {
        return logoExtension;
    }

    public void setLogoExtension(String logoExtension) {
        this.logoExtension = logoExtension;
    }

    public String getHeaderB64() {
        return headerB64;
    }

    public void setHeaderB64(String headerB64) {
        this.headerB64 = headerB64;
    }

    public String getHeaderExtension() {
        return headerExtension;
    }

    public void setHeaderExtension(String headerExtension) {
        this.headerExtension = headerExtension;
    }

    public String getBusColor() {
        return busColor;
    }

    public void setBusColor(String busColor) {
        this.busColor = busColor;
    }

    public Boolean getShowBus() {
        return showBus;
    }

    public void setShowBus(Boolean showBus) {
        this.showBus = showBus;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
