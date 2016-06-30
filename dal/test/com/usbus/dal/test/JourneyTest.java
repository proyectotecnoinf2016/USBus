package com.usbus.dal.test;

import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.enums.JourneyStatus;
import com.usbus.commons.enums.Position;
import com.usbus.dal.dao.BusDAO;
import com.usbus.dal.dao.JourneyDAO;
import com.usbus.dal.dao.ServiceDAO;
import com.usbus.dal.model.Bus;
import com.usbus.dal.model.Journey;
import com.usbus.dal.model.Service;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Created by Kavesa on 09/06/16.
 */
public class JourneyTest {
    protected JourneyDAO dao = new JourneyDAO();
    protected ServiceDAO serviceDAO = new ServiceDAO();
    protected BusDAO busDAO = new BusDAO();

    @Test
    public void persist() throws ParseException {
        dao.clean();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        Seat v3 = new Seat(3, Position.CORRIDOR, false);
        Seat v8 = new Seat(8, Position.CORRIDOR, false);
        Seat v13 = new Seat(13, Position.CORRIDOR, false);
        Seat v23 = new Seat(23, Position.WINDOWS, false);
        Seat v40 = new Seat(40, Position.CORRIDOR, false);

        Journey uno = new Journey();
        Service suno = serviceDAO.getByLocalId(2, 5L);
        uno.setId((long) 1);
        uno.setTenantId(2);
        uno.setStatus(JourneyStatus.ACTIVE);
        uno.setService(suno);
        uno.setDate(dateFormat.parse("12/06/2016 " + suno.getTime().getHours() + ":" + suno.getTime().getMinutes()));
        uno.setBusNumber(115);
        uno.setSeatsState(new Seat[]{v3, v8, v13, v23, v40});
        dao.persist(uno);

        Journey dos = new Journey();
        dos.setId((long) 2);
        dos.setTenantId(2);
        dos.setStatus(JourneyStatus.ACTIVE);
        dos.setService(suno);
        dos.setDate(dateFormat.parse("15/06/2016 " + suno.getTime().getHours() + ":" + suno.getTime().getMinutes()));
        dos.setBusNumber(118);
        dos.setSeatsState(new Seat[]{v3, v13, v23, v8});
        dao.persist(dos);

        Journey tres = new Journey();
        Service sdos = serviceDAO.getByLocalId(2, 2L);
        tres.setId((long) 3);
        tres.setTenantId(2);
        tres.setStatus(JourneyStatus.ACTIVE);
        tres.setService(sdos);
        tres.setDate(dateFormat.parse("15/06/2016 " + sdos.getTime().getHours() + ":" + sdos.getTime().getMinutes()));
        tres.setBusNumber(125);
        tres.setSeatsState(new Seat[]{v3, v23, v8, v40});
        dao.persist(tres);

        Journey cuatro = new Journey();
        cuatro.setId((long) 4);
        cuatro.setTenantId(2);
        cuatro.setService(suno);
        cuatro.setStatus(JourneyStatus.ACTIVE);
        cuatro.setDate(dateFormat.parse("16/06/2016 " + suno.getTime().getHours() + ":" + suno.getTime().getMinutes()));
        cuatro.setBusNumber(112);
        cuatro.setSeatsState(new Seat[]{v13, v23, v8, v40});
        dao.persist(cuatro);

        Journey cinco = new Journey();
        Service stres = serviceDAO.getByLocalId(2, 3L);
        cinco.setId((long) 5);
        cinco.setTenantId(2);
        cinco.setService(stres);
        cinco.setStatus(JourneyStatus.ACTIVE);
        cinco.setDate(dateFormat.parse("17/06/2016 " + stres.getTime().getHours() + ":" + stres.getTime().getMinutes()));
        cinco.setBusNumber(109);
        cinco.setSeatsState(new Seat[]{v3, v13});
        dao.persist(cinco);

        Journey seis = new Journey();
        Service scuatro = serviceDAO.getByLocalId(2, 4L);
        seis.setId((long) 6);
        seis.setTenantId(2);
        seis.setService(scuatro);
        seis.setStatus(JourneyStatus.ACTIVE);
        seis.setDate(dateFormat.parse("17/06/2016 " + scuatro.getTime().getHours() + ":" + scuatro.getTime().getMinutes()));
        seis.setBusNumber(119);
        seis.setSeatsState(new Seat[]{v3, v13, v8, v40});
        dao.persist(seis);

        System.out.print("Cantidad de journeys: ");
        System.out.println(dao.countAll());
    }

    @Test
    //Correr test de Servicios antes que este
    public void getJourneysByTenantDateAndStatus() throws ParseException {
        dao.clean();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        Seat v3 = new Seat(3, Position.CORRIDOR, false);
        Seat v8 = new Seat(8, Position.CORRIDOR, false);
        Seat v13 = new Seat(13, Position.CORRIDOR, false);
        Seat v23 = new Seat(23, Position.WINDOWS, false);
        Seat v40 = new Seat(40, Position.CORRIDOR, false);

        Journey uno = new Journey();
        Service suno = serviceDAO.getByLocalId(2, 5L);
        uno.setId((long) 1);
        uno.setTenantId(2);
        uno.setStatus(JourneyStatus.CANCELED);
        uno.setService(suno);
        uno.setDate(dateFormat.parse("12/06/2016 " + suno.getTime().getHours() + ":" + suno.getTime().getMinutes()));
        uno.setBusNumber(115);
        uno.setSeatsState(new Seat[]{v3, v8, v13, v23, v40});
        Bus abc01 = busDAO.getByBusId(2, "ABC01");
        uno.setBus(abc01);

        dao.persist(uno);

        Date D1 = dateFormat.parse("12/06/2016 " + suno.getTime().getHours() + ":" + suno.getTime().getMinutes());

        List<Journey> JList = dao.getJourneysByTenantDateAndStatus(2,D1,JourneyStatus.CANCELED,0,1);

        System.out.println("Tamaño lista: " + JList.size());
    }
}
