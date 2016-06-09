package com.usbus.dal.test;

import com.usbus.dal.dao.ServiceDAO;
import com.usbus.dal.model.Service;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.TimeZone;

/**
 * Created by Kavesa on 09/06/16.
 */
public class ServiceTest {
    protected ServiceDAO dao = new ServiceDAO();

    @Test
    public void persist() throws ParseException {
        dao.clean();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        Service srv = new Service(2, Long.parseLong("1"), "Montevideo - Rivera (Directo)", DayOfWeek.SATURDAY, format.parse("23:15"), null, 2, true);
        dao.persist(srv);

        srv = new Service(2, Long.parseLong("2"), "Montevideo - Rivera (Semi-Directo)", DayOfWeek.WEDNESDAY, format.parse("20:45"), null, 2, true);
        dao.persist(srv);

        srv = new Service(2, Long.parseLong("3"), "Montevideo - Colonia (Directo)", DayOfWeek.SUNDAY, format.parse("08:30"), null, 2, true);
        dao.persist(srv);

        srv = new Service(2, Long.parseLong("4"), "Montevideo - Tacuarembó (Común)", DayOfWeek.MONDAY, format.parse("12:45"), null, 2, true);
        dao.persist(srv);

        System.out.println(dao.countAll());
    }
}
