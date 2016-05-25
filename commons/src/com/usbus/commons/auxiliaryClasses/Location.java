package com.usbus.commons.auxiliaryClasses;

/**
 * Created by Lufasoch on 24/05/2016.
 */
public class Location {
    private String latitude;
    private String longitude;
    private String description;

    public Location(){
    }

    public Location(String latitude, String longitude, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
