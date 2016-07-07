package com.usbus.dal.test;

import com.usbus.dal.dao.JourneyDAO;
import com.usbus.dal.dao.ReservationDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.Reservation;
import com.usbus.dal.model.User;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Lufasoch on 06/07/2016.
 */
public class ReservationTest {
    protected JourneyDAO jdao = new JourneyDAO();
    protected ReservationDAO dao = new ReservationDAO();
    protected UserDAO userDAO = new UserDAO();

    @Test
    public void persist(){
//        User user = userDAO.getByUsername(2,"Pepe");
//
//        Date date = new Date();
//        Journey journey = jdao.getByLocalId(2,1L);
//
//        Reservation reservation = new Reservation(2,4L,null,"6.666.666/6",null,journey,1,true);
//        dao.persist(reservation);
    }
}
