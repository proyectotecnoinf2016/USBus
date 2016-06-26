package com.usbus.commons.auxiliaryClasses;

import com.usbus.commons.enums.HRStatus;

import java.util.Date;

/**
 * Created by jpmartinez on 25/06/16.
 */
public class HumanResourceStatus{
    private Date start;
    private Date end;
    private HRStatus status;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public HRStatus getStatus() {
        return status;
    }

    public void setStatus(HRStatus status) {
        this.status = status;
    }
}

