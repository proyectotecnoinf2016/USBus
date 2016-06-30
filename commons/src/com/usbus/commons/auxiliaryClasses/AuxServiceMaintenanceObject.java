package com.usbus.commons.auxiliaryClasses;

import java.util.Date;

/**
 * Created by Lufasoch on 28/06/2016.
 */
public class AuxServiceMaintenanceObject {
    private String busId;
    private Date time1;
    private Date time2;
    private int offset;
    private int limit;

    public AuxServiceMaintenanceObject(){}

    public AuxServiceMaintenanceObject(String busId, Date time1, Date time2, int offset, int limit) {

        this.busId = busId;
        this.time1 = time1;
        this.time2 = time2;
        this.offset = offset;
        this.limit = limit;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
