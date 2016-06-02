package com.usbus.commons.auxiliaryClasses;

import com.usbus.commons.enums.Position;

/**
 * Created by Lufasoch on 01/06/2016.
 */
public class Seat {
    private Integer number;
    private Position position;
    private Boolean free;

    public Seat(){};

    public Seat(Integer number, Position position, Boolean free) {
        this.number = number;
        this.position = position;
        this.free = free;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }
}
