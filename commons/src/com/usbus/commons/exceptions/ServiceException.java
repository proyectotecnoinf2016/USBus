package com.usbus.commons.exceptions;

/**
 * Created by jpmartinez on 01/07/16.
 */
public class ServiceException extends Exception{
    public ServiceException(String mensaje){
        super(mensaje);
    }
}