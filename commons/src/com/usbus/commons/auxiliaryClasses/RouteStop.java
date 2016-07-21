package com.usbus.commons.auxiliaryClasses;


/**
 * Created by Lufasoch on 24/05/2016.
 */
public class RouteStop {
    private String busStop;
    private Double km;
    private boolean isCombinationPoint;
    private Double latitude;
    private Double longitude;

    public RouteStop(){};

    public RouteStop(String busStop, Double km, boolean isCombinationPoint, Double latitude, Double longitude) {
        this.busStop = busStop;
        this.km = km;
        this.isCombinationPoint = isCombinationPoint;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public boolean isCombinationPoint() {
        return isCombinationPoint;
    }

    public void setCombinationPoint(boolean combinationPoint) {
        isCombinationPoint = combinationPoint;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
