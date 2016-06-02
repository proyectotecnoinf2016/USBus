package com.usbus.commons.auxiliaryClasses;


/**
 * Created by Lufasoch on 24/05/2016.
 */
public class RouteStop {
    private String busStop;
    private Double km;
    private boolean isCombinationPoint;

    public RouteStop(){};

    public RouteStop(String busStop, Double km, boolean isCombinationPoint) {
        this.busStop = busStop;
        this.km = km;
        this.isCombinationPoint = isCombinationPoint;
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
}
