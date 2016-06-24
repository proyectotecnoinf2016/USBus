package com.usbus.commons.auxiliaryClasses;

/**
 * Created by Lufasoch on 22/06/2016.
 */
public class TenantStyle {
    private Image logo;
    private Image headerImage;
    private String busColor;
    private Boolean showBus;
    private String theme;

    public TenantStyle(){}

    public TenantStyle(Image logo, Image headerImage, String busColor, Boolean showBus, String theme) {
        this.logo = logo;
        this.headerImage = headerImage;
        this.busColor = busColor;
        this.showBus = showBus;
        this.theme = theme;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public Image getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(Image headerImage) {
        this.headerImage = headerImage;
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
